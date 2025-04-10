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
}
