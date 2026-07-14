package com.example.codequest.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Gère les erreurs d'authentification (erreur 401).
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    /**
     * Se déclenche lorsqu'un utilisateur non authentifié tente d'accéder à une ressource protégée.
     * Renvoie une erreur HTTP 401 (Unauthorized).
     * 
     * @param request       La requête HTTP
     * @param response      La réponse HTTP
     * @param authException L'exception d'authentification levée
     * @throws IOException      En cas d'erreur d'entrée/sortie
     * @throws ServletException En cas d'erreur de servlet générale
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
