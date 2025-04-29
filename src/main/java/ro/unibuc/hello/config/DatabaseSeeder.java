package ro.unibuc.hello.config;

import org.springframework.stereotype.Component;

/**
 * <h2>DatabaseSeeder (template)</h2>
 * <p>
 * A bare‑bones skeleton you can fill in with whatever seeding logic your new
 * domain model needs.  Nothing here will touch MongoDB or any other service,
 * so the class is totally inert until you flesh it out.
 * </p>
 * <p>
 * How to extend:
 * <ul>
 *   <li>Add repository fields via <code>@RequiredArgsConstructor</code> or
 *       constructor injection.</li>
 *   <li>Implement a <code>CommandLineRunner</code> or
 *       <code>@PostConstruct</code> method as you see fit.</li>
 *   <li>Drop in example builders / fixtures for your own entities.</li>
 * </ul>
 * </p>
 */
@Component
public class DatabaseSeeder {

    // -------------------------
    //  Inject your repositories
    // -------------------------
    // private final UserRepository userRepository;
    // private final GameRepository gameRepository;

    // -------------------------
    //  Add seeding methods here
    // -------------------------
    // @PostConstruct
    // public void seed() {
    //     // Your seeding logic goes here
    // }
}
