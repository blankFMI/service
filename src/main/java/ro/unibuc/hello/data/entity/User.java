package ro.unibuc.hello.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Application account – backed by a MongoDB collection called "users".
 */
@Document(collection = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String username;
    private String email;
    private String password;

    /** BASIC by default; upgraded at purchase workflow. */
    @Builder.Default
    private AccountType accountType = AccountType.BASIC;
}