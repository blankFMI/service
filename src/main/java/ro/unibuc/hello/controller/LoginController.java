package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.User;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.LoginResponse;
import ro.unibuc.hello.data.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final UserRepository userRepository;
    // For demonstration: in-memory store for session tokens.
    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 1. Find user by username (assumes UserRepository has this method)
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        // 2. Check password (for demonstration, comparing plain text; use hashing in production)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        // 3. Generate a simple token (UUID)
        String token = UUID.randomUUID().toString();
        // 4. Store token in the in-memory sessions map (keyed by user ID)
        sessions.put(user.getId(), token);
        // 5. Return token in the response
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
