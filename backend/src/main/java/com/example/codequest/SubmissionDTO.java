package com.example.codequest;

/**
 * DTO représentant les données envoyées lors de la soumission d'une quête.
 */
public class SubmissionDTO {
    private String questId;
    private String code;

    public SubmissionDTO() {}

    public SubmissionDTO(String questId, String code) {
        this.questId = questId;
        this.code = code;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
