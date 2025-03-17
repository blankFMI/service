package ro.unibuc.hello.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:./deepseek-secret.properties")
public class DeepseekConfig {
    // This configuration class loads properties from deepseek-secret.properties.
}
