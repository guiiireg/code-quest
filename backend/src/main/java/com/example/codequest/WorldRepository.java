package com.example.codequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour la gestion de la persistance de l'entité World.
 */
@Repository
public interface WorldRepository extends JpaRepository<World, String> {
}
