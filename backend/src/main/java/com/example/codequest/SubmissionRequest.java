package com.example.codequest;

/**
 * DTO représentant la requête de soumission de code par l'utilisateur.
 */
public class SubmissionRequest {
    private String code;

    public SubmissionRequest() {}

    public SubmissionRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
