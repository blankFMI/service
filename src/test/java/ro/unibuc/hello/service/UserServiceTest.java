package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.data.AccountType;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testCreateUser() {
        User user = new User("testUser", "test@example.com", "pass123", AccountType.BASIC);
        when(userRepository.save(user)).thenReturn(user);

        User created = userService.createUser(user);
        assertEquals("testUser", created.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
                new User("u1", "u1@example.com", "pass1", AccountType.BASIC),
                new User("u2", "u2@example.com", "pass2", AccountType.BASIC)
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserById_Success() {
        User user = new User("u1", "u1@example.com", "pass1", AccountType.BASIC);
        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        User result = userService.getUserById("123");
        assertEquals("u1", result.getUsername());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById("404")).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> userService.getUserById("404"));
        assertTrue(ex.getMessage().contains("User not found with id"));
    }

    @Test
    void testUpdateUser_Success() {
        User existingUser = new User("oldUser", "old@mail.com", "oldpass", AccountType.BASIC);
        User updates = new User("newUser", "new@mail.com", "newpass", AccountType.PREMIUM);

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User updated = userService.updateUser("1", updates);

        assertEquals("newUser", updated.getUsername());
        assertEquals("new@mail.com", updated.getEmail());
        assertEquals("newpass", updated.getPassword());
        assertEquals(AccountType.PREMIUM, updated.getAccountType());
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        User update = new User("someone", "mail", "pw", AccountType.BASIC);
        Exception ex = assertThrows(RuntimeException.class, () -> userService.updateUser("999", update));
        assertTrue(ex.getMessage().contains("User not found with id"));
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById("123")).thenReturn(true);
        doNothing().when(userRepository).deleteById("123");

        userService.deleteUser("123");
        verify(userRepository).deleteById("123");
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById("999")).thenReturn(false);

        Exception ex = assertThrows(RuntimeException.class, () -> userService.deleteUser("999"));
        assertTrue(ex.getMessage().contains("User not found with id"));
    }
}
