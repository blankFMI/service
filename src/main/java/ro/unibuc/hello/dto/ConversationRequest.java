package ro.unibuc.hello.dto;

public class ConversationRequest {
    private String characterId;
    private String message;

    public ConversationRequest() { }

    public ConversationRequest(String characterId, String message) {
        this.characterId = characterId;
        this.message = message;
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
