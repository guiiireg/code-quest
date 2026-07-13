package com.codequest.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "user_quests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestSubmission {

    @EmbeddedId
    @Builder.Default
    private QuestSubmissionId id = new QuestSubmissionId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("questId")
    @JoinColumn(name = "quest_id")
    private Quest quest;

    @Column(nullable = false, length = 50)
    @Builder.Default
    private String status = "COMPLETED";

    @CreationTimestamp
    @Column(name = "completed_at", updatable = false)
    private Instant completedAt;
}
