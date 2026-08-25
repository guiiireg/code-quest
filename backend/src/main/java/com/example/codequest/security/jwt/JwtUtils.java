package com.example.codequest.security.jwt;

import com.example.codequest.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for generating and validating JSON Web Tokens (JWT).
 */
@Component
public class JwtUtils {

    /**
     * Secret key used to sign JWT tokens (Base64 encoded).
     */
    @Value("${codequest.app.jwtSecret}")
    private String jwtSecret;

    /**
     * Token expiration duration in milliseconds.
     */
    @Value("${codequest.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Generates a signed JWT token based on the user authentication.
     * 
     * @param authentication Authentication object containing the logged-in user principal
     * @return Signed JWT compact string
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * Decodes the Base64 secret key and returns an HMAC cryptographic Key instance.
     * 
     * @return Cryptographic Key for JWT signing
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Extracts the username (subject) from a JWT token string.
     * 
     * @param token The JWT token string
     * @return The extracted username
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                   .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validates the cryptographic signature and expiration of a JWT token.
     * 
     * @param authToken The JWT token string to validate
     * @return True if valid, false otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}

