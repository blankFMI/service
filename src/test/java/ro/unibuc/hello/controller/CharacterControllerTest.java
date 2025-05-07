package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.Character;
import ro.unibuc.hello.service.CharacterService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CharacterControllerTest {

    @Mock
    private CharacterService characterService;

    @InjectMocks
    private CharacterController characterController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Used to serialize test data into JSON

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks before each test
        mockMvc = MockMvcBuilders.standaloneSetup(characterController).build();
    }

//     @Test
//     public void testCreateCharacter() throws Exception {
//         // Arrange: Create a character input (no ID since it hasn't been saved yet)
//         Character inputCharacter = new Character(null, "Alice", "Loves adventure", "Friendly");

//         // Simulate the service assigning an ID upon creation
//         Character createdCharacter = new Character("1", "Alice", "Loves adventure", "Friendly");

//         // Mock the service call to return the created character
//         when(characterService.createCharacter(any(Character.class))).thenReturn(createdCharacter);

//         // Act & Assert: Perform a POST request and verify the expected JSON response
//         mockMvc.perform(post("/characters")
//                 .content(objectMapper.writeValueAsString(inputCharacter))
//                 .contentType(MediaType.APPLICATION_JSON))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$.id").value("1"))
//             .andExpect(jsonPath("$.personality").value("Friendly"))
//             .andExpect(jsonPath("$.background").value("Loves adventure"));
//     }

//     @Test
//     public void testGetAllCharacters() throws Exception {
//         // Arrange: Create a list of characters to be returned by the service
//         Character char1 = new Character("1", "Alice", "Origin story 1", "Cheerful");
//         Character char2 = new Character("2", "Bob", "Origin story 2", "Mysterious");

//         List<Character> characters = Arrays.asList(char1, char2);

//         // Mock the service call
//         when(characterService.getAllCharacters()).thenReturn(characters);

//         // Act & Assert: Perform a GET request and verify the JSON response
//         mockMvc.perform(get("/characters"))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$[0].id").value("1"))
//             .andExpect(jsonPath("$[0].personality").value("Cheerful"))
//             .andExpect(jsonPath("$[0].background").value("Origin story 1"))
//             .andExpect(jsonPath("$[1].id").value("2"))
//             .andExpect(jsonPath("$[1].personality").value("Mysterious"))
//             .andExpect(jsonPath("$[1].background").value("Origin story 2"));
//     }
}