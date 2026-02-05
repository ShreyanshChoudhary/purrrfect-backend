package com.Purrrfect.Service;

import com.Purrrfect.Model.User;
import com.Purrrfect.Repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.util.List;

@Service
public class UserService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;  // PasswordEncoder will be injected automatically
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    // Constructor injection of PasswordEncoder
    @Autowired
    public UserService(UserRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;  // No need to cast
    }

    // Log in using AuthenticationManager
    public boolean login(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            return authentication.isAuthenticated(); // Login successful
        } catch (AuthenticationException e) {
            logger.error("Login failed: {}", e.getMessage());
            return false; // Invalid credentials
        }
    }

    // Fetch all users - Optional for admin or debugging purposes (not required for signup/login)
    public List<User> getUsers() {
        logger.info("Fetching all users");
        return repo.findAll();
    }

    // Fetch a user by userId - Can be useful for login (fetch user based on some unique identifier)
    public User getUserById(Integer userId) {
        return repo.findById(userId).orElseGet(() -> {
            logger.warn("User with userId - {} not found", userId);
            return null;  // No default user created, just return null
        });
    }

    // Add a new user for signup
    public void addUser(@Valid User user) {
        user.setUserId(null);  // Ensure that the userId is not sent in the request (it will be auto-generated)
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
        logger.info("User with userId - {} added successfully", user.getUserId());
    }

    // Method to check if a user already exists by username - Useful for signup validation
    public User getUserByUsername(String name) {
        return repo.findByName(name).orElse(null);  // Return null if user not found
    }

    // Method to check if a user exists by email - Useful for login validation
    public User getUserByEmail(String email) {
        return repo.findByEmail(email).orElse(null);  // Return null if user not found
    }

    // Use PasswordEncoder to validate user credentials during sign-in (username/email and password)

}
