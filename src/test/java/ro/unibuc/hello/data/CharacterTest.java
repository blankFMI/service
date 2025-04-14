package ro.unibuc.hello.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    void testDefaultConstructorAndSetters() {
        Character character = new Character();
        character.setId("abc123");
        character.setName("Detective Noir");
        character.setBackground("Rainy Bucharest alley");
        character.setStyle("Mysterious and vintage");
        character.setPersonality("Sarcastic and observant");

        assertEquals("abc123", character.getId());
        assertEquals("Detective Noir", character.getName());
        assertEquals("Rainy Bucharest alley", character.getBackground());
        assertEquals("Mysterious and vintage", character.getStyle());
        assertEquals("Sarcastic and observant", character.getPersonality());
    }

    @Test
    void testParameterizedConstructor() {
        Character character = new Character(
                "Lara",
                "Lost jungle temple",
                "Adventurer",
                "Brave and curious"
        );

        assertNull(character.getId(), "ID should be null unless set explicitly");
        assertEquals("Lara", character.getName());
        assertEquals("Lost jungle temple", character.getBackground());
        assertEquals("Adventurer", character.getStyle());
        assertEquals("Brave and curious", character.getPersonality());
    }
}
