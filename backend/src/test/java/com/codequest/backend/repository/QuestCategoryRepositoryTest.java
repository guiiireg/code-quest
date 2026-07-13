package com.codequest.backend.repository;

import com.codequest.backend.entity.QuestCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuestCategoryRepositoryTest {

    @Autowired
    private QuestCategoryRepository questCategoryRepository;

    @Test
    void shouldSaveAndFindCategoryByName() {
        // Given
        QuestCategory category = QuestCategory.builder()
                .name("Java")
                .description("Le monde du langage Java")
                .build();
        questCategoryRepository.save(category);

        // When
        Optional<QuestCategory> found = questCategoryRepository.findByName("Java");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getDescription()).isEqualTo("Le monde du langage Java");
    }

    @Test
    void shouldNotFindNonExistentCategory() {
        // When
        Optional<QuestCategory> found = questCategoryRepository.findByName("C++");

        // Then
        assertThat(found).isNotPresent();
    }
}
