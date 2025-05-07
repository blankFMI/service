package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ConversationRequestTest {

    @Test
    void testDefaultConstructorAndSetters() {
        // Use the default constructor
        ConversationRequest request = new ConversationRequest();

        // Set fields using setters
        request.setConversationId("conv123");
        request.setUserId("user456");
        request.setCharacterId("char789");
        List<String> messages = Arrays.asList("Hello", "How are you?");
        request.setMessage(messages);

        // Verify getters return the correct values
        assertEquals("conv123", request.getConversationId());
        assertEquals("user456", request.getUserId());
        assertEquals("char789", request.getCharacterId());
        assertEquals(messages, request.getMessage());
    }

    @Test
    void testParameterizedConstructorAndGetters() {
        // Create a list of messages to pass to constructor
        List<String> messages = Arrays.asList("Hi", "What's up?");

        // Use the parameterized constructor
        ConversationRequest request = new ConversationRequest("convABC", "userDEF", "charGHI", messages);

        // Verify getters return the expected values
        assertEquals("convABC", request.getConversationId());
        assertEquals("userDEF", request.getUserId());
        assertEquals("charGHI", request.getCharacterId());
        assertEquals(messages, request.getMessage());
    }
}
