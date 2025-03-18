package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class SessionService {
    // In-memory token store; keys can be user IDs, values are tokens.
    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    // Add a token for a user.
    public void addToken(String userId, String token) {
        sessions.put(userId, token);
    }

    // Check if the provided token exists.
    public boolean isValidToken(String token) {
        return sessions.containsValue(token);
    }

    // Remove a token (for logout, etc.)
    public void removeToken(String userId) {
        sessions.remove(userId);
    }

    // Remove a token by its value
    public void removeTokenByValue(String token) {
        // This will remove any (and all) entries that match the token value
        sessions.entrySet().removeIf(entry -> entry.getValue().equals(token));
    }
}
