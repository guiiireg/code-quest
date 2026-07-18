package com.example.codequest.controllers;

import com.example.codequest.payload.request.LoginRequest;
import com.example.codequest.payload.request.SignupRequest;
import com.example.codequest.payload.response.JwtResponse;
import com.example.codequest.payload.response.MessageResponse;
import com.example.codequest.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour l'authentification des utilisateurs.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authentifie un utilisateur.
     * 
     * @param loginRequest Requête de connexion
     * @return Informations utilisateur et jeton JWT
     */
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    /**
     * Enregistre un nouvel utilisateur.
     * 
     * @param signUpRequest Requête d'inscription
     * @return Message de succès ou d'erreur
     */
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerUser(signUpRequest));
    }
}
