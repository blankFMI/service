package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class Character {

    @Id
    private String id;              // MongoDB document ID

    private String name;
    private String background;
    private String style;

    // Constructors
    public Character() {}

    public Character(String name, String background, String style) {
        this.name = name;
        this.background = background;
        this.style = style;
    }

    // Getters and Setters
    public String getId() {
        return id;
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
}
