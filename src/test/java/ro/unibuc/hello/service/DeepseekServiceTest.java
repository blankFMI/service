package ro.unibuc.hello.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ro.unibuc.hello.dto.DeepseekRequest;
import ro.unibuc.hello.dto.DeepseekResponse;
import ro.unibuc.hello.data.Message; 

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class DeepseekServiceTest {

    private RestTemplate restTemplate;
    private DeepseekService deepseekService;
    private MockRestServiceServer mockServer;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        objectMapper = new ObjectMapper();

        // Override the private RestTemplate in DeepseekService via reflection for testing purposes
        deepseekService = new DeepseekService() {
            {
                try {
                    var field = DeepseekService.class.getDeclaredField("restTemplate");
                    field.setAccessible(true);
                    field.set(this, restTemplate);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Test
    void testGetCharacterResponse_EmptyMessages_ThrowsException() {
        // Arrange
        DeepseekRequest request = new DeepseekRequest(); // messages not set

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            deepseekService.getCharacterResponse(request);
        });
    }

    
}
