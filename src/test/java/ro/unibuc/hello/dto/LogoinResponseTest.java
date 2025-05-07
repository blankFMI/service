package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {

    @Test
    void testDefaultConstructorAndSetter() {
        // Create an instance using the default constructor
        LoginResponse loginResponse = new LoginResponse();
        
        // Set the token using the setter
        loginResponse.setToken("sampleToken123");
        
        // Verify that getter returns the expected token
        assertEquals("sampleToken123", loginResponse.getToken(), "Token should be 'sampleToken123'");
    }

    @Test
    void testParameterizedConstructor() {
        // Create an instance using the parameterized constructor
        LoginResponse loginResponse = new LoginResponse("paramToken456");
        
        // Verify that the token is initialized correctly
        assertEquals("paramToken456", loginResponse.getToken(), "Token should be 'paramToken456'");
    }
}
