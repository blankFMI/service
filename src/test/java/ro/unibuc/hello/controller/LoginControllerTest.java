package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.LoginResponse;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.service.SessionService;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    private UserRepository userRepository;
    private SessionService sessionService;
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        sessionService = mock(SessionService.class);
        loginController = new LoginController(userRepository, sessionService);
    }

    @Test
    void login_Successful() {
        LoginRequest request = new LoginRequest();
        request.setUsername("basicUser");
        request.setPassword("basicPass");

        User user = new User();
        user.setId("user123");
        user.setUsername("basicUser");
        user.setPassword("basicPass");

        when(userRepository.findByUsername("basicUser")).thenReturn(user);

        ResponseEntity<?> response = loginController.login(request);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof LoginResponse);
        verify(sessionService).addToken(eq("user123"), anyString());
    }

    @Test
    void login_InvalidUsername() {
        LoginRequest request = new LoginRequest();
        request.setUsername("unknown");
        request.setPassword("anyPass");

        when(userRepository.findByUsername("unknown")).thenReturn(null);

        ResponseEntity<?> response = loginController.login(request);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void login_InvalidPassword() {
        LoginRequest request = new LoginRequest();
        request.setUsername("basicUser");
        request.setPassword("wrongPass");

        User user = new User();
        user.setId("user123");
        user.setUsername("basicUser");
        user.setPassword("correctPass");

        when(userRepository.findByUsername("basicUser")).thenReturn(user);

        ResponseEntity<?> response = loginController.login(request);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void secureData_ValidToken() {
        when(sessionService.isValidToken("valid-token")).thenReturn(true);

        ResponseEntity<?> response = loginController.getSecureData("Bearer valid-token");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("This is secured data", response.getBody());
    }

    @Test
    void secureData_InvalidToken() {
        when(sessionService.isValidToken("invalid-token")).thenReturn(false);

        ResponseEntity<?> response = loginController.getSecureData("Bearer invalid-token");

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid token", response.getBody());
    }

    @Test
    void secureData_MissingHeader() {
        ResponseEntity<?> response = loginController.getSecureData(null);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Missing or invalid token", response.getBody());
    }

    @Test
    void logout_Successful() {
        ResponseEntity<?> response = loginController.logout("Bearer valid-token");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Logged out successfully", response.getBody());
        verify(sessionService).removeTokenByValue("valid-token");
    }

    @Test
    void logout_InvalidHeader() {
        ResponseEntity<?> response = loginController.logout(null);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Missing or invalid token", response.getBody());
    }


}
