package com.codequest.backend.repository;

import com.codequest.backend.entity.QuestCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestCategoryRepository extends JpaRepository<QuestCategory, UUID> {

    Optional<QuestCategory> findByName(String name);
}
