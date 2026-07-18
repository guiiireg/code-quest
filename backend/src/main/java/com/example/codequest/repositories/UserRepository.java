package com.example.codequest.repositories;

import com.example.codequest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository pour l'entité User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     * 
     * @param username Le nom d'utilisateur à rechercher
     * @return Un Optional contenant l'utilisateur s'il est trouvé, sinon vide
     */
    Optional<User> findByUsername(String username);

    /**
     * Vérifie si un utilisateur existe déjà avec ce nom d'utilisateur.
     * 
     * @param username Le nom d'utilisateur à tester
     * @return Vrai si l'utilisateur existe déjà, faux sinon
     */
    Boolean existsByUsername(String username);

    /**
     * Vérifie si un utilisateur existe déjà avec cette adresse email.
     * 
     * @param email L'adresse email à tester
     * @return Vrai si l'adresse email est déjà prise, faux sinon
     */
    Boolean existsByEmail(String email);
}
