package com.example.codequest.payload.request;

/**
 * Payload request pour la connexion (signin).
 * 
 * @param username Le nom d'utilisateur
 * @param password Le mot de passe de l'utilisateur
 */
public record LoginRequest(String username, String password) {}
