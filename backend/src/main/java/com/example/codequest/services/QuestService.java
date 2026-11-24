package com.example.codequest.services;

import com.example.codequest.exceptions.QuestNotFoundException;
import com.example.codequest.models.Quest;
import com.example.codequest.repositories.QuestRepository;
import com.example.codequest.payload.request.SubmissionDTO;
import com.example.codequest.payload.response.SubmissionResponse;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.regex.Pattern;

@Service
public class QuestService {

    private static final int MAX_CODE_LENGTH = 16_384;
    private static final Duration MATCH_TIMEOUT = Duration.ofMillis(250);

    private final QuestRepository questRepository;
    private final CodeCompilerService compilerService;
    private final ProgressService progressService;
    private final Semaphore evaluationSlots = new Semaphore(2);

    public QuestService(QuestRepository questRepository, CodeCompilerService compilerService,
                        ProgressService progressService) {
        this.questRepository = questRepository;
        this.compilerService = compilerService;
        this.progressService = progressService;
    }

    public Quest getQuestById(String id) {
        return questRepository.findById(id)
                .orElseThrow(() -> new QuestNotFoundException("Quest not found with id: " + id));
    }

    public SubmissionResponse submitQuest(String id, SubmissionDTO request, String userId) {
        Quest quest = getQuestById(id);
        String code = request.code();
        if (code == null || code.isEmpty()) {
            return new SubmissionResponse(false, "Validation failure: No code submitted.", 0);
        }
        if (code.length() > MAX_CODE_LENGTH) {
            return new SubmissionResponse(false, "Validation failure: Code exceeds the 16,384 character limit.", 0);
        }
        if (code.isBlank()) {
            return new SubmissionResponse(false, "Validation failure: No code submitted.", 0);
        }
        if (quest.getCodeTemplate() != null && code.strip().equals(quest.getCodeTemplate().strip())) {
            return new SubmissionResponse(false, "Validation failure: You have not completed the exercise yet. Modify the starter code to succeed.", 0);
        }
        if (!evaluationSlots.tryAcquire()) {
            return new SubmissionResponse(false, "The evaluator is busy. Please try again shortly.", 0);
        }

        try {
            return evaluate(quest, code, userId);
        } finally {
            evaluationSlots.release();
        }
    }

    private SubmissionResponse evaluate(Quest quest, String code, String userId) {
        if (requiresJava(quest.getLanguages())) {
            var result = compilerService.compile(code);
            if (!result.success()) {
                return new SubmissionResponse(false, "Java compilation error:\n" + result.output(), 0);
            }
        }

        String regex = quest.getTestValidationRegex();
        if (regex != null && !regex.isBlank()) {
            try {
                long deadline = System.nanoTime() + MATCH_TIMEOUT.toNanos();
                if (!Pattern.compile(regex).matcher(new TimedCode(code, deadline)).matches()) {
                    return new SubmissionResponse(false, "Validation failure: The submitted code does not meet the requirements or expected tags.", 0);
                }
            } catch (MatchTimeout | StackOverflowError exception) {
                return new SubmissionResponse(false, "Validation exceeded its complexity limit. Simplify the code and try again.", 0);
            }
        }

        int xp = progressService.recordCompletion(userId, quest);
        return new SubmissionResponse(true, "Congratulations! Quest completed successfully.", xp);
    }

    private boolean requiresJava(String languages) {
        return languages == null || languages.isBlank() || Arrays.stream(languages.split(","))
                .map(String::strip)
                .anyMatch("Java"::equalsIgnoreCase);
    }

    private record TimedCode(String code, long deadline) implements CharSequence {
        @Override
        public int length() {
            return code.length();
        }

        @Override
        public char charAt(int index) {
            if (System.nanoTime() - deadline >= 0) {
                throw new MatchTimeout();
            }
            return code.charAt(index);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return new TimedCode(code.substring(start, end), deadline);
        }
    }

    private static final class MatchTimeout extends RuntimeException {}
}
