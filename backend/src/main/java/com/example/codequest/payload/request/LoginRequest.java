package com.example.codequest.payload.request;

/**
 * Request payload for user authentication (signin).
 * 
 * @param username The username
 * @param password The user password
 */
public record LoginRequest(String username, String password) {}

