package com.example.reciclArte_backend.service;

import com.example.reciclArte_backend.entity.User;
import com.example.reciclArte_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        userRepository.findByEmail(user.getEmail())
            .ifPresent(existing -> {
                throw new RuntimeException("The user is already registered");
            });
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}