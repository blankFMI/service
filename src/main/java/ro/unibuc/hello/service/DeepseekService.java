package ro.unibuc.hello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DeepseekService {

    private final RestTemplate restTemplate;

    @Value("${llm.deepseek.base-url:https://api.deepseek.com}")
    private String baseUrl;

    @Value("${llm.deepseek.api-key}")
    private String apiKey;

    /**
     * Sends the chat prompt and returns the assistant's answer text.
     */
    public String chat(List<Map<String, String>> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", "deepseek-chat",
                "messages", messages
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        Map<?, ?> resp = restTemplate.postForObject(baseUrl + "/v1/chat/completions", entity, Map.class);
        // naive parsing – assumes first choice, top‑level structure compatible with OpenAI
        return ((Map<?,?>)((List<?>)resp.get("choices")).get(0)).get("message").toString();
    }
}
