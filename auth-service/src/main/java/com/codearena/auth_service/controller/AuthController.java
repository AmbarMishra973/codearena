package com.codearena.auth_service.controller;

import com.codearena.auth_service.dto.LoginRequest;  // Import LoginRequest DTO
import com.codearena.auth_service.dto.SignupRequest;  // Import SignupRequest DTO
import com.codearena.auth_service.dto.JwtResponse;
import com.codearena.auth_service.security.JwtUtils;
import com.codearena.auth_service.security.UserDetailsImpl;
import com.codearena.auth_service.entity.User;  // Import User entity from auth_service.entity package
import com.codearena.auth_service.repository.UserRepository;  // Import UserRepository from auth_service.repository package
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;  // Inject the UserRepository

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    // ✅ Signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

        // Check if the email already exists in the database
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Check if the username already exists in the database
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // Create a new User entity
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));  // Encode the password

        // Save the user to the database
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    // ✅ Login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Authenticate the user using the AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JWT token for the authenticated user
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Get user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Return the JWT token and user details in the response
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAuthorities()
        ));
    }
}
