package ro.unibuc.hello.data.repository;

import ro.unibuc.hello.data.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ConversationRepository extends MongoRepository<Conversation, String> {
    Optional<Conversation> findByUserIdAndCharacterId(String userId, String characterId);
}
