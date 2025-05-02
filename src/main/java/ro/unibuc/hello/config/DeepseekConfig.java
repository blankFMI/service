package ro.unibuc.hello.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DeepseekConfig {

    @Bean
    WebClient deepseekClient(@Value("${deepseek.base-url:https://api.deepseek.com}") String base,
                             @Value("${deepseek.api-key}") String key) {
        return WebClient.builder()
                .baseUrl(base)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + key)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "deepseekCB")
    CircuitBreaker deepseekCB() {
        return CircuitBreaker.ofDefaults("deepseek");
    }
}
