package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "characters")
public class Character {

    @Id
    private String id;

    private String name;
    private String background;
    private String style;
    private String personality;

    // Constructors
    public Character() {}

    // Updated constructor to include personality (optional)
    public Character(String name, String background, String style, String personality) {
        this.name = name;
        this.background = background;
        this.style = style;
        this.personality = personality;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }
}
