package ro.unibuc.hello.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

public class AppConfigTest {

    @Test
    void testRestTemplateBean() {
        // Load the context with our configuration class
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        // Retrieve the RestTemplate bean from the context
        RestTemplate restTemplate = context.getBean(RestTemplate.class);
        
        // Assert that the bean was created (i.e. it is not null)
        assertNotNull(restTemplate, "RestTemplate bean should be created by AppConfig");
    }
}
