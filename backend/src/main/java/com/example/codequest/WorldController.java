package com.example.codequest;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour la gestion des mondes et des quêtes.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class WorldController {
    
    private final WorldRepository worldRepository;
    private final QuestRepository questRepository;
    private final CodeCompilerService compilerService;

    /**
     * Constructeur pour l'injection des repositories.
     * 
     * @param worldRepository Le repository des mondes
     * @param questRepository Le repository des quêtes
     * @param compilerService Le service de compilation
     */
    public WorldController(WorldRepository worldRepository, QuestRepository questRepository, CodeCompilerService compilerService) {
        this.worldRepository = worldRepository;
        this.questRepository = questRepository;
        this.compilerService = compilerService;
    }

    /**
     * Récupère la liste de tous les mondes.
     * 
     * @return La liste complète des mondes
     */
    @GetMapping("/worlds")
    public List<World> getAllWorlds() {
        return worldRepository.findAll();
    }

    /**
     * Récupère un monde spécifique par son identifiant.
     * 
     * @param id L'identifiant unique du monde à rechercher
     * @return Le monde correspondant à l'identifiant
     * @throws WorldNotFoundException si le monde n'est pas trouvé
     */
    @GetMapping("/worlds/{id}")
    public World getWorldById(@PathVariable String id) {
        return worldRepository.findById(id)
            .orElseThrow(() -> new WorldNotFoundException("Monde non trouvé"));
    }

    /**
     * Récupère une quête spécifique par son identifiant.
     * 
     * @param id L'identifiant unique de la quête à rechercher
     * @return La quête correspondante
     */
    @GetMapping("/quests/{id}")
    public Quest getQuestById(@PathVariable String id) {
        return questRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Quête non trouvée"));
    }

    /**
     * Valide le code soumis pour une quête spécifique (compilation et validation).
     * 
     * @param id L'identifiant de la quête
     * @param request La requête contenant le code utilisateur
     * @return Le résultat de la soumission
     */
    @PostMapping("/quests/{id}/submit")
    public SubmissionResponse submitQuest(@PathVariable String id, @RequestBody SubmissionDTO request) {
        Quest quest = questRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Quête non trouvée"));

        String code = request.code();
        if (code == null || code.isBlank()) {
            return new SubmissionResponse(false, "Erreur : Code manquant.", 0);
        }

        // 1. Compilation
        CodeCompilerService.CompilationResult compileResult = compilerService.compile(code);
        if (!compileResult.success()) {
            return new SubmissionResponse(false, "Erreur de compilation :\n" + compileResult.output(), 0);
        }

        // 2. Validation Logique (Regex)
        String regex = quest.getTestValidationRegex();
        if (regex != null && !regex.isBlank()) {
            if (code.matches(regex)) {
                return new SubmissionResponse(true, "Compilation réussie. Validation logicielle réussie !", quest.getXpReward());
            } else {
                return new SubmissionResponse(false, "Compilation réussie, mais la logique de la solution est incorrecte.", 0);
            }
        }

        // Par défaut si aucune validation logicielle n'est définie
        return new SubmissionResponse(true, "Compilation réussie. (Aucune validation logicielle définie)", quest.getXpReward());
    }
}
