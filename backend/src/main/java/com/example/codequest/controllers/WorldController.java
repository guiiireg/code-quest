package com.example.codequest.controllers;

import com.example.codequest.payload.request.SubmissionDTO;
import com.example.codequest.payload.response.QuestResponse;
import com.example.codequest.payload.response.SubmissionResponse;
import com.example.codequest.payload.response.WorldResponse;
import com.example.codequest.payload.response.WorldResponse.AtlasQuest;
import com.example.codequest.payload.response.WorldResponse.JournalQuest;
import com.example.codequest.security.services.UserDetailsImpl;
import com.example.codequest.services.WorldService;
import com.example.codequest.services.QuestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing realms (worlds) and quests.
 */
@RestController
@RequestMapping("/api")
public class WorldController {
    
    private final WorldService worldService;
    private final QuestService questService;

    /**
     * Constructor for service dependency injection.
     * 
     * @param worldService The world domain service
     * @param questService The quest domain service
     */
    public WorldController(WorldService worldService, QuestService questService) {
        this.worldService = worldService;
        this.questService = questService;
    }

    /**
     * Retrieves all available worlds.
     * 
     * @return Complete list of worlds
     */
    @GetMapping("/worlds")
    public List<WorldResponse<AtlasQuest>> getAllWorlds() {
        return worldService.getAllWorlds();
    }

    /**
     * Retrieves a specific world by its unique identifier.
     * 
     * @param id The unique world identifier
     * @return The corresponding world
     */
    @GetMapping("/worlds/{id}")
    public WorldResponse<JournalQuest> getWorldById(@PathVariable String id) {
        return worldService.getWorldById(id);
    }

    /**
     * Retrieves a specific quest by its unique identifier.
     * 
     * @param id The unique quest identifier
     * @return The corresponding quest
     */
    @GetMapping("/quests/{id}")
    public QuestResponse getQuestById(@PathVariable String id) {
        return QuestResponse.from(questService.getQuestById(id));
    }

    /**
     * Validates and evaluates submitted code for a specific quest.
     * 
     * @param id The quest identifier
     * @param request The request payload containing submitted code
     * @return The submission result with validation details and XP reward
     */
    @PostMapping("/quests/{id}/submit")
    public SubmissionResponse submitQuest(@PathVariable String id, @RequestBody SubmissionDTO request,
                                          @AuthenticationPrincipal UserDetailsImpl player) {
        return questService.submitQuest(id, request, player.getId());
    }
}

