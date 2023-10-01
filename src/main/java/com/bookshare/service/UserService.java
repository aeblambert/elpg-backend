package com.bookshare.service;

import java.util.HashMap;
import com.bookshare.model.User;
import com.bookshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String secret;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${jwt.secret}") String secret) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.secret = secret;
    }

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
                Algorithm algorithm = Algorithm.HMAC256(secret);
                String token = JWT.create()
                        .withClaim("email", user.getEmail())
                        .sign(algorithm);
               // userRepository.save(user);  // maybe bring this back later if storing other attributes for user
                HashMap<String, String> response = new HashMap<>();
                response.put("message", "Login successful!");
                response.put("jwtToken", token);
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
    public HashMap<String, String> updateUsername(String email, String newUsername) throws Exception {
        HashMap<String, String> response = new HashMap<>();

        // Validate username length
        if (newUsername.length() > 10) {
            response.put("message", "Username must be a maximum of 10 characters");
            return response;
        }

        // 1. Validate that username is unique
        Optional<User> userWithNewUsername = userRepository.findByUsername(newUsername);
        if (userWithNewUsername.isPresent()) {
            response.put("message", "Username already exists");
            return response;
        }

        // 2. Find the user by email
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new Exception("User with given email not found.");
        }

        // 3. Update the username in the database for the user identified by the email
        User user = optionalUser.get();
        user.setUsername(newUsername);
        userRepository.save(user);

        // 4. Return a response indicating success
        response.put("message", "Username updated successfully.");
        return response;
    }
}
