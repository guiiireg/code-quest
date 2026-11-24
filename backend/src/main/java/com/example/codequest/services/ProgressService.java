package com.example.codequest.services;

import com.example.codequest.models.Quest;
import com.example.codequest.payload.response.ProgressResponse;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgressService {

    private final JdbcClient jdbc;

    public ProgressService(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @Transactional
    public int recordCompletion(String userId, Quest quest) {
        int inserted = jdbc.sql("""
                INSERT INTO quest_completions (user_id, quest_id, xp_awarded)
                VALUES (:userId, :questId, :xp)
                ON CONFLICT (user_id, quest_id) DO NOTHING
                """)
                .param("userId", userId)
                .param("questId", quest.getId())
                .param("xp", quest.getXpReward())
                .update();
        return inserted == 1 ? quest.getXpReward() : 0;
    }

    public ProgressResponse getProgress(String userId) {
        var completions = jdbc.sql("""
                SELECT quest_id, xp_awarded
                FROM quest_completions
                WHERE user_id = :userId
                ORDER BY completion_id
                """)
                .param("userId", userId)
                .query((row, index) -> new Completion(row.getString("quest_id"), row.getInt("xp_awarded")))
                .list();

        long xp = completions.stream().mapToLong(Completion::xp).sum();
        return new ProgressResponse(xp, completions.stream().map(Completion::questId).toList());
    }

    private record Completion(String questId, int xp) {}
}
