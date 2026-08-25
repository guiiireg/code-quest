package com.example.codequest.controllers;

import com.example.codequest.payload.request.LoginRequest;
import com.example.codequest.payload.request.SignupRequest;
import com.example.codequest.payload.response.JwtResponse;
import com.example.codequest.payload.response.MessageResponse;
import com.example.codequest.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user authentication and registration.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticates a user.
     * 
     * @param loginRequest Login credentials
     * @return User details and JWT token
     */
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    /**
     * Registers a new user.
     * 
     * @param signUpRequest Registration details
     * @return Success message response
     */
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerUser(signUpRequest));
    }
}

