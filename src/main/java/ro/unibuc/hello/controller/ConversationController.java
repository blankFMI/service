package ro.unibuc.hello.controller;

import ro.unibuc.hello.dto.ConversationRequest;
import ro.unibuc.hello.dto.ConversationResponse;
import ro.unibuc.hello.service.ConversationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    // Endpoint for initiating a conversation or sending a new message
    @PostMapping("/talk")
    public ResponseEntity<ConversationResponse> talk(@RequestBody ConversationRequest request) {
        ConversationResponse response = conversationService.converse(request);
        return ResponseEntity.ok(response);
    }
}
