package com.example.codequest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception levée lorsqu'une quête n'est pas trouvée.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuestNotFoundException extends RuntimeException {
    public QuestNotFoundException(String message) {
        super(message);
    }
}
