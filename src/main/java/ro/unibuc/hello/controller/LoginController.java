package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.User;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.LoginResponse;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    public LoginController(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Find user by username
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username");
        }
        // Check password (for demo purposes, comparing plain text; use hashing in production)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
        // Generate a simple token (UUID)
        String token = UUID.randomUUID().toString();
        // Store token in the session service
        sessionService.addToken(user.getId(), token);
        // Return token in the response
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/secure-data")
    public ResponseEntity<?> getSecureData(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
        String token = authorization.substring(7);

        // Validate the token using the sessionService
        if (!sessionService.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // If token is valid, proceed with returning secure data
        return ResponseEntity.ok("This is secured data");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or invalid token");
        }
        String token = authorization.substring(7);
    
        // Remove the token from the session store
        sessionService.removeTokenByValue(token);
    
        return ResponseEntity.ok("Logged out successfully");
    }


}
