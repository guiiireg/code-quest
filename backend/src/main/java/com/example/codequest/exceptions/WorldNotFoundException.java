package com.example.codequest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a realm (world) is not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "World not found")
public class WorldNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message The detail message
     */
    public WorldNotFoundException(String message) {
        super(message);
    }
}

