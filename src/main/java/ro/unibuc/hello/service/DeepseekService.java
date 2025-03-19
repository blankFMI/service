package ro.unibuc.hello.service;

import ro.unibuc.hello.dto.DeepseekRequest;
import ro.unibuc.hello.dto.DeepseekResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeepseekService {

    private final RestTemplate restTemplate;
    // Replace with your actual Deepseek API base URL
    private final String baseUrl = "http://api.deepseek.example.com";

    public DeepseekService() {
        this.restTemplate = new RestTemplate();
    }

    public DeepseekResponse getCharacterResponse(DeepseekRequest request) {
        // Adjust the endpoint path as needed
        String url = baseUrl + "/conversation";
        return restTemplate.postForObject(url, request, DeepseekResponse.class);
    }
}
