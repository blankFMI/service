package ro.unibuc.hello.dto.Deepseek;

import java.util.List;

public record DeepseekResponse(
        String id,
        long created,
        List<Choice> choices,
        Usage usage) {

    public record Choice(ProviderMsg message) {}
    public record ProviderMsg(String role, String content) {}
    public record Usage(int prompt_tokens, int completion_tokens, int total_tokens) {}

    // Convenience accessor
    public String assistantText() {
        return choices().getFirst().message().content();
    }
}
