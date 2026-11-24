package com.example.codequest.services;

import com.example.codequest.payload.response.WorldResponse;
import com.example.codequest.payload.response.WorldResponse.AtlasQuest;
import com.example.codequest.payload.response.WorldResponse.JournalQuest;
import com.example.codequest.models.World;
import com.example.codequest.repositories.QuestRepository;
import com.example.codequest.repositories.QuestRepository.AtlasEntry;
import com.example.codequest.repositories.WorldRepository;
import com.example.codequest.exceptions.WorldNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service managing domain logic for worlds/realms.
 */
@Service
@Transactional(readOnly = true)
public class WorldService {

    private final WorldRepository worldRepository;
    private final QuestRepository questRepository;

    /**
     * Constructor dependency injection of the world repository.
     * 
     * @param worldRepository The world repository
     */
    public WorldService(WorldRepository worldRepository, QuestRepository questRepository) {
        this.worldRepository = worldRepository;
        this.questRepository = questRepository;
    }

    /**
     * Retrieves all available worlds.
     * 
     * @return List of all worlds
     */
    public List<WorldResponse<AtlasQuest>> getAllWorlds() {
        List<World> worlds = worldRepository.findAll();
        if (worlds.isEmpty()) {
            return List.of();
        }

        Map<String, List<AtlasQuest>> questsByWorld = questRepository.findAtlasEntries().stream()
                .collect(Collectors.groupingBy(AtlasEntry::getWorldId, Collectors.mapping(
                        entry -> new AtlasQuest(entry.getId(), entry.getTitle(), entry.getXpReward(), entry.getConcept()),
                        Collectors.toList())));

        return worlds.stream()
                .map(world -> new WorldResponse<>(world.getId(), world.getName(), world.getDescription(),
                        questsByWorld.getOrDefault(world.getId(), List.of())))
                .toList();
    }

    /**
     * Retrieves a world by its unique identifier.
     * 
     * @param id The world identifier
     * @return The found world
     * @throws WorldNotFoundException If the world is not found
     */
    public WorldResponse<JournalQuest> getWorldById(String id) {
        World world = worldRepository.findById(id)
                .orElseThrow(() -> new WorldNotFoundException("World not found with id: " + id));
        List<JournalQuest> quests = world.getQuests().stream().map(JournalQuest::from).toList();
        return new WorldResponse<>(world.getId(), world.getName(), world.getDescription(), quests);
    }
}

