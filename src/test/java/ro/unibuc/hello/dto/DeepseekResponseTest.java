package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DeepseekResponseTest {

    @Test
    void testGetReplyReturnsNullWhenChoicesIsNull() {
        DeepseekResponse response = new DeepseekResponse();
        response.setChoices(null);
        assertNull(response.getReply(), "Expected getReply() to return null if choices is null");
    }

    @Test
    void testGetReplyReturnsNullWhenChoicesIsEmpty() {
        DeepseekResponse response = new DeepseekResponse();
        response.setChoices(Collections.emptyList());
        assertNull(response.getReply(), "Expected getReply() to return null if choices is empty");
    }

    @Test
    void testGetReplyReturnsNullWhenFirstChoiceMessageIsNull() {
        DeepseekResponse response = new DeepseekResponse();
        // Create a choice without a message
        DeepseekResponse.Choice choice = new DeepseekResponse.Choice();
        choice.setMessage(null);
        response.setChoices(List.of(choice));
        assertNull(response.getReply(), "Expected getReply() to return null if first choice's message is null");
    }

    @Test
    void testGetReplyReturnsMessageContent() {
        DeepseekResponse response = new DeepseekResponse();
        // Create a choice with a valid message
        DeepseekResponse.Choice choice = new DeepseekResponse.Choice();
        DeepseekResponse.Message msg = new DeepseekResponse.Message();
        msg.setContent("Assistant reply");
        choice.setMessage(msg);
        response.setChoices(List.of(choice));
        assertEquals("Assistant reply", response.getReply(), "Expected getReply() to return the content of the first message");
    }

    @Test
    void testSetReplyUpdatesMessageContent() {
        DeepseekResponse response = new DeepseekResponse();
        // Create a choice with an initial message
        DeepseekResponse.Choice choice = new DeepseekResponse.Choice();
        DeepseekResponse.Message msg = new DeepseekResponse.Message();
        msg.setContent("Original reply");
        choice.setMessage(msg);
        response.setChoices(List.of(choice));

        // Use setReply() to update the message content
        response.setReply("Updated reply");
        assertEquals("Updated reply", response.getReply(), "Expected the message content to be updated");
    }

    @Test
    void testGettersAndSettersForBasicFields() {
        DeepseekResponse response = new DeepseekResponse();
        response.setId("1234");
        response.setObject("chat.completion");
        response.setCreated(1678901234L);
        response.setModel("deepseek-model-v1");
        response.setSystem_fingerprint("fp-test");

        assertEquals("1234", response.getId());
        assertEquals("chat.completion", response.getObject());
        assertEquals(1678901234L, response.getCreated());
        assertEquals("deepseek-model-v1", response.getModel());
        assertEquals("fp-test", response.getSystem_fingerprint());
    }

    @Test
    void testUsageObjectSetAndGet() {
        DeepseekResponse response = new DeepseekResponse();
        DeepseekResponse.Usage usage = new DeepseekResponse.Usage();

        usage.setPrompt_tokens(100);
        usage.setCompletion_tokens(50);
        usage.setTotal_tokens(150);
        usage.setPrompt_cache_hit_tokens(80);
        usage.setPrompt_cache_miss_tokens(20);

        DeepseekResponse.PromptTokensDetails details = new DeepseekResponse.PromptTokensDetails();
        details.setCached_tokens(60);
        usage.setPrompt_tokens_details(details);

        response.setUsage(usage);

        assertNotNull(response.getUsage());
        assertEquals(100, response.getUsage().getPrompt_tokens());
        assertEquals(50, response.getUsage().getCompletion_tokens());
        assertEquals(150, response.getUsage().getTotal_tokens());
        assertEquals(80, response.getUsage().getPrompt_cache_hit_tokens());
        assertEquals(20, response.getUsage().getPrompt_cache_miss_tokens());
        assertEquals(60, response.getUsage().getPrompt_tokens_details().getCached_tokens());
    }


}
