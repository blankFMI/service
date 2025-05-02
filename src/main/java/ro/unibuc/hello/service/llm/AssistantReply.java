package ro.unibuc.hello.service.llm;

import java.time.Instant;

/** Returned by ChatProvider implementations. */
public record AssistantReply(
        String content,
        Usage usage,
        String providerRequestId,
        Instant created) {

    public record Usage(int promptTokens, int completionTokens, int totalTokens) {}
}
