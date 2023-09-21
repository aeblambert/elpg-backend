package com.bookshare.service;

import java.util.HashMap;
import com.bookshare.model.User;
import com.bookshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Implement methods for user-related operations, e.g., registration, finding users, etc.

    public HashMap<String, String> registerUser(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "User " + email + " already exists.  Please log in!");
            return response;
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setHashedPassword(passwordEncoder.encode(password));
            userRepository.save(newUser);
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "User " + email + " registered successfully.  Please log in!");
            return response;
        }
    }
    public HashMap<String, String> loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getHashedPassword())) {
                // Generate a session token (we'll use a simple one for now, you can replace this with JWT later)
                String sessionToken = UUID.randomUUID().toString();
                // In a real-world application, you'd store this token in a more secure way.
                user.setSessionToken(sessionToken);
                userRepository.save(user);
                HashMap<String, String> response = new HashMap<>();
                response.put("message", "Login successful!");
                response.put("sessionToken", sessionToken);
                return response;
            } else {
                HashMap<String, String> response = new HashMap<>();
                response.put("message", "Invalid password!");
                return response;
            }
        } else {
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "User not found!");
            return response;
        }
    }
}
