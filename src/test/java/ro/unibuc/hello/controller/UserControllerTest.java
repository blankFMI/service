package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.AccountType;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.service.UserService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private UserController userController;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateUser() throws Exception {
        User mockUser = new User("basicUser", "basic@example.com", "basicPass", AccountType.BASIC);
        ReflectionTestUtils.setField(mockUser, "id", "123456789");

        when(userService.createUser(any(User.class))).thenReturn(mockUser);

        String requestJson = """
        {
            "username": "basicUser",
            "email": "basic@example.com",
            "password": "basicPass",
            "accountType": "BASIC"
        }
        """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("basicUser"))
                .andExpect(jsonPath("$.email").value("basic@example.com"))
                .andExpect(jsonPath("$.accountType").value("BASIC"))
                .andExpect(jsonPath("$.id").value("123456789"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user1 = new User("1", "john.doe@example.com", "John Doe", AccountType.BASIC);
        User user2 = new User("2", "jane.smith@example.com", "Jane Smith", AccountType.BASIC);
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }
}
