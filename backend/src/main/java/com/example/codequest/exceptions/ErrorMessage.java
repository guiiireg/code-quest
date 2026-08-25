package com.example.codequest.exceptions;

import java.util.Date;

/**
 * DTO to standardize error responses returned by the API.
 * 
 * @param statusCode HTTP status code
 * @param timestamp  Timestamp when the error occurred
 * @param message    Detailed error message
 * @param description Request description (URL, path, etc.)
 */
public record ErrorMessage(
    int statusCode,
    Date timestamp,
    String message,
    String description
) {}

