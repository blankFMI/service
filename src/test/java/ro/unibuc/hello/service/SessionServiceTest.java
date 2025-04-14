package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {

    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        sessionService = new SessionService();
    }

    @Test
    void testAddTokenAndIsValidToken() {
        String userId = "user123";
        String token = "tokenABC";

        sessionService.addToken(userId, token);

        assertTrue(sessionService.isValidToken(token), "Token should be valid after adding");
    }

    @Test
    void testRemoveToken() {
        String userId = "user456";
        String token = "tokenDEF";

        sessionService.addToken(userId, token);
        sessionService.removeToken(userId);

        assertFalse(sessionService.isValidToken(token), "Token should be invalid after removing by userId");
    }

    @Test
    void testRemoveTokenByValue() {
        String userId = "user789";
        String token = "tokenXYZ";

        sessionService.addToken(userId, token);
        sessionService.removeTokenByValue(token);

        assertFalse(sessionService.isValidToken(token), "Token should be invalid after removing by value");
    }

    @Test
    void testIsValidTokenWithInvalidToken() {
        assertFalse(sessionService.isValidToken("nonexistentToken"), "Nonexistent token should be invalid");
    }
}
