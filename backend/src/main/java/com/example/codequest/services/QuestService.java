package com.example.codequest.services;

import com.example.codequest.exceptions.QuestNotFoundException;
import com.example.codequest.models.Quest;
import com.example.codequest.repositories.QuestRepository;
import com.example.codequest.payload.request.SubmissionDTO;
import com.example.codequest.payload.response.SubmissionResponse;
import org.springframework.stereotype.Service;

/**
 * Service managing domain logic for quests and code submissions.
 */
@Service
public class QuestService {

    private final QuestRepository questRepository;
    private final CodeCompilerService compilerService;

    /**
     * Constructor dependency injection.
     * 
     * @param questRepository Quest repository
     * @param compilerService Code compiler service
     */
    public QuestService(QuestRepository questRepository, CodeCompilerService compilerService) {
        this.questRepository = questRepository;
        this.compilerService = compilerService;
    }

    /**
     * Retrieves a quest by its unique identifier.
     * 
     * @param id The quest identifier
     * @return The found quest
     */
    public Quest getQuestById(String id) {
        return questRepository.findById(id)
                .orElseThrow(() -> new QuestNotFoundException("Quest not found with id: " + id));
    }

    /**
     * Processes code submission for a quest.
     * 
     * @param id The quest identifier
     * @param request The submission payload
     * @return The submission evaluation response
     */
    public SubmissionResponse submitQuest(String id, SubmissionDTO request) {
        Quest quest = getQuestById(id);

        String code = request.code();
        if (code == null || code.isBlank()) {
            return new SubmissionResponse(false, "Validation failure: No code submitted.", 0);
        }

        // Verify that the code was modified from the starter template
        if (quest.getCodeTemplate() != null && code.trim().equalsIgnoreCase(quest.getCodeTemplate().trim())) {
            return new SubmissionResponse(false, "Validation failure: You have not completed the exercise yet. Modify the starter code to succeed.", 0);
        }

        // 1. Java compilation (skipped for purely HTML/CSS web quests)
        boolean isWebQuest = quest.getLanguages() != null && 
            (quest.getLanguages().toUpperCase().contains("HTML") || quest.getLanguages().toUpperCase().contains("CSS")) &&
            !quest.getLanguages().toUpperCase().contains("JAVA");

        if (!isWebQuest) {
            CodeCompilerService.CompilationResult compileResult = compilerService.compile(code);
            if (!compileResult.success()) {
                return new SubmissionResponse(false, "Java compilation error:\n" + compileResult.output(), 0);
            }
        }

        // 2. Logical & structural validation (Regex matching)
        String regex = quest.getTestValidationRegex();
        if (regex != null && !regex.isBlank()) {
            if (code.matches(regex)) {
                return new SubmissionResponse(true, "Congratulations! Quest completed successfully.", quest.getXpReward());
            } else {
                return new SubmissionResponse(false, "Validation failure: The submitted code does not meet the requirements or expected tags.", 0);
            }
        }

        return new SubmissionResponse(true, "Quest completed successfully.", quest.getXpReward());
    }
}

