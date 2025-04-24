package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ro.unibuc.hello.data.Character;
import ro.unibuc.hello.data.CharacterRepository;
import ro.unibuc.hello.data.ConversationRepository;
import ro.unibuc.hello.dto.ConversationRequest;
import ro.unibuc.hello.dto.ConversationResponse;
import ro.unibuc.hello.dto.DeepseekResponse;
import ro.unibuc.hello.service.DeepseekService;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for {@link ConversationController} that verifies the happy path of posting a
 * message to /conversations/talk. We stub {@link DeepseekService} so the test does not hit the
 * external API.
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
@Tag("IntegrationTest")
public class ConversationControllerIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    @DynamicPropertySource
    static void registerMongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @SuppressWarnings("removal")
    @MockBean
    private DeepseekService deepseekService; // mocked to avoid real HTTP calls

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void cleanUp() {
        conversationRepository.deleteAll();
        characterRepository.deleteAll();
    }

    @Test
    void shouldConverseWithCharacter() throws Exception {
        // 1. Prepare and store a character in MongoDB
        Character character = new Character("Rick", "Mad scientist", "Sarcastic", "Genius");
        characterRepository.save(character); // id will be generated

        // 2. Stub DeepseekService to return a predictable reply
        DeepseekResponse mockResponse = Mockito.mock(DeepseekResponse.class);
        Mockito.when(mockResponse.getReply()).thenReturn("Wubba Lubba Dub Dub!");
        Mockito.when(deepseekService.getCharacterResponse(any())).thenReturn(mockResponse);

        // 3. Craft the conversation request payload
        ConversationRequest request = new ConversationRequest(null, "user123", character.getId(), Collections.singletonList("Hello there"));

        // 4. Perform POST /conversations/talk
        MvcResult result = mockMvc.perform(post("/conversations/talk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reply").value("Wubba Lubba Dub Dub!"))
                .andReturn();

        // 5. Ensure the conversation is persisted
        assertThat(conversationRepository.count()).isGreaterThan(0);

        // 6. Optionally deserialize and inspect response
        ConversationResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ConversationResponse.class);
        assertThat(response.getReply()).isEqualTo("Wubba Lubba Dub Dub!");
    }
}
