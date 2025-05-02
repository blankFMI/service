package ro.unibuc.hello.service;

import ro.unibuc.hello.data.entity.*;
import ro.unibuc.hello.data.repository.*;
import ro.unibuc.hello.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final DeepseekService deepseekService;
    private final ConversationRepository conversationRepository;
    private final CharacterRepository characterRepository;

    public ConversationResponse converse(ConversationRequest dto) {
        // 1) Ensure character exists
        characterRepository.findById(dto.getCharacterId())
                .orElseThrow(() -> new IllegalArgumentException("Character not found"));

        // 2) Load or create conversation
        Conversation convo = conversationRepository
                .findByUserIdAndCharacterId(dto.getUserId(), dto.getCharacterId())
                .orElseGet(() -> Conversation.builder()
                        .userId(dto.getUserId())
                        .characterId(dto.getCharacterId())
                        .build());

        // 3) Append user message
        String userContent = String.join(" ", dto.getMessage());
        Message userMsg = Message.builder()
                .sender("user")
                .timestamp(LocalDateTime.now())
                .content(userContent)
                .build();
        convo.getMessages().add(userMsg);

        // 4) Build prompt (last 20)
        List<Message> last20 = convo.getMessages()
                                    .subList(Math.max(convo.getMessages().size() - 20, 0), convo.getMessages().size());
        List<Map<String, String>> prompt = new ArrayList<>();
        for (Message m : last20) {
            prompt.add(Map.of("role", m.getSender(), "content", m.getContent()));
        }

        // 5) Call LLM
        String assistantReply = deepseekService.chat(prompt);

        // 6) Persist assistant message
        Message assistantMsg = Message.builder()
                .sender("assistant")
                .timestamp(LocalDateTime.now())
                .content(assistantReply)
                .build();
        convo.getMessages().add(assistantMsg);
        conversationRepository.save(convo);

        // 7) Return DTO
        return ConversationResponse.builder()
                .conversationId(convo.getId())
                .assistantMessage(assistantReply)
                .timestamp(assistantMsg.getTimestamp())
                .build();
    }
}
