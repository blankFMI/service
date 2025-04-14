package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConversationResponseTest {

    @Test
    void testDefaultConstructorAndSetters() {
        // Use default constructor
        ConversationResponse response = new ConversationResponse();
        
        // Set a value using the setter
        response.setReply("Test reply");
        
        // Verify the getter returns the expected value
        assertEquals("Test reply", response.getReply(), "The reply should be 'Test reply'");
    }

    @Test
    void testParameterizedConstructor() {
        // Create an instance with the parameterized constructor
        ConversationResponse response = new ConversationResponse("Hello World");
        
        // Verify that the getter returns the initialized value
        assertEquals("Hello World", response.getReply(), "The reply should be 'Hello World'");
    }
}
