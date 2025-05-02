package ro.unibuc.hello.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * One line in a chat transcript.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    /** "user" or "assistant" */
    private String sender;
    private LocalDateTime timestamp;
    private String content;
}
