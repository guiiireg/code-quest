package com.example.codequest.exceptions;

import java.util.Date;

/**
 * DTO pour standardiser les messages d'erreur renvoyés par l'API.
 * 
 * @param statusCode Le code de statut HTTP
 * @param timestamp  La date et l'heure de l'erreur
 * @param message    Le message d'erreur détaillé
 * @param description La description de la requête (URL, etc.)
 */
public record ErrorMessage(
    int statusCode,
    Date timestamp,
    String message,
    String description
) {}
