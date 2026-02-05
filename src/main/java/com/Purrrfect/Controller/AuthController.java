package com.Purrrfect.Controller;

import com.Purrrfect.DTO.SignupRequest;
import com.Purrrfect.Model.JwtRequest;
import com.Purrrfect.Model.JwtResponse;
import com.Purrrfect.Model.User;
import com.Purrrfect.Service.UserService;
import com.Purrrfect.Security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    // ✅ Test endpoint to verify controller is working
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("✅ AuthController is working at /api/auth");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        logger.debug("🔑 Login attempt for email: {}", request.getEmail());

        try {
            // Test UserDetailsService first
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            logger.info("✅ UserDetails loaded successfully: {}", userDetails.getUsername());

            // Now test authentication
            this.doAuthenticate(request.getEmail(), request.getPassword());

            String token = this.helper.generateToken(userDetails);

            // Get user for additional info
            User user = userService.getUserByEmail(request.getEmail());

            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername())
                    .userId(user != null ? user.getUserId() : null)
                    .build();

            logger.info("✅ Login successful for user: {}", request.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("❌ Login failed for {}: {}", request.getEmail(), e.getMessage());
            throw e;
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            logger.info("👤 Signup request received for: {}", signupRequest.getEmail());
            logger.info("📝 Signup data - Name: {}, Email: {}, Phone: {}",
                    signupRequest.getName(), signupRequest.getEmail(), signupRequest.getPhoneNumber());

            // Validate required fields
            if (signupRequest.getEmail() == null || signupRequest.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Email is required"));
            }

            if (signupRequest.getPassword() == null || signupRequest.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Password is required"));
            }

            if (signupRequest.getName() == null || signupRequest.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Name is required"));
            }

            // Check if user already exists
            User existingUser = userService.getUserByEmail(signupRequest.getEmail());
            if (existingUser != null) {
                logger.warn("❌ Email already registered: {}", signupRequest.getEmail());
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Email already registered"));
            }

            // Create new user - Use the name directly
            User newUser = new User();
            newUser.setName(signupRequest.getName().trim()); // ✅ Use the name directly
            newUser.setEmail(signupRequest.getEmail());
            newUser.setPhoneNumber(signupRequest.getPhoneNumber());
            newUser.setAddress(signupRequest.getAddress());
            newUser.setProfileImageUrl(signupRequest.getProfileImageUrl());

            // Pass raw password - service will encode it
            logger.info("🔐 Raw password received (will be encoded in service)");
            newUser.setPassword(signupRequest.getPassword());

            // Save user
            userService.addUser(newUser);

            // Verify password was saved correctly
            User savedUser = userService.getUserByEmail(signupRequest.getEmail());
            if (savedUser != null) {
                boolean passwordSavedCorrectly = passwordEncoder.matches(
                        signupRequest.getPassword(), savedUser.getPassword());
                logger.info("✅ Password saved correctly: {}", passwordSavedCorrectly);
                logger.info("✅ User created with name: '{}'", savedUser.getName());
            }

            logger.info("✅ User created successfully: {}", signupRequest.getEmail());

            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User created successfully");
            response.put("userId", savedUser != null ? savedUser.getUserId() : null);
            response.put("email", signupRequest.getEmail());
            response.put("name", signupRequest.getName()); // ✅ Return the name

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("❌ Signup failed for {}: {}", signupRequest.getEmail(), e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .body(createErrorResponse("Registration failed: " + e.getMessage()));
        }
    }

    @GetMapping("/test-encoder")
    public ResponseEntity<String> testEncoder() {
        String testPassword = "password@123";
        String encoded = passwordEncoder.encode(testPassword);
        boolean matches = passwordEncoder.matches(testPassword, encoded);

        // Test with the actual stored password
        String storedHash = "$2a$10$fpcjSl/RpMXmWxHWxdbZoutYYu37cVDhHl1CSMCLeYOzBX5TdglJS";
        boolean actualMatches = passwordEncoder.matches("password@123", storedHash);

        String result = "Test password: " + testPassword + "\n" +
                "Encoded: " + encoded + "\n" +
                "Matches: " + matches + "\n" +
                "Stored hash matches: " + actualMatches + "\n" +
                "Encoder class: " + passwordEncoder.getClass().getName();

        return ResponseEntity.ok(result);
    }

    private void doAuthenticate(String email, String password) {
        // Enhanced debugging
        User user = userService.getUserByEmail(email);
        if (user == null) {
            logger.error("🚫 User not found in DB with email: {}", email);
            throw new BadCredentialsException("User not found");
        }

        logger.info("📥 Password entered: [HIDDEN]");
        logger.info("🔐 Password in DB (hashed): {}", user.getPassword());
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        logger.info("✅ Passwords match? {}", passwordMatches);

        if (!passwordMatches) {
            throw new BadCredentialsException("Password does not match");
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, password);

        try {
            logger.info("Attempting authentication with AuthenticationManager...");
            Authentication result = manager.authenticate(authentication);
            logger.info("Authentication successful: {}", result.isAuthenticated());
        } catch (AuthenticationException e) {
            logger.error("Authentication failed with exception: ", e);
            throw new BadCredentialsException("Invalid Email or Password!!");
        }
    }

    // Helper method to get user by email
    private User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    // Helper method to create error responses
    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> exceptionHandler(BadCredentialsException e) {
        logger.error("Bad credentials exception: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(createErrorResponse("Invalid Email or Password"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e) {
        logger.error("Unexpected error in AuthController: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Internal server error: " + e.getMessage()));
    }
}