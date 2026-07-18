package com.example.codequest.repositories;

import com.example.codequest.models.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour la gestion de la persistance de l'entité Quest.
 */
@Repository
public interface QuestRepository extends JpaRepository<Quest, String> {
}
