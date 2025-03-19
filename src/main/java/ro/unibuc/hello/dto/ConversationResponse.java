package ro.unibuc.hello.dto;

public class ConversationResponse {
    private String reply;

    public ConversationResponse() { }

    public ConversationResponse(String reply) {
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
