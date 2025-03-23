package ro.unibuc.hello.dto;

import java.util.List;

public class DeepseekResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private String system_fingerprint;

    // Returns the assistant's reply from the first choice
    public String getReply() {
        if (choices != null && !choices.isEmpty() && choices.get(0).getMessage() != null) {
            return choices.get(0).getMessage().getContent();
        }
        return null;
    }

    // Setter for reply not needed, but provided for compatibility
    public void setReply(String reply) {
        if (choices != null && !choices.isEmpty() && choices.get(0).getMessage() != null) {
            choices.get(0).getMessage().setContent(reply);
        }
    }

    // Getters and setters for other fields
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getObject() {
        return object;
    }
    public void setObject(String object) {
        this.object = object;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public List<Choice> getChoices() {
        return choices;
    }
    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
    public Usage getUsage() {
        return usage;
    }
    public void setUsage(Usage usage) {
        this.usage = usage;
    }
    public String getSystem_fingerprint() {
        return system_fingerprint;
    }
    public void setSystem_fingerprint(String system_fingerprint) {
        this.system_fingerprint = system_fingerprint;
    }

    // Nested class representing a choice from the API
    public static class Choice {
        private int index;
        private Message message;
        private Object logprobs;
        private String finish_reason;

        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public Message getMessage() {
            return message;
        }
        public void setMessage(Message message) {
            this.message = message;
        }
        public Object getLogprobs() {
            return logprobs;
        }
        public void setLogprobs(Object logprobs) {
            this.logprobs = logprobs;
        }
        public String getFinish_reason() {
            return finish_reason;
        }
        public void setFinish_reason(String finish_reason) {
            this.finish_reason = finish_reason;
        }
    }

    // Nested class representing the message structure
    public static class Message {
        private String role;
        private String content;

        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }

    // Nested class representing usage statistics
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
        private PromptTokensDetails prompt_tokens_details;
        private int prompt_cache_hit_tokens;
        private int prompt_cache_miss_tokens;

        public int getPrompt_tokens() {
            return prompt_tokens;
        }
        public void setPrompt_tokens(int prompt_tokens) {
            this.prompt_tokens = prompt_tokens;
        }
        public int getCompletion_tokens() {
            return completion_tokens;
        }
        public void setCompletion_tokens(int completion_tokens) {
            this.completion_tokens = completion_tokens;
        }
        public int getTotal_tokens() {
            return total_tokens;
        }
        public void setTotal_tokens(int total_tokens) {
            this.total_tokens = total_tokens;
        }
        public PromptTokensDetails getPrompt_tokens_details() {
            return prompt_tokens_details;
        }
        public void setPrompt_tokens_details(PromptTokensDetails prompt_tokens_details) {
            this.prompt_tokens_details = prompt_tokens_details;
        }
        public int getPrompt_cache_hit_tokens() {
            return prompt_cache_hit_tokens;
        }
        public void setPrompt_cache_hit_tokens(int prompt_cache_hit_tokens) {
            this.prompt_cache_hit_tokens = prompt_cache_hit_tokens;
        }
        public int getPrompt_cache_miss_tokens() {
            return prompt_cache_miss_tokens;
        }
        public void setPrompt_cache_miss_tokens(int prompt_cache_miss_tokens) {
            this.prompt_cache_miss_tokens = prompt_cache_miss_tokens;
        }
    }

    // Nested class for detailed prompt token usage
    public static class PromptTokensDetails {
        private int cached_tokens;

        public int getCached_tokens() {
            return cached_tokens;
        }
        public void setCached_tokens(int cached_tokens) {
            this.cached_tokens = cached_tokens;
        }
    }
}
