package com.example.codequest.controllers;

import com.example.codequest.models.World;
import com.example.codequest.models.Quest;
import com.example.codequest.payload.request.SubmissionDTO;
import com.example.codequest.payload.response.SubmissionResponse;
import com.example.codequest.services.WorldService;
import com.example.codequest.services.QuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des mondes et des quêtes.
 */
@RestController
@RequestMapping("/api")
public class WorldController {
    
    private final WorldService worldService;
    private final QuestService questService;

    /**
     * Constructeur pour l'injection des services.
     * 
     * @param worldService Le service des mondes
     * @param questService Le service des quêtes
     */
    public WorldController(WorldService worldService, QuestService questService) {
        this.worldService = worldService;
        this.questService = questService;
    }

    /**
     * Récupère la liste de tous les mondes.
     * 
     * @return La liste complète des mondes
     */
    @GetMapping("/worlds")
    public List<World> getAllWorlds() {
        return worldService.getAllWorlds();
    }

    /**
     * Récupère un monde spécifique par son identifiant.
     * 
     * @param id L'identifiant unique du monde à rechercher
     * @return Le monde correspondant à l'identifiant
     */
    @GetMapping("/worlds/{id}")
    public World getWorldById(@PathVariable String id) {
        return worldService.getWorldById(id);
    }

    /**
     * Récupère une quête spécifique par son identifiant.
     * 
     * @param id L'identifiant unique de la quête à rechercher
     * @return La quête correspondante
     */
    @GetMapping("/quests/{id}")
    public Quest getQuestById(@PathVariable String id) {
        return questService.getQuestById(id);
    }

    /**
     * Valide le code soumis pour une quête spécifique.
     * 
     * @param id L'identifiant de la quête
     * @param request La requête contenant le code utilisateur
     * @return Le résultat de la soumission
     */
    @PostMapping("/quests/{id}/submit")
    public SubmissionResponse submitQuest(@PathVariable String id, @RequestBody SubmissionDTO request) {
        return questService.submitQuest(id, request);
    }
}
