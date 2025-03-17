package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.Conversation;
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

    // Endpoint to start a new conversation
    @PostMapping("/start")
    public ResponseEntity<Conversation> startConversation(@RequestParam String userId,
                                                            @RequestParam String characterId) {
        Conversation conversation = conversationService.startConversation(userId, characterId);
        return ResponseEntity.ok(conversation);
    }

    // Endpoint to add a message to a conversation
    @PostMapping("/{id}/messages")
    public ResponseEntity<Conversation> addMessage(@PathVariable("id") String conversationId,
                                                   @RequestParam String sender,
                                                   @RequestParam String content) {
        Conversation updatedConversation = conversationService.addMessage(conversationId, sender, content);
        return ResponseEntity.ok(updatedConversation);
    }

    // Endpoint to process conversation (simulate sending context to API and receiving response)
    @PostMapping("/{id}/process")
    public ResponseEntity<String> processConversation(@PathVariable("id") String conversationId) {
        String response = conversationService.processConversation(conversationId);
        return ResponseEntity.ok(response);
    }
}
