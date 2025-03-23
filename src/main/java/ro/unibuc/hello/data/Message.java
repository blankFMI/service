package ro.unibuc.hello.data;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("role")
    private String sender;       // "user" or "character"
    private LocalDateTime timestamp;
    private String content;

    public Message() {
    }

    public Message(String sender, LocalDateTime timestamp, String content) {
        this.sender = sender;
        this.timestamp = timestamp;
        this.content = content;
    }

    // Getters and setters

    @JsonProperty("role")
    public String getSender() {
        return sender;
    }

    @JsonProperty("role")
    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
