package ro.unibuc.hello.service.llm;

/** Immutable prompt element. */
public record ChatMessage(Role role, String content) {
    public enum Role { SYSTEM, USER, ASSISTANT }
}