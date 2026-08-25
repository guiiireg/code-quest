package com.example.codequest.repositories;

import com.example.codequest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds a user by their unique username.
     * 
     * @param username The username to search for
     * @return An Optional containing the user if found, or empty otherwise
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user exists with the given username.
     * 
     * @param username The username to check
     * @return True if the user exists, false otherwise
     */
    Boolean existsByUsername(String username);

    /**
     * Checks if a user exists with the given email address.
     * 
     * @param email The email address to check
     * @return True if the email already exists, false otherwise
     */
    Boolean existsByEmail(String email);
}

