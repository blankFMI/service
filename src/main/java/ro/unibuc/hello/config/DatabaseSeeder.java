package ro.unibuc.hello.config;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ro.unibuc.hello.data.repository.*;
import ro.unibuc.hello.data.entity.*;

/**
 * DatabaseSeeder – scaffolding only.
 * Flesh it out whenever you need real seed data.
 */
@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    
    private final UserRepository userRepository;   // <-- injected bean

    /** Called from Application.main() to pre-populate collections. */
    public void seedData() {
        // TODO: inject repositories and write seed records here
        // Example:
        if (userRepository.count() == 0) {         // avoid duplicates when restarting
            userRepository.save(
                User.builder()
                    .username("alice")
                    .email("alice@example.com")
                    .password("secret")            // hash in real life!
                    .build()
            );
        }
    }
}
