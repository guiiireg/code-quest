package com.example.codequest.repositories;

import com.example.codequest.models.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Quest entity.
 */
@Repository
public interface QuestRepository extends JpaRepository<Quest, String> {
}

