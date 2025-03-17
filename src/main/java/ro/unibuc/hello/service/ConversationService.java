package ro.unibuc.hello.service;

import ro.unibuc.hello.api.ConversationAPIClient;
import ro.unibuc.hello.data.Conversation;
import ro.unibuc.hello.data.Message;
import ro.unibuc.hello.data.ConversationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationAPIClient conversationAPIClient;

    public ConversationService(ConversationRepository conversationRepository,
                               ConversationAPIClient conversationAPIClient) {
        this.conversationRepository = conversationRepository;
        this.conversationAPIClient = conversationAPIClient;
    }

    // Start a new conversation for a given user and character
    public Conversation startConversation(String userId, String characterId) {
        Conversation conversation = new Conversation(userId, characterId);
        return conversationRepository.save(conversation);
    }

    // Add a message to an existing conversation
    public Conversation addMessage(String conversationId, String sender, String content) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        Message message = new Message(sender, content, LocalDateTime.now());
        conversation.getMessages().add(message);
        return conversationRepository.save(conversation);
    }

    // Process the conversation: retrieve context, call the API, and append the API response
    public String processConversation(String conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        // Retrieve the last 20 messages (or fewer, if not available)
        List<Message> messages = conversation.getMessages();
        int start = Math.max(0, messages.size() - 20);
        List<Message> contextMessages = messages.subList(start, messages.size());

        // Build the conversation context as a String
        StringBuilder contextBuilder = new StringBuilder();
        for (Message msg : contextMessages) {
            contextBuilder.append(msg.getSender())
                          .append(": ")
                          .append(msg.getContent())
                          .append("\n");
        }
        String context = contextBuilder.toString();

        // Call the external API (Deepseek) to get a response
        String apiResponse = conversationAPIClient.getResponse(context);

        // Append the API response as a new message from the character
        Message responseMessage = new Message("character", apiResponse, LocalDateTime.now());
        conversation.getMessages().add(responseMessage);
        conversationRepository.save(conversation);

        return apiResponse;
    }
}
