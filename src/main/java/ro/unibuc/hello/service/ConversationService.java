package ro.unibuc.hello.service;

import ro.unibuc.hello.dto.ConversationRequest;
import ro.unibuc.hello.dto.ConversationResponse;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    public ConversationResponse converse(ConversationRequest request) {
        // For demonstration, simply echo the message with a simulated reply.
        String reply = "Hello, I am character " + request.getCharacterId() + 
                       ". You said: " + request.getMessage() + ".";
        return new ConversationResponse(reply);
    }
}
