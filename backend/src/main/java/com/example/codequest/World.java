package com.example.codequest;

import java.util.List;

public record World(
        String id,
        String name,
        String description,
        List<Quest> quests) {
}
