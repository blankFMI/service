package ro.unibuc.hello.dto;

import java.util.List;

public class ConversationRequest {
    private String conversationId; // Optional: existing conversation identifier; can be null if starting a new conversation.
    private String userId;
    private String characterId;
    // Changed from String to List<String>
    private List<String> message;

    public ConversationRequest() { }

    // Updated constructor to accept a list for message
    public ConversationRequest(String conversationId, String userId, String characterId, List<String> message) {
        this.conversationId = conversationId;
        this.userId = userId;
        this.characterId = characterId;
        this.message = message;
    }

    // Getters and Setters
    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
