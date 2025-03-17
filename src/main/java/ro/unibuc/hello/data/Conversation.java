package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "conversations")
public class Conversation {

    @Id
    private String id;
    private String userId;
    private String characterId;
    private List<Message> messages = new ArrayList<>();
    private LocalDateTime startedAt = LocalDateTime.now();

    public Conversation() {}

    public Conversation(String userId, String characterId) {
        this.userId = userId;
        this.characterId = characterId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
}
