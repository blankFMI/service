package ro.unibuc.hello.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * AI persona definition – similar to character.ai.
 */
@Document(collection = "characters")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Character {
    @Id
    private String id;

    private String name;
    private String background;
    private String style;
    private String personality;
}
