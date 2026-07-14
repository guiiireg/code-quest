package com.example.codequest.payload.request;

import java.util.Set;

/**
 * Payload request pour l'inscription (signup).
 * 
 * @param username Le nom d'utilisateur souhaité
 * @param email    L'adresse email de l'utilisateur
 * @param password Le mot de passe
 * @param roles    L'ensemble des rôles demandés (par exemple : admin, user)
 */
public record SignupRequest(String username, String email, String password, Set<String> roles) {}
