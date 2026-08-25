package com.example.codequest.repositories;

import com.example.codequest.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Role entity.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * Finds a role by its unique name.
     * 
     * @param name The role name to look up (e.g., "ROLE_USER")
     * @return An Optional containing the role if found, or empty otherwise
     */
    Optional<Role> findByName(String name);
}

