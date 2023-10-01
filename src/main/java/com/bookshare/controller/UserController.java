package com.bookshare.controller;

import com.bookshare.dto.UserRegistrationRequest;
import com.bookshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import com.bookshare.model.User;
import com.bookshare.repository.UserRepository;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        try {
            HashMap<String, String> response = userService.registerUser(registrationRequest.getEmail(), registrationRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRegistrationRequest loginRequest) {
        try {
            HashMap<String, String> response = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getUsername() == null) {
                    response.put("isFirstLogin", "true");
                } else {
                    response.put("isFirstLogin", "false");
                }
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/update-username")
    public ResponseEntity<?> updateUsername(@RequestBody Map<String, String> payload) {
        try {
            // Extract username and email (or other identifier) from payload
            String username = payload.get("username");
            String email = payload.get("email");  // Assuming email is the user identifier here

            // Call the UserService method to update the username
            HashMap<String, String> response = userService.updateUsername(email, username);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
