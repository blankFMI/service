package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "conversations")
public class Conversation {

    @Id
    private String id;  // Unique conversation identifier
    private String userId;      // Reference to the user who is conversing
    private String characterId; // Reference to the character being conversed with
    
    // An ordered list of messages exchanged in the conversation
    private List<Message> messages = new ArrayList<>();
    
    // Optional: A field to store conversation context (e.g., last 20 messages, concatenated as a string)
    private String context;

    public Conversation() {
    }

    public Conversation(String userId, String characterId) {
        this.userId = userId;
        this.characterId = characterId;
    }

    // Getters and setters

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

    public String getContext() {
       return context;
    }

    public void setContext(String context) {
       this.context = context;
    }
}
