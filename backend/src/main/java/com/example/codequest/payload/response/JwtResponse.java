package com.example.codequest.payload.response;

import java.util.List;

/**
 * Payload response contenant le JWT et les informations de l'utilisateur.
 */
public record JwtResponse(String token, String id, String username, String email, List<String> roles) {}
