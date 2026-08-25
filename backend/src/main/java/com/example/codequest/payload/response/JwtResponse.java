package com.example.codequest.payload.response;

import java.util.List;

/**
 * Response payload containing the JWT authentication token and user profile details.
 * 
 * @param token    The generated JWT token
 * @param id       The unique user identifier
 * @param username The username
 * @param email    The email address
 * @param roles    List of user roles
 */
public record JwtResponse(String token, String id, String username, String email, List<String> roles) {}

