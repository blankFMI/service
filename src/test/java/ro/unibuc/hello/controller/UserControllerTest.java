package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.AccountType;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    void testCreateUser() throws Exception {
        String userJson = """
        {
          "username": "premiumuser",
          "email": "premium@example.com",
          "password": "pass123",
          "accountType": "PREMIUM"
        }
        """;
        when(userService.createUser(any(User.class)))
            .thenReturn(new User("testuser", "test@example.com", "testpass", AccountType.PREMIUM));


        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.accountType").value(Matchers.anyOf(
                    Matchers.is("BASIC"),
                    Matchers.is("PREMIUM"))));
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User("user1", "user1@example.com", "pass1", AccountType.BASIC);
        User user2 = new User("user2", "user2@example.com", "pass2", AccountType.PREMIUM);

        List<User> mockUserList = List.of(user1, user2);

        when(userService.getAllUsers()).thenReturn(mockUserList);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("user1", response.getBody().get(0).getUsername());
        verify(userService, times(1)).getAllUsers();
    }


    @Test
    void testGetUserById() throws Exception {
        User user = new User("user1", "user1@example.com", "pass1", AccountType.BASIC);
        user.setId("111");

        when(userService.getUserById("111")).thenReturn(user);

        String userId = user.getId();
        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.email").value("user1@example.com"));
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        String userId = "112";

        doThrow(new RuntimeException("User not found")).when(userService).deleteUser(userId);

        mockMvc.perform(delete("/users/delete/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id: " + userId));

        verify(userService, times(1)).deleteUser(userId);
    }
    
    @Test
    void testDeleteUser_Success() throws Exception {
        String userId = "113";

        // Simulăm că nu se aruncă excepție => delete-ul merge
        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/users/delete/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));

        verify(userService, times(1)).deleteUser(userId);
    }




}
