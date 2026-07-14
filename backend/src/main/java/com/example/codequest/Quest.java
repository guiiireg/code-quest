package com.example.codequest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Représente une quête dans l'application.
 * Une quête contient un id unique, un titre, une description, une récompense en
 * expérience et une difficulté.
 */
@Entity
@Table(name = "quests")
public class Quest {

    /**
     * Id unique de la quête.
     */
    @Id
    private String id;

    /**
     * Titre de la quête.
     */
    private String title;

    /**
     * Description de la quête.
     */
    private String description;

    /**
     * Récompense en expérience de la quête.
     */
    private int xpReward;

    /**
     * Niveau de difficulté de la quête.
     */
    private String difficulty;

    /**
     * Le monde auquel appartient cette quête.
     */
    @ManyToOne
    @JoinColumn(name = "world_id")
    @JsonIgnore
    private World world;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Quest() {}

    /**
     * Constructeur complet pour créer une instance de quête.
     * 
     * @param id          L'id unique de la quête
     * @param title       Le titre de la quête
     * @param description La description détaillée de la quête
     * @param xpReward    La récompense en expérience de la quête
     * @param difficulty  Le niveau de difficulté de la quête
     */
    public Quest(String id, String title, String description, int xpReward, String difficulty) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.xpReward = xpReward;
        this.difficulty = difficulty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}