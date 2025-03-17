package ro.unibuc.hello.api;

/**
 * A simple interface for interacting with external conversation APIs.
 * 
 * This interface abstracts the implementation details of any API (such as Deepseek)
 * that processes a conversation context and returns an AI response.
 */
public interface ConversationAPIClient {

    /**
     * Sends the conversation context to the external API and retrieves the AI response.
     *
     * @param conversationContext the conversation history or context as a String
     * @return the response from the AI as a String
     */
    String getResponse(String conversationContext);
}
