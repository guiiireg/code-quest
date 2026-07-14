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
 * Utilitaire pour la gestion des tokens JWT.
 */
@Component
public class JwtUtils {

    /**
     * Clé secrète utilisée pour signer les tokens JWT (encodée en Base64).
     */
    @Value("${codequest.app.jwtSecret}")
    private String jwtSecret;

    /**
     * Durée de validité en millisecondes d'un jeton JWT.
     */
    @Value("${codequest.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Génère un jeton JWT basé sur l'authentification de l'utilisateur.
     * 
     * @param authentication L'objet d'authentification contenant l'utilisateur connecté
     * @return Le jeton JWT généré sous forme de chaîne de caractères
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
     * Décode la clé secrète Base64 et retourne une clé cryptographique HMAC utilisable pour la signature.
     * 
     * @return La clé cryptographique pour JWT
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Extrait le nom d'utilisateur contenu dans un jeton JWT.
     * 
     * @param token Le jeton JWT
     * @return Le nom d'utilisateur extrait
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                   .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Valide la signature et la durée de validité du jeton JWT.
     * 
     * @param authToken Le jeton JWT à valider
     * @return Vrai si le jeton est valide, faux sinon
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
