package com.example.codequest.repositories;

import com.example.codequest.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository pour l'entité Role.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * Recherche un rôle par son nom.
     * 
     * @param name Le nom du rôle à rechercher (par exemple, "ROLE_USER")
     * @return Un Optional contenant le rôle s'il est trouvé, sinon vide
     */
    Optional<Role> findByName(String name);
}
