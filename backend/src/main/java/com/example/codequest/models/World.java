package com.example.codequest.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a world/realm in the application.
 * A world contains a unique id, name, description, and a list of quests.
 */
@Entity
@Table(name = "worlds")
public class World {

    /**
     * Unique identifier for the world.
     */
    @Id
    private String id;

    /**
     * Name of the world.
     */
    private String name;

    /**
     * Description of the world.
     */
    private String description;

    /**
     * List of quests associated with this world.
     */
    @OneToMany(mappedBy = "world", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quest> quests = new ArrayList<>();

    /**
     * Default constructor required by JPA.
     */
    public World() {}

    /**
     * Full constructor to initialize a world with its quests.
     * 
     * @param id          Unique world identifier
     * @param name        World name
     * @param description Detailed world description
     * @param quests      List of quests available in this world
     */
    public World(String id, String name, String description, List<Quest> quests) {
        this.id = id;
        this.name = name;
        this.description = description;
        setQuests(quests);
    }

    /**
     * Gets the unique identifier of the world.
     * 
     * @return The unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the world.
     * 
     * @param id The new identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the world.
     * 
     * @return The world name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the world.
     * 
     * @param name The new world name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the world description.
     * 
     * @return The world description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the world description.
     * 
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the list of quests belonging to this world.
     * 
     * @return The list of quests
     */
    public List<Quest> getQuests() {
        return quests;
    }

    /**
     * Sets the list of quests for this world and ensures bidirectional relationship consistency.
     * 
     * @param quests The new list of quests
     */
    public void setQuests(List<Quest> quests) {
        this.quests = quests != null ? quests : new ArrayList<>();
        // Maintain bidirectional relationship consistency
        for (Quest quest : this.quests) {
            quest.setWorld(this);
        }
    }
}

