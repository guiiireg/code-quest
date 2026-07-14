package com.example.codequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception levée lorsqu'un monde n'est pas trouvé.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Monde non trouvé")
public class WorldNotFoundException extends RuntimeException {
    public WorldNotFoundException(String message) {
        super(message);
    }
}
