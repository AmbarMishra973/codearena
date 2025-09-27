package com.codearena.auth_service.controller;

import com.codearena.codearena_common.dto.LoginRequest;
import com.codearena.codearena_common.dto.SignupRequest;
import com.codearena.codearena_common.dto.JwtResponse;
import com.codearena.auth_service.security.JwtUtils;
import com.codearena.auth_service.security.UserDetailsImpl;
import com.codearena.auth_service.entity.User;
import com.codearena.auth_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    // Signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

        // Check if the email already exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Check if the username already exists
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // Create and save the new user
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Authenticate the user using the AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Set the authentication context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate the JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Get user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Return the JWT token and user details
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAuthorities()
        ));
    }

    // Exception handler for validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Collecting error messages
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        // Returning validation errors
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
