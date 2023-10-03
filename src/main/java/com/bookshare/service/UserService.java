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
        System.out.println("loginUser, user: " + email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        System.out.println("loginUser, user: " + userOptional);
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
                response.put("nickname", user.getNickname());
                System.out.println("Nickname retrieved: " + user.getNickname());
                System.out.println(("JwtToken: " + token));
                return response;
            } else {
                System.out.println("Invalid passwrd");
                HashMap<String, String> response = new HashMap<>();
                response.put("message", "Invalid password!");
                return response;
            }
        } else {
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "Email not registered, please register to continue!!");
            return response;
        }
    }
    public HashMap<String, String> setNickname(String email, String newNickname) throws Exception {
        HashMap<String, String> response = new HashMap<>();

        if (newNickname.length() > 10) {
            response.put("message", "Nickname must be a maximum of 10 characters");
            return response;
        }

        Optional<User> userWithNewNickname = userRepository.findByNickname(newNickname);
        if (userWithNewNickname.isPresent()) {
            response.put("message", "That nickname is already taken; choose another");
            return response;
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new Exception("User with email address " + email + " does not exist");
        } else {
            User user = optionalUser.get();
            if (user.getNickname() != null) {
                throw new Exception("Nickname '" + user.getNickname() + "' has already been assigned to this email address");
            } else {
                user.setNickname(newNickname);
                userRepository.save(user);

                // 4. Return a response indicating success
                response.put("message", "Nickname set successfully");
                return response;
            }
        }
    }
}
