package ro.unibuc.hello.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeepseekConversationClient implements ConversationAPIClient {

    private final RestTemplate restTemplate;

    @Value("${deepseek.api.url}")
    private String deepseekApiUrl;
    
    @Value("${deepseek.api.key}")
    private String deepseekApiKey;

    public DeepseekConversationClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public String getResponse(String conversationContext) {
        // Build the payload required by the Deepseek API.
        Map<String, Object> payload = new HashMap<>();
        payload.put("context", conversationContext);
        // You can add additional parameters here if needed.

        // Prepare HTTP headers.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + deepseekApiKey);

        // Create the request entity with payload and headers.
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        // Make the POST request to the Deepseek API.
        ResponseEntity<String> response = restTemplate.postForEntity(deepseekApiUrl, requestEntity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            // Handle error responses appropriately.
            throw new RuntimeException("Deepseek API error: " + response.getStatusCode());
        }
    }
}
