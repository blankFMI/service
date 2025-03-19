package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    // Find all conversations for a given user
    List<Conversation> findByUserId(String userId);
    
    // Find all conversations for a given character
    List<Conversation> findByCharacterId(String characterId);
}
