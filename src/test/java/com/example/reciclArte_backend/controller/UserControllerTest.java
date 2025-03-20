package com.example.reciclArte_backend.controller;

import com.example.reciclArte_backend.entity.User;
import com.example.reciclArte_backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testRegisterUser_Success() throws Exception {

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userService.registerUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    void testRegisterUser_Failure() throws Exception {

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userService.registerUser(any(User.class))).thenThrow(new RuntimeException("El usuario ya está registrado"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Server Error: El usuario ya está registrado"));

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    void testLoginUser_Success() throws Exception {
      
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userService.loginUser("test@example.com", "password")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).loginUser("test@example.com", "password");
    }

    @Test
    void testLoginUser_InvalidCredentials() throws Exception {
   
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("wrongpassword");

        when(userService.loginUser("test@example.com", "wrongpassword")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Incorrect credentials"));

        verify(userService, times(1)).loginUser("test@example.com", "wrongpassword");
    }

    @Test
    void testGetCurrentUser_Success() throws Exception {
  
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userService.getUserByEmail(email)).thenReturn(Optional.of(user));


        mockMvc.perform(get("/api/auth/current-user")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));

        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void testGetCurrentUser_NotFound() throws Exception {
        String email = "nonexistent@example.com";

        when(userService.getUserByEmail(email)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/auth/current-user")
                        .param("email", email))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found")); 

        verify(userService, times(1)).getUserByEmail(email);
    }
}