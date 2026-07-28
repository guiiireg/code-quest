package com.example.codequest.services;

import com.example.codequest.exceptions.QuestNotFoundException;
import com.example.codequest.models.Quest;
import com.example.codequest.repositories.QuestRepository;
import com.example.codequest.payload.request.SubmissionDTO;
import com.example.codequest.payload.response.SubmissionResponse;
import org.springframework.stereotype.Service;

/**
 * Service gérant la logique métier pour les quêtes et les soumissions de code.
 */
@Service
public class QuestService {

    private final QuestRepository questRepository;
    private final CodeCompilerService compilerService;

    /**
     * Injection par constructeur des dépendances.
     * 
     * @param questRepository Le repository des quêtes
     * @param compilerService Le service de compilation
     */
    public QuestService(QuestRepository questRepository, CodeCompilerService compilerService) {
        this.questRepository = questRepository;
        this.compilerService = compilerService;
    }

    /**
     * Récupère une quête par son identifiant.
     * 
     * @param id L'identifiant de la quête
     * @return La quête trouvée
     */
    public Quest getQuestById(String id) {
        return questRepository.findById(id)
                .orElseThrow(() -> new QuestNotFoundException("Quête non trouvée avec l'id : " + id));
    }

    /**
     * Traite la soumission de code pour une quête.
     * 
     * @param id L'identifiant de la quête
     * @param request Les données de soumission
     * @return Le résultat de la soumission
     */
    public SubmissionResponse submitQuest(String id, SubmissionDTO request) {
        Quest quest = getQuestById(id);

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

        return new SubmissionResponse(true, "Compilation réussie. (Aucune validation logicielle définie)", quest.getXpReward());
    }
}
