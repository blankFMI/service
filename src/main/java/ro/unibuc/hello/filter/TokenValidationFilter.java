package ro.unibuc.hello.filter;

import ro.unibuc.hello.service.SessionService;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.stereotype.Component;
import java.io.IOException;

public class TokenValidationFilter extends OncePerRequestFilter {

    private final SessionService sessionService;

    public TokenValidationFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Skip token validation for login and public endpoints
        String path = request.getRequestURI();
        if (path.startsWith("/auth/login") || path.startsWith("/public") || path.startsWith("/users") || path.startsWith("/characters") || path.startsWith("/hello-world") || path.startsWith("/info") || path.startsWith("/greetings") || path.startsWith("/conversations")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Retrieve the Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
            return;
        }
        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Validate the token using the session service
        if (!sessionService.isValidToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }
        
        // If token is valid, continue processing the request
        filterChain.doFilter(request, response);
    }
}
