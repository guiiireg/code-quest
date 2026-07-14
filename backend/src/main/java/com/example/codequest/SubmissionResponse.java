package com.example.codequest;

/**
 * Record représentant la réponse à une soumission (DTO moderne et immuable).
 */
public record SubmissionResponse(boolean success, String output, int xpGained) {}
