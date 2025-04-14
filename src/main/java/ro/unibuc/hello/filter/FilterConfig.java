package ro.unibuc.hello.filter;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.unibuc.hello.filter.TokenValidationFilter;
import ro.unibuc.hello.service.SessionService;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> tokenValidationFilter(SessionService sessionService) {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenValidationFilter(sessionService));
        registrationBean.addUrlPatterns("/users/*"); // or "/*" if it applies to everything
        registrationBean.setOrder(1); // Optional: define filter priority
        return registrationBean;
    }
}
