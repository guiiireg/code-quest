package com.example.codequest.controllers;

import com.example.codequest.payload.response.ProgressResponse;
import com.example.codequest.security.services.UserDetailsImpl;
import com.example.codequest.services.ProgressService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/api/progress")
    public ProgressResponse getProgress(@AuthenticationPrincipal UserDetailsImpl player) {
        return progressService.getProgress(player.getId());
    }
}
