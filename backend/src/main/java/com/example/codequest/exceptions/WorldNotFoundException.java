package com.example.codequest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception levée lorsqu'un monde n'est pas trouvé.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Monde non trouvé")
public class WorldNotFoundException extends RuntimeException {

    /**
     * Construit une nouvelle exception avec le message spécifié.
     * 
     * @param message Le message détaillant l'erreur
     */
    public WorldNotFoundException(String message) {
        super(message);
    }
}
