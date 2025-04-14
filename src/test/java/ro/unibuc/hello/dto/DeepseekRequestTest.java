package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import ro.unibuc.hello.data.Message;

class DeepseekRequestTest {

    @Test
    void testDefaultConstructorAndSetters() {
        // Create an instance using the default constructor
        DeepseekRequest request = new DeepseekRequest();
        
        // Set values via setters
        request.setPersonality("Friendly");
        request.setBackground("Background info");
        request.setContext("Some request context");
        
        Message msg = new Message("Hello", LocalDateTime.now(), "sender");
        List<Message> messages = List.of(msg);
        request.setMessages(messages);
        
        // Optionally, override the default model
        request.setModel("custom-model");
        
        // Verify that getters return the same values set
        assertEquals("Friendly", request.getPersonality());
        assertEquals("Background info", request.getBackground());
        assertEquals("Some request context", request.getContext());
        assertEquals(messages, request.getMessages());
        assertEquals("custom-model", request.getModel());
    }

    @Test
    void testParameterizedConstructor() {
        // Create a Message and a list of messages
        Message msg = new Message("Hi", LocalDateTime.now(), "sender");
        List<Message> messages = List.of(msg);
        
        // Create an instance using the parameterized constructor
        DeepseekRequest request = new DeepseekRequest("Outgoing", "Background details", "Request context", messages);
        
        // Verify that the values are correctly initialized
        assertEquals("Outgoing", request.getPersonality());
        assertEquals("Background details", request.getBackground());
        assertEquals("Request context", request.getContext());
        assertEquals(messages, request.getMessages());
        // The default model should be "deepseek-chat" if not overridden
        assertEquals("deepseek-chat", request.getModel());
    }
}
