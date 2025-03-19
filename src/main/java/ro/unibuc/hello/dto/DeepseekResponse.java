package ro.unibuc.hello.dto;

public class DeepseekResponse {
    private String reply;

    public DeepseekResponse() { }

    public DeepseekResponse(String reply) {
        this.reply = reply;
    }

    // Getter and setter...
    public String getReply() {
        return reply;
    }
    public void setReply(String reply) {
        this.reply = reply;
    }
}
    