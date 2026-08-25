package com.example.codequest.payload.request;

/**
 * Record representing quest code submission payload (immutable DTO).
 * 
 * @param questId The quest identifier
 * @param code The submitted source code
 */
public record SubmissionDTO(String questId, String code) {}

