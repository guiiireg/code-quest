package com.example.codequest;

/**
 * DTO représentant la réponse suite à la validation d'une soumission de code.
 */
public class SubmissionResponse {
    private boolean success;
    private String output;
    private int xpGained;

    public SubmissionResponse() {}

    public SubmissionResponse(boolean success, String output, int xpGained) {
        this.success = success;
        this.output = output;
        this.xpGained = xpGained;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getXpGained() {
        return xpGained;
    }

    public void setXpGained(int xpGained) {
        this.xpGained = xpGained;
    }
}
