package com.example.codequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

/**
 * Tests d'intégration pour le contrôleur REST des mondes (WorldController).
 */
@WebMvcTest(WorldController.class)
class WorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorldRepository worldRepository;

    /**
     * Vérifie que la récupération de tous les mondes retourne la liste complète
     * avec un code de retour HTTP 200 (OK).
     */
    @Test
    void shouldReturnAllWorlds() throws Exception {
        Mockito.when(worldRepository.findAll()).thenReturn(List.of(
            new World("world-test", "Monde test", "Maitrister", List.of(
                new Quest("quest-1", "corriger", "api", 150, "EASY")
            ))
        ));

        mockMvc.perform(get("/api/worlds")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("world-test"))
                .andExpect(jsonPath("$[0].name").value("Monde test"))
                .andExpect(jsonPath("$[0].description").value("Maitrister"))
                .andExpect(jsonPath("$[0].quests", hasSize(1)))
                .andExpect(jsonPath("$[0].quests[0].id").value("quest-1"))
                .andExpect(jsonPath("$[0].quests[0].title").value("corriger"));
    }

    /**
     * Vérifie que la récupération d'un monde existant par son ID retourne les détails
     * attendus avec un code HTTP 200 (OK).
     */
    @Test
    void shouldReturnWorldByIdWhenExists() throws Exception {
        Mockito.when(worldRepository.findById("world-test")).thenReturn(Optional.of(
            new World("world-test", "Monde test", "Maitrister", List.of(
                new Quest("quest-1", "corriger", "api", 150, "EASY")
            ))
        ));

        mockMvc.perform(get("/api/worlds/world-test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("world-test"))
                .andExpect(jsonPath("$.name").value("Monde test"))
                .andExpect(jsonPath("$.description").value("Maitrister"));
    }

    /**
     * Vérifie que la récupération d'un monde inexistant retourne un code d'erreur HTTP 404 (Not Found).
     */
    @Test
    void shouldReturn404WhenWorldDoesNotExist() throws Exception {
        Mockito.when(worldRepository.findById("non-existent-world")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/worlds/non-existent-world")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
