package ro.unibuc.hello.service;

import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // Injected encoder

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User updateUser(String id, User updatedUser) {
        // Fetch the existing user from the database
        Optional<User> existingOpt = userRepository.findById(id);
        if (existingOpt.isPresent()) {
            User existingUser = existingOpt.get();
            // Update fields if provided in updatedUser
            if (updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                // Encrypt the new password
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            if (updatedUser.getAccountType() != null) {
                existingUser.setAccountType(updatedUser.getAccountType());
            }
            return userRepository.save(existingUser);
        } else {
            // In a real application, you might throw a custom exception here.
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
