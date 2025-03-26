package ro.unibuc.hello.service;

import ro.unibuc.hello.dto.DeepseekRequest;
import ro.unibuc.hello.dto.DeepseekResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DeepseekService {

    private final RestTemplate restTemplate;
    // Replace with your actual Deepseek API base URL
    private final String baseUrl = "https://api.deepseek.com";
    private final String apiKey = "sk-7c5795fa6ca94383869c5f6944f50253";

    public DeepseekService() {
        this.restTemplate = new RestTemplate();
    }

    public DeepseekResponse getCharacterResponse(DeepseekRequest request) {
        // Ensure `messages` is a List<String> before sending the request
        if (request.getMessages() == null || request.getMessages().isEmpty()) {
            throw new IllegalArgumentException("Messages field must not be null or empty");
        }

        // Adjust the endpoint path as needed
        String url = baseUrl + "/v1/chat/completions";
        // Prepare headers including the API key
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<DeepseekRequest> entity = new HttpEntity<>(request, headers);
        // Get the raw JSON response as a String
        String rawResponse = restTemplate.postForObject(url, entity, String.class);
        
        // Output the raw response
        System.out.println("Raw Deepseek API response: " + rawResponse);
        return restTemplate.postForObject(url, entity, DeepseekResponse.class);
    }
}