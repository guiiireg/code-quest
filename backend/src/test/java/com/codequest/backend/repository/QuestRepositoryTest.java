package com.codequest.backend.repository;

import com.codequest.backend.entity.Quest;
import com.codequest.backend.entity.QuestCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuestRepositoryTest {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private QuestCategoryRepository questCategoryRepository;

    @Test
    void shouldFindQuestsByCategoryId() {
        // Given
        QuestCategory category = QuestCategory.builder()
                .name("Spring Boot")
                .description("Framework Spring")
                .build();
        QuestCategory savedCategory = questCategoryRepository.save(category);

        Quest quest1 = Quest.builder()
                .title("Init Spring")
                .category(savedCategory)
                .xpReward(10)
                .isBoss(false)
                .build();
        Quest quest2 = Quest.builder()
                .title("Spring Data")
                .category(savedCategory)
                .xpReward(20)
                .isBoss(false)
                .build();
        questRepository.saveAll(List.of(quest1, quest2));

        // When
        List<Quest> foundQuests = questRepository.findByCategory_Id(savedCategory.getId());

        // Then
        assertThat(foundQuests).hasSize(2);
        assertThat(foundQuests).extracting("title").containsExactlyInAnyOrder("Init Spring", "Spring Data");
    }

    @Test
    void shouldFindOnlyBossQuests() {
        // Given
        QuestCategory category = QuestCategory.builder()
                .name("Docker")
                .build();
        QuestCategory savedCategory = questCategoryRepository.save(category);

        Quest normalQuest = Quest.builder()
                .title("Create Dockerfile")
                .category(savedCategory)
                .xpReward(10)
                .isBoss(false)
                .build();
        Quest bossQuest = Quest.builder()
                .title("Defeat Docker Swarm Boss")
                .category(savedCategory)
                .xpReward(100)
                .isBoss(true)
                .build();
        questRepository.saveAll(List.of(normalQuest, bossQuest));

        // When
        List<Quest> bossQuests = questRepository.findByIsBossTrue();

        // Then
        assertThat(bossQuests).hasSize(1);
        assertThat(bossQuests.get(0).getTitle()).isEqualTo("Defeat Docker Swarm Boss");
    }
}
