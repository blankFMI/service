package ro.unibuc.hello.service.llm;

import reactor.core.publisher.Mono;
import java.util.List;

/** A chat provider hides the HTTP/API details behind a simple contract. */
public interface ChatProvider {
    Mono<ro.unibuc.hello.service.llm.AssistantReply> chat(List<ro.unibuc.hello.service.llm.ChatMessage> prompt);
}
