package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ro.unibuc.hello.data.Conversation;
import ro.unibuc.hello.data.CharacterRepository;
import ro.unibuc.hello.data.ConversationRepository;
import ro.unibuc.hello.dto.ConversationRequest;
import ro.unibuc.hello.dto.ConversationResponse;
import ro.unibuc.hello.dto.DeepseekResponse;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversationServiceTest {

    private CharacterRepository characterRepository;
    private ConversationRepository conversationRepository;
    private DeepseekService deepseekService;
    private ConversationService conversationService;

    @BeforeEach
    void setUp() {
        characterRepository = mock(CharacterRepository.class);
        conversationRepository = mock(ConversationRepository.class);
        deepseekService = mock(DeepseekService.class);

        conversationService = new ConversationService(deepseekService, characterRepository, conversationRepository);
    }

    // @Test
    // void testConverse_Successful() {
    //     // Arrange
    //     // Use fully qualified class name to remove ambiguity:
    //     ro.unibuc.hello.data.Character character = new ro.unibuc.hello.data.Character();
    //     character.setPersonality("Friendly");
    //     character.setBackground("Lives in the forest");

    //     Conversation conversation = new Conversation("user1", "char1");

    //     ConversationRequest request = new ConversationRequest();
    //     request.setCharacterId("char1");
    //     request.setUserId("user1");
    //     // The setMessage method expects a List<String>, so no changes here.
    //     request.setMessage(List.of("Hello!"));

    //     DeepseekResponse response = new DeepseekResponse();
    //     response.setReply("Hello, traveler!");

    //     when(characterRepository.findById("char1")).thenReturn(Optional.of(character));
    //     when(conversationRepository.findByUserIdAndCharacterId("user1", "char1")).thenReturn(Optional.of(conversation));
    //     when(deepseekService.getCharacterResponse(any())).thenReturn(response);

    //     // Act
    //     ConversationResponse result = conversationService.converse(request);

    //     // Assert
    //     assertNotNull(result);
    //     assertEquals("Hello, traveler!", result.getReply());

    //     ArgumentCaptor<Conversation> savedConversation = ArgumentCaptor.forClass(Conversation.class);
    //     verify(conversationRepository).save(savedConversation.capture());

    //     Conversation convo = savedConversation.getValue();
    //     // After the conversation, there should be two messages added: the user message and the character reply.
    //     assertEquals(2, convo.getMessages().size());
    //     assertTrue(convo.getContext().contains("Friendly"));
    //     assertTrue(convo.getContext().contains("Hello!"));
    // }

    @Test
    void testConverse_CharacterNotFound() {
        // Arrange
        ConversationRequest request = new ConversationRequest();
        request.setCharacterId("unknown");
        request.setUserId("user1");
        request.setMessage(List.of("Hi"));

        when(characterRepository.findById("unknown")).thenReturn(Optional.empty());

        // Act & Assert
        Exception ex = assertThrows(RuntimeException.class, () -> conversationService.converse(request));
        assertEquals("Character not found", ex.getMessage());
    }

    @Test
    void testConverse_DeepseekFails() {
        // Arrange
        ro.unibuc.hello.data.Character character = new ro.unibuc.hello.data.Character();
        character.setPersonality("Wise");
        character.setBackground("Ancient temple");

        when(characterRepository.findById("char1")).thenReturn(Optional.of(character));
        when(conversationRepository.findByUserIdAndCharacterId("user1", "char1"))
                .thenReturn(Optional.of(new Conversation("user1", "char1")));

        ConversationRequest request = new ConversationRequest();
        request.setCharacterId("char1");
        request.setUserId("user1");
        request.setMessage(List.of("Tell me a story"));

        when(deepseekService.getCharacterResponse(any())).thenThrow(new RuntimeException("API down"));

        // Act & Assert
        Exception ex = assertThrows(RuntimeException.class, () -> conversationService.converse(request));
        assertTrue(ex.getMessage().contains("Deepseek API call failed"));
    }
}
