package com.example.codequest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Gestionnaire global des exceptions pour centraliser le traitement des erreurs API.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère l'exception WorldNotFoundException et QuestNotFoundException.
     */
    @ExceptionHandler({WorldNotFoundException.class, QuestNotFoundException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(RuntimeException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Gère l'exception UserAlreadyExistsException.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère toutes les autres exceptions non capturées spécifiquement.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
