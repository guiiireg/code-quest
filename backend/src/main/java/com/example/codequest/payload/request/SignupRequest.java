package com.example.codequest.payload.request;

import java.util.Set;

/**
 * Request payload for user registration (signup).
 * 
 * @param username The desired username
 * @param email    The user email address
 * @param password The raw password
 * @param roles    Set of requested roles (e.g. admin, user)
 */
public record SignupRequest(String username, String email, String password, Set<String> roles) {}

