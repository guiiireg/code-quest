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

    /**
     * Constructeur pour l'injection des repositories.
     * 
     * @param worldRepository Le repository des mondes
     * @param questRepository Le repository des quêtes
     */
    public WorldController(WorldRepository worldRepository, QuestRepository questRepository) {
        this.worldRepository = worldRepository;
        this.questRepository = questRepository;
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
    public World getworldById(@PathVariable String id) {
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
     * Valide le code soumis pour une quête spécifique.
     * 
     * @param id L'identifiant de la quête
     * @param request La requête contenant le code utilisateur
     * @return Le résultat de la soumission
     */
    @PostMapping("/quests/{id}/submit")
    public SubmissionResponse submitQuest(@PathVariable String id, @RequestBody SubmissionRequest request) {
        Quest quest = questRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Quête non trouvée"));

        String code = request.getCode();
        if (code == null) {
            return new SubmissionResponse(false, "Code vide ou manquant.", 0);
        }

        String regex = quest.getTestValidationRegex();
        boolean matches = false;
        try {
            matches = Pattern.compile(regex).matcher(code).matches();
        } catch (Exception e) {
            return new SubmissionResponse(false, "Erreur lors de la validation du code : " + e.getMessage(), 0);
        }

        if (matches) {
            return new SubmissionResponse(true, "Félicitations ! Votre code est correct et passe tous les tests de validation !", quest.getXpReward());
        } else {
            return new SubmissionResponse(false, "Échec de validation. Votre code ne respecte pas les consignes de l'exercice ou a échoué aux tests de conformité.", 0);
        }
    }
}
