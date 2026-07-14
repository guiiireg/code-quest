package com.example.codequest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour la gestion des mondes.
 */
@RestController
@RequestMapping("/api/worlds")
public class WorldController {
    
    /**
     * Liste fictive de mondes pour les tests.
     */
    private final List<World> mockworlds = List.of(
        new World("world-test", "Monde test", "Maitrister", List.of(
            new Quest("quest-1", "corriger", "api", 150, "EASY")
        ))
    );

    /**
     * Récupère la liste de tous les mondes.
     * 
     * @return La liste complète des mondes
     */
    @GetMapping
    public List<World> getAllWorlds() {
        return mockworlds;
    }

    /**
     * Récupère un monde spécifique par son identifiant.
     * 
     * @param id L'identifiant unique du monde à rechercher
     * @return Le monde correspondant à l'identifiant
     * @throws RuntimeException si le monde n'est pas trouvé
     */
    @GetMapping("/{id}")
    public World getworldById(@PathVariable String id) {
        return mockworlds.stream()
            .filter(world -> world.id().equals(id))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Monde non trouvé"));
    }
}
