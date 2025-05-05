package ro.unibuc.hello.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.entity.User;
import ro.unibuc.hello.data.repository.UserRepository;
import ro.unibuc.hello.service.SessionService;

import java.util.UUID;

@RestController
@RequestMapping("/auth") // /api/auth/login | logout | me
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final SessionService sessions; // simple in‑memory token store

    record LoginRequest(String username, String password) {}
    record LoginResponse(String token, String userId) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User u = userRepo.findByUsername(req.username()).orElse(null);
        if (u == null || !u.getPassword().equals(req.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials");
        }
        String token = UUID.randomUUID().toString();
        sessions.addToken(u.getId(), token);
        return ResponseEntity.ok(new LoginResponse(token, u.getId()));
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            sessions.removeTokenByValue(auth.substring(7));
        }
    }

    @GetMapping("/secure-data")
    public String secure() { return "You are authenticated"; }
}
