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
                System.out.println("Token: " + token);
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
}
