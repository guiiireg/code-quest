package com.example.codequest.payload.response;

import com.example.codequest.models.Quest;

public record QuestResponse(String id, String title, String description, int xpReward,
                            String difficulty, String category, String languages, String concept,
                            String theory, String codeTemplate) {
    public static QuestResponse from(Quest quest) {
        return new QuestResponse(quest.getId(), quest.getTitle(), quest.getDescription(),
                quest.getXpReward(), quest.getDifficulty(), quest.getCategory(), quest.getLanguages(),
                quest.getConcept(), quest.getTheory(), quest.getCodeTemplate());
    }
}
