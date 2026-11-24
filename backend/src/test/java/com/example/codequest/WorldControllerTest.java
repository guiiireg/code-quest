package com.example.codequest;

import com.example.codequest.controllers.WorldController;
import com.example.codequest.models.Quest;
import com.example.codequest.models.World;
import com.example.codequest.repositories.QuestRepository;
import com.example.codequest.repositories.WorldRepository;
import com.example.codequest.services.CodeCompilerService;
import com.example.codequest.services.QuestService;
import com.example.codequest.services.ProgressService;
import com.example.codequest.security.services.UserDetailsImpl;
import com.example.codequest.services.WorldService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.codequest.security.WebSecurityConfig;
import com.example.codequest.security.jwt.AuthEntryPointJwt;
import com.example.codequest.security.jwt.AuthTokenFilter;
import com.example.codequest.security.jwt.JwtUtils;
import com.example.codequest.security.services.UserDetailsServiceImpl;

/**
 * Integration and web layer tests for the WorldController REST API.
 */
@WebMvcTest(WorldController.class)
@Import({WorldService.class, QuestService.class, WebSecurityConfig.class, AuthEntryPointJwt.class, AuthTokenFilter.class, JwtUtils.class})
@WithMockUser
class WorldControllerTest {

    private static final UserDetailsImpl PLAYER = new UserDetailsImpl(
            "player-id", "testuser", "player@example.org", "", List.of());

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserDetailsServiceImpl userDetailsService;

    @MockitoBean
    private WorldRepository worldRepository;

    @MockitoBean
    private QuestRepository questRepository;

    @MockitoBean
    private CodeCompilerService compilerService;

    @MockitoBean
    private ProgressService progressService;

    /**
     * Verifies that retrieving all worlds returns the full list with HTTP 200 OK.
     */
    @Test
    void shouldReturnAllWorlds() throws Exception {
        var entry = Mockito.mock(QuestRepository.AtlasEntry.class);
        Mockito.when(entry.getWorldId()).thenReturn("world-1");
        Mockito.when(entry.getId()).thenReturn("q1");
        Mockito.when(entry.getTitle()).thenReturn("Syntaxe & Variables");
        Mockito.when(entry.getXpReward()).thenReturn(100);
        Mockito.when(entry.getConcept()).thenReturn("Variables");
        Mockito.when(questRepository.findAtlasEntries()).thenReturn(List.of(entry));
        Mockito.when(worldRepository.findAll()).thenReturn(List.of(
            new World("world-1", "Terre du Code", "Le point de départ", List.of()),
            new World("world-2", "Monde vide", "", List.of())
        ));

        mockMvc.perform(get("/api/worlds")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value("world-1"))
                .andExpect(jsonPath("$[0].name").value("Terre du Code"))
                .andExpect(jsonPath("$[0].description").value("Le point de départ"))
                .andExpect(jsonPath("$[0].quests", hasSize(1)))
                .andExpect(jsonPath("$[0].quests[0].id").value("q1"))
                .andExpect(jsonPath("$[0].quests[0].title").value("Syntaxe & Variables"))
                .andExpect(jsonPath("$[0].quests[0].xpReward").value(100))
                .andExpect(jsonPath("$[0].quests[0].concept").value("Variables"))
                .andExpect(jsonPath("$[0].quests[0].description").doesNotHaveJsonPath())
                .andExpect(jsonPath("$[0].quests[0].theory").doesNotHaveJsonPath())
                .andExpect(jsonPath("$[0].quests[0].codeTemplate").doesNotHaveJsonPath())
                .andExpect(jsonPath("$[0].quests[0].testValidationRegex").doesNotHaveJsonPath())
                .andExpect(jsonPath("$[1].quests", hasSize(0)));
    }

    /**
     * Verifies that retrieving an existing world by ID returns the expected details with HTTP 200 OK.
     */
    @Test
    void shouldReturnWorldByIdWhenExists() throws Exception {
        Mockito.when(worldRepository.findById("world-1")).thenReturn(Optional.of(
            new World("world-1", "Terre du Code", "Le point de départ", List.of(
                new Quest("q1", "Syntaxe & Variables", "Desc", 100, "EASY")
            ))
        ));

        mockMvc.perform(get("/api/worlds/world-1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("world-1"))
                .andExpect(jsonPath("$.name").value("Terre du Code"))
                .andExpect(jsonPath("$.description").value("Le point de départ"))
                .andExpect(jsonPath("$.quests[0].description").value("Desc"))
                .andExpect(jsonPath("$.quests[0].difficulty").value("EASY"))
                .andExpect(jsonPath("$.quests[0].theory").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.quests[0].codeTemplate").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.quests[0].testValidationRegex").doesNotHaveJsonPath());
    }

    /**
     * Verifies that requesting a non-existent world returns HTTP 404 Not Found.
     */
    @Test
    void shouldReturn404WhenWorldDoesNotExist() throws Exception {
        Mockito.when(worldRepository.findById("non-existent-world")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/worlds/non-existent-world")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifies that retrieving an existing quest by ID returns the expected details.
     */
    @Test
    void shouldReturnQuestByIdWhenExists() throws Exception {
        Quest mockQuest = new Quest("quest-test", "Titre", "Desc", 100, "EASY");
        mockQuest.setTheory("Leçon");
        mockQuest.setCodeTemplate("<p></p>");
        mockQuest.setTestValidationRegex("pattern");
        Mockito.when(questRepository.findById("quest-test")).thenReturn(Optional.of(mockQuest));

        mockMvc.perform(get("/api/quests/quest-test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("quest-test"))
                .andExpect(jsonPath("$.title").value("Titre"))
                .andExpect(jsonPath("$.xpReward").value(100))
                .andExpect(jsonPath("$.theory").value("Leçon"))
                .andExpect(jsonPath("$.codeTemplate").value("<p></p>"))
                .andExpect(jsonPath("$.testValidationRegex").doesNotHaveJsonPath());
    }

    /**
     * Verifies that valid submitted code compiles and passes validation regex.
     */
    @Test
    void shouldReturnSuccessWhenCompilationAndRegexPass() throws Exception {
        Quest mockQuest = new Quest("quest-test", "Titre", "Desc", 100, "EASY");
        mockQuest.setTestValidationRegex(".*SUCCESS.*");
        Mockito.when(questRepository.findById("quest-test")).thenReturn(Optional.of(mockQuest));

        String code = "public class Solution { // SUCCESS }";
        Mockito.when(compilerService.compile(code)).thenReturn(new CodeCompilerService.CompilationResult(true, "Compilation successful.\n"));
        Mockito.when(progressService.recordCompletion("player-id", mockQuest)).thenReturn(100);

        String requestJson = "{\"questId\":\"quest-test\",\"code\":\"" + code + "\"}";

        mockMvc.perform(post("/api/quests/quest-test/submit")
                .with(user(PLAYER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.output").value("Congratulations! Quest completed successfully."))
                .andExpect(jsonPath("$.xpGained").value(100));
    }

    /**
     * Verifies that code with syntax errors fails Java compilation.
     */
    @Test
    void shouldReturnFailureWhenCompilationFails() throws Exception {
        Quest mockQuest = new Quest("quest-test", "Titre", "Desc", 100, "EASY");
        Mockito.when(questRepository.findById("quest-test")).thenReturn(Optional.of(mockQuest));

        String code = "public class Solution { syntax error }";
        Mockito.when(compilerService.compile(code)).thenReturn(new CodeCompilerService.CompilationResult(false, "Line 1: syntax error"));

        String requestJson = "{\"questId\":\"quest-test\",\"code\":\"" + code + "\"}";

        mockMvc.perform(post("/api/quests/quest-test/submit")
                .with(user(PLAYER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.output").value("Java compilation error:\nLine 1: syntax error"))
                .andExpect(jsonPath("$.xpGained").value(0));
    }

    /**
     * Verifies that code compiling but failing validation regex returns a failure response.
     */
    @Test
    void shouldReturnFailureWhenCompilationPassesButRegexFails() throws Exception {
        Quest mockQuest = new Quest("quest-test", "Titre", "Desc", 100, "EASY");
        mockQuest.setTestValidationRegex(".*SUCCESS.*");
        Mockito.when(questRepository.findById("quest-test")).thenReturn(Optional.of(mockQuest));

        String code = "public class Solution { // WRONG CODE }";
        Mockito.when(compilerService.compile(code)).thenReturn(new CodeCompilerService.CompilationResult(true, "Compilation successful.\n"));

        String requestJson = "{\"questId\":\"quest-test\",\"code\":\"" + code + "\"}";

        mockMvc.perform(post("/api/quests/quest-test/submit")
                .with(user(PLAYER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.output").value("Validation failure: The submitted code does not meet the requirements or expected tags."))
                .andExpect(jsonPath("$.xpGained").value(0));
    }

    /**
     * Verifies that submitting null code returns a validation failure.
     */
    @Test
    void shouldReturnFailureWhenSubmittedCodeIsNull() throws Exception {
        Quest mockQuest = new Quest("quest-test", "Titre", "Desc", 100, "EASY");
        Mockito.when(questRepository.findById("quest-test")).thenReturn(Optional.of(mockQuest));

        String requestJson = "{\"questId\":\"quest-test\",\"code\":null}";

        mockMvc.perform(post("/api/quests/quest-test/submit")
                .with(user(PLAYER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.output").value("Validation failure: No code submitted."));
    }

    @Test
    @org.springframework.security.test.context.support.WithAnonymousUser
    void shouldRequireAuthenticationForSubmission() throws Exception {
        mockMvc.perform(post("/api/quests/quest-test/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"test\"}"))
                .andExpect(status().isUnauthorized());

        Mockito.verifyNoInteractions(compilerService, progressService);
    }

    @Test
    void shouldValidateJavaScriptWithoutJavaCompiler() throws Exception {
        Quest quest = new Quest("quest-test", "Titre", "Desc", 100, "EASY");
        quest.setLanguages("HTML5, ARIA, JavaScript, A11y");
        quest.setTestValidationRegex("<div>.*</div>");
        Mockito.when(questRepository.findById("quest-test")).thenReturn(Optional.of(quest));
        Mockito.when(progressService.recordCompletion("player-id", quest)).thenReturn(100, 0);

        for (int expectedXp : List.of(100, 0)) {
            mockMvc.perform(post("/api/quests/quest-test/submit")
                    .with(user(PLAYER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"code\":\"<div>solution</div>\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.xpGained").value(expectedXp));
        }
        Mockito.verifyNoInteractions(compilerService);
    }

    @Test
    void shouldRejectOversizedCodeBeforeEvaluation() throws Exception {
        Quest quest = new Quest("quest-test", "Titre", "Desc", 100, "EASY");
        Mockito.when(questRepository.findById("quest-test")).thenReturn(Optional.of(quest));

        mockMvc.perform(post("/api/quests/quest-test/submit")
                .with(user(PLAYER))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"" + "x".repeat(16_385) + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.xpGained").value(0));

        Mockito.verifyNoInteractions(compilerService, progressService);
    }
}
