package ro.unibuc.hello.service.llm;

import ro.unibuc.hello.dto.Deepseek.*;
import ro.unibuc.hello.service.llm.*;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeepseekChatProvider implements ChatProvider {

    private final WebClient deepseekClient;
    @Qualifier("deepseekCB")
    private final CircuitBreaker circuitBreaker;

    @Override
    public Mono<AssistantReply> chat(List<ChatMessage> prompt) {
        DeepseekRequest body = new DeepseekRequest(prompt);

        return Mono.defer(() ->
                deepseekClient.post()
                        .uri("/v1/chat/completions")
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(DeepseekResponse.class)
                        .map(this::map))
            .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
            .timeout(Duration.ofSeconds(15));
    }

    private AssistantReply map(DeepseekResponse r) {
        return new AssistantReply(
                r.assistantText(),
                new AssistantReply.Usage(
                        r.usage().prompt_tokens(),
                        r.usage().completion_tokens(),
                        r.usage().total_tokens()),
                r.id(),
                Instant.ofEpochSecond(r.created()));
    }
}
