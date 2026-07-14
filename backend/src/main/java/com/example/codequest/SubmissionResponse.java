package com.example.codequest;

/**
 * Record représentant la réponse à une soumission (DTO moderne et immuable).
 * 
 * @param success Indique si la soumission a réussi
 * @param output Le résultat ou log de la compilation/exécution
 * @param xpGained L'expérience gagnée (0 si échec)
 */
public record SubmissionResponse(boolean success, String output, int xpGained) {}
