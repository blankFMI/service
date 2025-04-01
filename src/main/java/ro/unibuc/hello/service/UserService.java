package ro.unibuc.hello.service;

import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // Here you can add further logic,
        // e.g., password encryption, validations, etc.
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                // For demo purposes, storing plain text; in production, hash the password!
                existingUser.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getAccountType() != null) {
                existingUser.setAccountType(updatedUser.getAccountType());
            }
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User updateUser2(String id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                // For demo purposes, storing plain text; in production, hash the password!
                existingUser.setPassword(updatedUser.getPassword());
            }
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User upgradeUser(String id, User upgratedUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (upgratedUser.getAccountType() != null) {
                existingUser.setAccountType(upgratedUser.getAccountType());
            }
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User downgradeUser(String id, User downgratedUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (downgratedUser.getAccountType() != null) {
                existingUser.setAccountType(downgratedUser.getAccountType());
            }
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // New method to delete a user by id
    public void deleteUser(String id) {
        // Optionally check if user exists before deleting
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    // Additional methods (find by ID, update, delete, etc.) can be added later.
}
