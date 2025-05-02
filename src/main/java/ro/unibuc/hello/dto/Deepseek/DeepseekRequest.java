package ro.unibuc.hello.dto.Deepseek;

import ro.unibuc.hello.service.llm.ChatMessage;
import java.util.List;

public record DeepseekRequest(String model, List<ProviderMsg> messages) {
    public DeepseekRequest(List<ChatMessage> chat) {
        this("deepseek-chat", chat.stream().map(ProviderMsg::new).toList());
    }
    public record ProviderMsg(ChatMessage chat) {
        public String role()    { return chat.role().name().toLowerCase(); }
        public String content() { return chat.content(); }
    }
}
