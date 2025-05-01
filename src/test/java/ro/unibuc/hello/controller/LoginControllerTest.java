package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.service.SessionService;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class LoginControllerTest {

    private LoginController loginController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionService sessionService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginController = new LoginController(userRepository, sessionService);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testLogin_Success() throws Exception {
        User mockUser = new User("testuser", "email", "password", null);
        mockUser.setId("abc123");
    
        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);
        doNothing().when(sessionService).addToken(eq("abc123"), anyString());
    
        LoginRequest request = new LoginRequest("testuser", "password");
        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void testLogin_InvalidUsername() throws Exception{
        when(userRepository.findByUsername("wrong")).thenReturn(null);

        LoginRequest request = new LoginRequest("wrong", "anything");

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username"));
    }

    @Test
    void testLogin_InvalidPassword() throws Exception {
        User mockUser = new User("testuser", "email", "correctpass", null);
        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);

        LoginRequest request = new LoginRequest("testuser", "wrongpass");

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid password"));
    }

    @Test
    void testGetSecureData_WithValidBearerToken() throws Exception {
        String token = "valid-token";
    
        when(sessionService.isValidToken(token)).thenReturn(true);
    
        mockMvc.perform(get("/auth/secure-data")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("This is secured data"));
    }
    
    @Test
    void testGetSecureData_InvalidToken() throws Exception {
        when(sessionService.isValidToken("invalid-token")).thenReturn(false);
    
        mockMvc.perform(get("/auth/secure-data")
                .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid token"));
    }
    
    @Test
    void testGetSecureData_MissingBearerPrefix() throws Exception {
        mockMvc.perform(get("/auth/secure-data")
                .header("Authorization", "invalid-token"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Missing or invalid token"));
    }
    @Test
    void testLogout_WithValidBearerToken() throws Exception {
        String token = "valid-token";

        doNothing().when(sessionService).removeTokenByValue(token);

        mockMvc.perform(post("/auth/logout")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged out successfully"));
    }

    @Test
    void testLogout_MissingBearerPrefix() throws Exception {
        mockMvc.perform(post("/auth/logout")
                .header("Authorization", "invalid-token"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Missing or invalid token"));
    }

    @Test
    void testLogout_MissingHeader() throws Exception {
        mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Missing or invalid token"));
    }
}
