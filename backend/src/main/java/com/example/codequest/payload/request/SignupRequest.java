package com.example.codequest.payload.request;

import java.util.Set;

/**
 * Payload request pour l'inscription (signup).
 */
public record SignupRequest(String username, String email, String password, Set<String> roles) {}
