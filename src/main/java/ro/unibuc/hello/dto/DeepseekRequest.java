package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.Message;
import java.util.List;

public class DeepseekRequest {
    private String personality;
    private String background;
    private String context;
    private List<Message> messages;
    private String model = "deepseek-chat";

    public DeepseekRequest() { }

    public DeepseekRequest(String personality, String background, String context, List<Message> messages) {
        this.personality = personality;
        this.background = background;
        this.context = context;
        this.messages = messages;
    }

    public String getPersonality() {
        return personality;
    }
    public void setPersonality(String personality) {
        this.personality = personality;
    }
    public String getBackground() {
        return background;
    }
    public void setBackground(String background) {
        this.background = background;
    }
    public String getContext() {
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
}