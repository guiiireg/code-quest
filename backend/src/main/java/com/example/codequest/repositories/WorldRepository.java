package com.example.codequest.repositories;

import com.example.codequest.models.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the World entity.
 */
@Repository
public interface WorldRepository extends JpaRepository<World, String> {
}

