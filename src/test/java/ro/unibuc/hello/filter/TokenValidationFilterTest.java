package ro.unibuc.hello.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ro.unibuc.hello.service.SessionService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TokenValidationFilterTest {

    private SessionService sessionService;
    private TokenValidationFilter tokenFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        // Create mocks for dependencies
        sessionService = mock(SessionService.class);
        tokenFilter = new TokenValidationFilter(sessionService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
    }

    // Test case 1: Paths that should be excluded from token validation.
    @Test
    void testExcludedPaths() throws ServletException, IOException {
        String[] excludedPaths = {
            "/auth/login", "/public", "/users", "/characters", "/hello-world", "/info", "/greetings", "/conversations"
        };

        for (String path : excludedPaths) {
            when(request.getRequestURI()).thenReturn(path);
            // Clear any previous interactions
            reset(chain, response);

            tokenFilter.doFilterInternal(request, response, chain);

            // Verify that chain.doFilter() is called and no error is sent.
            verify(chain, times(1)).doFilter(request, response);
            verify(response, never()).sendError(anyInt(), anyString());
        }
    }

    // Test case 2: Missing Authorization header for non-excluded endpoints.
    @Test
    void testMissingAuthHeader() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/protected/resource");
        when(request.getHeader("Authorization")).thenReturn(null);

        tokenFilter.doFilterInternal(request, response, chain);

        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
        verify(chain, never()).doFilter(request, response);
    }

    // Test case 3: Authorization header does not start with "Bearer ".
    @Test
    void testInvalidAuthHeaderPrefix() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/protected/resource");
        when(request.getHeader("Authorization")).thenReturn("Token invalidFormat");

        tokenFilter.doFilterInternal(request, response, chain);

        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
        verify(chain, never()).doFilter(request, response);
    }

    // Test case 4: Provided token is invalid.
    @Test
    void testInvalidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/protected/resource");
        // Properly formatted header "Bearer <token>"
        when(request.getHeader("Authorization")).thenReturn("Bearer someInvalidToken");

        // Simulate the session service returning false (invalid token)
        when(sessionService.isValidToken("someInvalidToken")).thenReturn(false);

        tokenFilter.doFilterInternal(request, response, chain);

        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        verify(chain, never()).doFilter(request, response);
    }

    // Test case 5: Valid token is provided.
    @Test
    void testValidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/protected/resource");
        // Provide a valid Bearer token header.
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");

        // Simulate a valid token check
        when(sessionService.isValidToken("validToken")).thenReturn(true);

        tokenFilter.doFilterInternal(request, response, chain);

        // Expect the request to continue processing
        verify(chain, times(1)).doFilter(request, response);
        verify(response, never()).sendError(anyInt(), anyString());
    }
}
