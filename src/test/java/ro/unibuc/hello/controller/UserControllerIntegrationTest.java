package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ro.unibuc.hello.data.AccountType;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;




@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class UserControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withExposedPorts(27017)
            .withSharding();

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        final String MONGO_URL = "mongodb://localhost:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));
        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        userService.deleteAllUsers();

        //User(String username, String email, String password, AccountType accountType)
        User user1 = new User("John Doe", "john@example.com", "basicPaass", AccountType.BASIC);
        User user2 = new User("Jane Smith", "jane@example.com", "premiumPass", AccountType.PREMIUM);
        user1.setId("1");
        user2.setId("2");
       
        userService.createUser(user1);
        userService.createUser(user2);
    }

    @Test
    public void testGetAllUsers() throws Exception {


        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].username").value("John Doe"))
            .andExpect(jsonPath("$[1].username").value("Jane Smith"));
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("Alice Johnson", "alice@example.com", "alicePass", AccountType.BASIC);
        user.setId("3");
        userService.createUser(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.username").value("Alice Johnson"));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setUsername("John Updated");
        updatedUser.setEmail("john@newmail.com");
        updatedUser.setPassword("updatedPass");

        userService.updateUser("1", updatedUser);

        mockMvc.perform(put("/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(status().isOk());
                // .andExpect(jsonPath("$.username").value("John Updated"))
                // .andExpect(jsonPath("$.email").value("john@newmail.com"))
                // .andExpect(jsonPath("$.password").value("updatedPass"));

        //intr un fel testam si getUserById aici
        mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("John Updated"))
            .andExpect(jsonPath("$.email").value("john@newmail.com"))
            .andExpect(jsonPath("$.password").value("updatedPass"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/delete/2"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testDeleteNonExistentUser() throws Exception {
        mockMvc.perform(delete("/users/delete/999"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User not found with id: 999")); // sau mesajul corespunzător
    }
}
