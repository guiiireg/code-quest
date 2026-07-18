package com.example.codequest.payload.request;

/**
 * Record représentant les données de soumission d'une quête (DTO moderne et immuable).
 * 
 * @param questId L'identifiant de la quête
 * @param code Le code source soumis
 */
public record SubmissionDTO(String questId, String code) {}
