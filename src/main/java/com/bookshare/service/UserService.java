package com.bookshare.service;

import java.util.HashMap;
import com.bookshare.model.User;
import com.bookshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
            HashMap<String, String> response = new HashMap<>();
            if (passwordEncoder.matches(password, user.getHashedPassword())) {
                try {
                    Algorithm algorithm = Algorithm.HMAC256(secret);
                    String token = JWT.create()
                            .withClaim("email", user.getEmail())
                            .withClaim("nickname", user.getNickname())
                            .sign(algorithm);
                // userRepository.save(user);  // maybe bring this back later if storing other attributes for user
                    response.put("message", "Login successful!");
                    response.put("jwtToken", token);
                    response.put("nickname", user.getNickname());
                    return response;
                } catch (Exception e) {
                    System.out.println(e);
                    response.put("message", "Invalid JWTToken generated");
                    return response;
                }
            } else {
                System.out.println("Invalid password");
                response = new HashMap<>();
                response.put("message", "Invalid password!");
                return response;
            }
        } else {
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "Email not registered!");
            return response;
        }
    }
    public ResponseEntity<HashMap<String, String>> newUser(String email, String nickname, String district, boolean consentToShare) throws Exception {
        HashMap<String, String> response = new HashMap<>();

        Optional<User> userWithNewNickname = userRepository.findByNickname(nickname);
        if (userWithNewNickname.isPresent()) {
            response.put("message", "That nickname is already taken; choose another");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new Exception("User with email address " + email + " does not exist");
        } else {
            User user = optionalUser.get();
            if (user.getNickname() != null) {
                throw new Exception("Nickname '" + user.getNickname() + "' has already been assigned to this email address");
            } else {
                user.setNickname(nickname);
                user.setDistrict(district);
                user.setConsentToShare(consentToShare);
                userRepository.save(user);
                response.put("message", "User information updated successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }
}
