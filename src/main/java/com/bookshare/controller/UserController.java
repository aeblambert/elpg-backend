package com.bookshare.controller;

import com.bookshare.dto.NewUserRequest;
import com.bookshare.dto.UserLoginRequest;
import com.bookshare.dto.UserRegistrationRequest;
import com.bookshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
        try {
            HashMap<String, String> response = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                response.put("isFirstLogin", String.valueOf(user.getNickname() == null));
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/new-user")
    public ResponseEntity<?> newUser(@RequestBody NewUserRequest newUserRequest) {
        try {
            String nickname = newUserRequest.getNickname();
            String email = newUserRequest.getEmail();
            String district = newUserRequest.getDistrict();
            boolean consentToShare = newUserRequest.getConsent();

            HashMap<String, String> response = userService.newUser(email, nickname, district, consentToShare);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
