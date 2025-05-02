package ro.unibuc.hello.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Thread between one user and one character. Messages are stored inline for simplicity.
 */
@Document(collection = "conversations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {
    @Id
    private String id;

    private String userId;
    private String characterId;

    /** Chronological transcript (oldest → newest). */
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

    /** Optional system context prompt attached by the user. */
    private String context;
}
