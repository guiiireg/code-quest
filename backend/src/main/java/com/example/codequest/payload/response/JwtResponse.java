package com.example.codequest.payload.response;

import java.util.List;

/**
 * Payload response contenant le JWT et les informations de l'utilisateur.
 * 
 * @param token    Le token JWT généré
 * @param id       L'identifiant de l'utilisateur connecté
 * @param username Le nom d'utilisateur
 * @param email    L'adresse email de l'utilisateur
 * @param roles    La liste des rôles de l'utilisateur
 */
public record JwtResponse(String token, String id, String username, String email, List<String> roles) {}
