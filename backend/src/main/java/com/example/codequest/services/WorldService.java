package com.example.codequest.services;

import com.example.codequest.models.World;
import com.example.codequest.repositories.WorldRepository;
import com.example.codequest.exceptions.WorldNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service managing domain logic for worlds/realms.
 */
@Service
public class WorldService {

    private final WorldRepository worldRepository;

    /**
     * Constructor dependency injection of the world repository.
     * 
     * @param worldRepository The world repository
     */
    public WorldService(WorldRepository worldRepository) {
        this.worldRepository = worldRepository;
    }

    /**
     * Retrieves all available worlds.
     * 
     * @return List of all worlds
     */
    public List<World> getAllWorlds() {
        return worldRepository.findAll();
    }

    /**
     * Retrieves a world by its unique identifier.
     * 
     * @param id The world identifier
     * @return The found world
     * @throws WorldNotFoundException If the world is not found
     */
    public World getWorldById(String id) {
        return worldRepository.findById(id)
                .orElseThrow(() -> new WorldNotFoundException("World not found with id: " + id));
    }
}

