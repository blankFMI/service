package ro.unibuc.hello.service;

import ro.unibuc.hello.data.Character;
import ro.unibuc.hello.data.Conversation;
import ro.unibuc.hello.data.Message;
import ro.unibuc.hello.dto.ConversationRequest;
import ro.unibuc.hello.dto.ConversationResponse;
import ro.unibuc.hello.dto.DeepseekRequest;
import ro.unibuc.hello.dto.DeepseekResponse;
import ro.unibuc.hello.data.CharacterRepository;
import ro.unibuc.hello.data.ConversationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
public class ConversationService {

    private final DeepseekService deepseekService;
    private final CharacterRepository characterRepository;
    private final ConversationRepository conversationRepository;

    public ConversationService(DeepseekService deepseekService,
                               CharacterRepository characterRepository,
                               ConversationRepository conversationRepository) {
        this.deepseekService = deepseekService;
        this.characterRepository = characterRepository;
        this.conversationRepository = conversationRepository;
    }

    public ConversationResponse converse(ConversationRequest request) {
        // 1. Retrieve character details by characterId.
        Character character = characterRepository.findById(request.getCharacterId())
                .orElseThrow(() -> new RuntimeException("Character not found"));

        // 2. Retrieve (or create) the conversation for this user-character pair.
        Optional<Conversation> optionalConversation = conversationRepository
                .findByUserIdAndCharacterId(request.getUserId(), request.getCharacterId());
        Conversation conversation = optionalConversation.orElseGet(() ->
                new Conversation(request.getUserId(), request.getCharacterId()));

        // 3. Create a new user message and store it in the conversation.
        String userMessageContent = String.join(" ", request.getMessage());
        Message userMessage = new Message("user", LocalDateTime.now(), userMessageContent);
        conversation.getMessages().add(userMessage);

        // 4. Get the last 20 messages (or fewer).
        List<Message> allMessages = conversation.getMessages();
        int totalMessages = allMessages.size();
        int startIndex = Math.max(0, totalMessages - 20);
        List<Message> recentMessages = allMessages.subList(startIndex, totalMessages);

        // 5. Build a conversation context string from the recent messages.
        String context = recentMessages.stream()
                .map(msg -> msg.getSender() + ": " + msg.getContent())
                .collect(Collectors.joining("\n"));

        // 6. Build a template string using character data + recent conversation.
        String characterData = character.getPersonality() + " " + character.getBackground();
        ;
String template = "You are " + characterData + " based on this conversation:\n"
                + context + "\nWhat is your response? \nRespond in the language the conversation is in.";
        // 7. Prepare the DeepseekRequest.
        DeepseekRequest deepseekRequest = new DeepseekRequest();
        deepseekRequest.setPersonality(character.getPersonality());
        deepseekRequest.setBackground(character.getBackground());
        deepseekRequest.setContext(template);

        // Instead of sending a list of messages, send one "user" message containing the entire template.
        deepseekRequest.setMessages(Collections.singletonList(
            new Message("user", null, template)
        ));

        // 8. Call DeepseekService to get the character's reply.
        DeepseekResponse deepseekResponse;
        try {
            deepseekResponse = deepseekService.getCharacterResponse(deepseekRequest);
        } catch (Exception e) {
            throw new RuntimeException("Deepseek API call failed: " + e.getMessage());
        }

        // 9. Store the character's reply in the conversation.
        Message characterMessage = new Message("character", LocalDateTime.now(), deepseekResponse.getReply());
        conversation.getMessages().add(characterMessage);

        // (Optional) Save the template in the conversation context.
        conversation.setContext(template);
        conversationRepository.save(conversation);

        // 10. Return the character's reply in the response.
        return new ConversationResponse(deepseekResponse.getReply());
    }
}
