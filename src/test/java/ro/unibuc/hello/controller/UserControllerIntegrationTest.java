// package ro.unibuc.hello.controller;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.context.DynamicPropertyRegistry;
// import org.springframework.test.context.DynamicPropertySource;
// import org.testcontainers.containers.MongoDBContainer;
// import org.testcontainers.junit.jupiter.Container;
// import ro.unibuc.hello.data.entity.UserEntity;
// import ro.unibuc.hello.data.repository.UserRepository;
// import ro.unibuc.hello.dto.User;
// import ro.unibuc.hello.utils.GenericControllerIntegrationTest;
// import org.springframework.test.web.servlet.ResultActions;

// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// public class UserControllerIntegrationTest extends GenericControllerIntegrationTest<UserController> {

//     @Container
//     private final static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
//             .withExposedPorts(27017)
//             .withSharding();

//     @DynamicPropertySource
//     private static void setProperties(DynamicPropertyRegistry registry) {
//         final String MONGO_URL = "mongodb://localhost:";
//         final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

//         registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
//     }

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private UserController userController;

//     @Override
//     public String getEndpoint() {
//         return "users";
//     }

//     @Override
//     public UserController getController() {
//         return userController;
//     }

//     @Test
//     void testCreateUser() throws Exception {
//         User userInput = new User("john", "john@example.com", "password", null);

//         performPost(userInput, null, "")
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.username").value("john"));
//     }

//     @Test
//     void testGetAllUsers() throws Exception {
//         performGet(null, "")
//                 .andExpect(status().isOk());
//     }

//     @Test
//     void testGetUserById_Found() throws Exception {
//         UserEntity user = userRepository.save(new UserEntity("john", "john@example.com", "password", UserEntity.Role.CUSTOMER));

//         performGet(null, "/" + user.getId())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(user.getId()))
//                 .andExpect(jsonPath("$.username").value("john"));
//     }

//     @Test
//     void testUpdateUser_Found() throws Exception {
//         UserEntity user = userRepository.save(new UserEntity("john", "john@example.com", "password", UserEntity.Role.CUSTOMER));
//         User updatedUser = new User("johnny", "johnny@example.com", "newpassword", null);

//         performPut(updatedUser, null, "/" + user.getId())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.username").value("johnny"));
//     }

//     @Test
//     void testDeleteUser_Found() throws Exception {
//         UserEntity user = userRepository.save(new UserEntity("john", "john@example.com", "password", UserEntity.Role.CUSTOMER));

//         performDelete(null, "/" + user.getId())
//                 .andExpect(status().isOk());
//     }
// }
