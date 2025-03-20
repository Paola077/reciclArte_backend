package com.example.reciclArte_backend.service;

import com.example.reciclArte_backend.entity.User;
import com.example.reciclArte_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.registerUser(user));
        assertEquals("The user is already registered", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginUser_Success() {

        String email = "test@example.com";
        String password = "password";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> loggedInUser = userService.loginUser(email, password);

        assertTrue(loggedInUser.isPresent());
        assertEquals(user, loggedInUser.get());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testLoginUser_UserNotFound() {

        String email = "nonexistent@example.com";
        String password = "password";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> loggedInUser = userService.loginUser(email, password);

        assertFalse(loggedInUser.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testLoginUser_InvalidPassword() {
        String email = "test@example.com";
        String password = "wrongpassword";

        User user = new User();
        user.setEmail(email);
        user.setPassword("correctpassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> loggedInUser = userService.loginUser(email, password);

        assertFalse(loggedInUser.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testGetUserByEmail_Success() {
        String email = "test@example.com";

        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByEmail(email);

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testGetUserByEmail_NotFound() {

        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserByEmail(email);

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }
}