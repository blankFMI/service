package ro.unibuc.hello.data.repository;

import ro.unibuc.hello.data.entity.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<Character, String> {
}
