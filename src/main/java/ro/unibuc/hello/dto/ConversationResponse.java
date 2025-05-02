package ro.unibuc.hello.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationResponse {
    private String conversationId;
    private String assistantMessage;
    private LocalDateTime timestamp;
}
