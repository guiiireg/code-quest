package com.codequest.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestSubmissionId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "quest_id")
    private UUID questId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestSubmissionId that = (QuestSubmissionId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(questId, that.questId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, questId);
    }
}
