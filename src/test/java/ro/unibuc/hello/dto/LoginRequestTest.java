package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    void testDefaultConstructorAndSetters() {
        // Create an instance using the default constructor
        LoginRequest loginRequest = new LoginRequest();
        
        // Set values using setters
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("testPass");

        // Verify that getters return the expected values
        assertEquals("testUser", loginRequest.getUsername(), "Username should be 'testUser'");
        assertEquals("testPass", loginRequest.getPassword(), "Password should be 'testPass'");
    }

    @Test
    void testParameterizedConstructor() {
        // Instantiate LoginRequest with the parameterized constructor
        LoginRequest loginRequest = new LoginRequest("user123", "pass123");

        // Verify that the values are set correctly via getters
        assertEquals("user123", loginRequest.getUsername(), "Username should be 'user123'");
        assertEquals("pass123", loginRequest.getPassword(), "Password should be 'pass123'");
    }
}
