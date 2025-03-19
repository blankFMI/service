package ro.unibuc.hello.dto;

public class ConversationRequest {
    private String conversationId; // Optional: existing conversation identifier; can be null if starting a new conversation.
    private String userId;
    private String characterId;
    private String message;

    public ConversationRequest() { }

    public ConversationRequest(String conversationId, String userId, String characterId, String message) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
