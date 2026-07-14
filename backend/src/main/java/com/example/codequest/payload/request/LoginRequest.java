package com.example.codequest.payload.request;

/**
 * Payload request pour la connexion (signin).
 */
public record LoginRequest(String username, String password) {}
