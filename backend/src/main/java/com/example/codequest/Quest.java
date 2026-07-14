package com.example.codequest;

public record Quest(
        String id,
        String title,
        String description,
        int xpReward,
        String difficulty) {
}