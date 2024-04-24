package com.example.movinProject.main.user.controller;

import com.example.movinProject.config.SpringSecurity.security.DatabaseUserDetailsService;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.domain.user.repository.UserRepository;
import com.example.movinProject.main.user.dto.UserDto;
import com.example.movinProject.main.user.dto.UserRegisterRequest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseUserDetailsService userDetailsService;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }
    @Test
    @DisplayName("Register a new user successfully")
    void testRegister() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUserName("testUser");
        request.setPassword("testPassword");
        request.setEmail("test@example.com");

        ResponseEntity<UserDto> response = userController.register(request);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("testUser", response.getBody().getName());

        // Verify user is saved in the repository
        Optional<User> savedUser = userRepository.findByUserName("testUser");
        assertTrue(savedUser.isPresent());
        assertEquals("test@example.com", savedUser.get().getEmail());
    }

    @Test
    @DisplayName("Attempt to register with existing username")
    void testRegisterExistingUser() {
        // First, create a user
        User existingUser = User.create("testUser", "password123", "test@example.com");
        userRepository.save(existingUser);

        // Try to register the same username
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUserName("testUser");
        request.setPassword("newPassword123");
        request.setEmail("test2@example.com");

        ResponseEntity<UserDto> response = userController.register(request);
        assertEquals(400, response.getStatusCodeValue()); // Expect a Bad Request response
    }

    @Test
    void testGetUserDetailsExistingUser() {

        User newUser = User.create("testUser", "password123", "test@example.com");
        userRepository.save(newUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        ResponseEntity<UserDto> response = userController.getUserDetails(userDetails);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("testUser", response.getBody().getName());
    }

    @Test
    void testGetUserDetailsNonExistentUser() {

        ResponseEntity<UserDto> response = userController.getUserDetails(null);
        assertEquals(403, response.getStatusCodeValue());

    }
}