package ro.unibuc.hello.controller;

import  ro.unibuc.hello.dto.*;
import  ro.unibuc.hello.service.ConversationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping("/talk")
    public ResponseEntity<ConversationResponse> talk(@Valid @RequestBody ConversationRequest request) {
        return ResponseEntity.ok(conversationService.converse(request));
    }
}
