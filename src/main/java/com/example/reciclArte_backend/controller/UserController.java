package com.example.reciclArte_backend.controller;

import com.example.reciclArte_backend.entity.User;
import com.example.reciclArte_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173") 
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);

            if (registeredUser != null) {
                return ResponseEntity.ok(registeredUser);
            } else {
                return ResponseEntity.badRequest().body("The user could not be registered.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        Optional<User> loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());

        if (loggedInUser.isPresent()) {
            return ResponseEntity.ok(loggedInUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(@RequestParam String email) {
        Optional<User> user = userService.getUserByEmail(email);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
