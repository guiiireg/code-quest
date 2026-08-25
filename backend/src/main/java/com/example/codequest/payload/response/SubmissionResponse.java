package com.example.codequest.payload.response;

/**
 * Record representing the response to a quest code submission (immutable DTO).
 * 
 * @param success Indicates whether the submission succeeded
 * @param output Output log or error from compilation/execution
 * @param xpGained Experience points gained (0 on failure)
 */
public record SubmissionResponse(boolean success, String output, int xpGained) {}

