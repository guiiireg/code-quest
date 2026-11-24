package com.example.codequest.repositories;

import com.example.codequest.models.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Quest entity.
 */
@Repository
public interface QuestRepository extends JpaRepository<Quest, String> {
    interface AtlasEntry {
        String getWorldId();
        String getId();
        String getTitle();
        int getXpReward();
        String getConcept();
    }

    @Query("""
            select q.world.id as worldId, q.id as id, q.title as title,
                   q.xpReward as xpReward, q.concept as concept
            from Quest q
            where q.world is not null
            """)
    List<AtlasEntry> findAtlasEntries();
}

