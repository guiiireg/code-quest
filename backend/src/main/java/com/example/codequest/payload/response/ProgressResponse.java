package com.example.codequest.payload.response;

import java.util.List;

public record ProgressResponse(long xp, List<String> completedQuests) {}
