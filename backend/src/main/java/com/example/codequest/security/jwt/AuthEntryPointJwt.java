package com.example.codequest.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Handles unauthorized authentication errors (HTTP 401).
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    /**
     * Triggered when an unauthenticated user attempts to access a protected resource.
     * Returns an HTTP 401 Unauthorized response.
     * 
     * @param request       The HTTP request
     * @param response      The HTTP response
     * @param authException The authentication exception thrown
     * @throws IOException      In case of I/O error
     * @throws ServletException In case of servlet error
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}

