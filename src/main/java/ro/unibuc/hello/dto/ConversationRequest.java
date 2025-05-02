package ro.unibuc.hello.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String characterId;

    @Size(min = 1)
    private List<@NotBlank String> message; // can send multi‑line messages
}
