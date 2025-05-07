package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ro.unibuc.hello.dto.ConversationRequest;
import ro.unibuc.hello.dto.ConversationResponse;
import ro.unibuc.hello.service.ConversationService;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConversationControllerTest {

    private ConversationService conversationService;
    private ConversationController conversationController;

    @BeforeEach
    void setUp() {
        conversationService = mock(ConversationService.class);
        conversationController = new ConversationController(conversationService);
    }

    @Test
    void testTalk_ReturnsExpectedResponse() {
        // Arrange
        ConversationRequest request = new ConversationRequest();
        request.setConversationId(null);
        request.setUserId("67da5d115e6caf1826497beb");
        request.setCharacterId("67e3c066f7c9cb7d2ea0cdd7");
        request.setMessage(Collections.singletonList("user: Bună, ce mai faci astăzi?"));

        ConversationResponse expectedResponse = new ConversationResponse();
        expectedResponse.setReply("Salut! Sunt bine, tu?");

        when(conversationService.converse(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ConversationResponse> responseEntity = conversationController.talk(request);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(conversationService, times(1)).converse(request);
    }
}
