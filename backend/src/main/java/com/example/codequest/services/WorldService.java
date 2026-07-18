package com.example.codequest.services;

import com.example.codequest.models.World;
import com.example.codequest.repositories.WorldRepository;
import com.example.codequest.exceptions.WorldNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service gérant la logique métier pour les mondes.
 */
@Service
public class WorldService {

    private final WorldRepository worldRepository;

    /**
     * Injection par constructeur du repository des mondes.
     * 
     * @param worldRepository Le repository des mondes
     */
    public WorldService(WorldRepository worldRepository) {
        this.worldRepository = worldRepository;
    }

    /**
     * Récupère tous les mondes disponibles.
     * 
     * @return La liste de tous les mondes
     */
    public List<World> getAllWorlds() {
        return worldRepository.findAll();
    }

    /**
     * Récupère un monde par son identifiant.
     * 
     * @param id L'identifiant du monde
     * @return Le monde trouvé
     * @throws WorldNotFoundException si le monde n'est pas trouvé
     */
    public World getWorldById(String id) {
        return worldRepository.findById(id)
                .orElseThrow(() -> new WorldNotFoundException("Monde non trouvé avec l'id : " + id));
    }
}
