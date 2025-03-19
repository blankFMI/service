package ro.unibuc.hello.dto;

public class DeepseekRequest {
    private String personality;
    private String background;
    private String context; // e.g., last 20 messages concatenated

    public DeepseekRequest() { }

    public DeepseekRequest(String personality, String background, String context) {
        this.personality = personality;
        this.background = background;
        this.context = context;
    }

    // Getters and setters...
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
}
