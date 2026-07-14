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
    
    private final WorldRepository worldRepository;

    /**
     * Constructeur pour l'injection du repository.
     * 
     * @param worldRepository Le repository des mondes
     */
    public WorldController(WorldRepository worldRepository) {
        this.worldRepository = worldRepository;
    }

    /**
     * Récupère la liste de tous les mondes.
     * 
     * @return La liste complète des mondes
     */
    @GetMapping
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
    @GetMapping("/{id}")
    public World getworldById(@PathVariable String id) {
        return worldRepository.findById(id)
            .orElseThrow(() -> new WorldNotFoundException("Monde non trouvé"));
    }
}
