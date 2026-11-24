package com.example.codequest.payload.response;

import java.util.List;

import com.example.codequest.models.Quest;

public record WorldResponse<T>(String id, String name, String description, List<T> quests) {
    public record AtlasQuest(String id, String title, int xpReward, String concept) {}

    public record JournalQuest(String id, String title, String description, int xpReward,
                               String difficulty, String category, String languages, String concept) {
        public static JournalQuest from(Quest quest) {
            return new JournalQuest(quest.getId(), quest.getTitle(), quest.getDescription(),
                    quest.getXpReward(), quest.getDifficulty(), quest.getCategory(),
                    quest.getLanguages(), quest.getConcept());
        }
    }
}
