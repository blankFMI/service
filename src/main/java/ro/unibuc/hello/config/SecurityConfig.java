package ro.unibuc.hello.config;

import ro.unibuc.hello.filter.TokenValidationFilter;
import ro.unibuc.hello.service.SessionService;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public RestTemplate restTemplate() { return new RestTemplate(); }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SessionService sessionService) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated())
            .addFilterBefore(new TokenValidationFilter(sessionService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
