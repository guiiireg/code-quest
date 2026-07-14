package com.example.codequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un monde dans l'application.
 * Un monde contient un id unique, un nom, une description et une liste de
 * quêtes.
 */
@Entity
@Table(name = "worlds")
public class World {

    /**
     * Id unique du monde.
     */
    @Id
    private String id;

    /**
     * Nom du monde.
     */
    private String name;

    /**
     * Description du monde.
     */
    private String description;

    /**
     * Liste des quêtes associées à ce monde.
     */
    @OneToMany(mappedBy = "world", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quest> quests = new ArrayList<>();

    /**
     * Constructeur par défaut requis par JPA.
     */
    public World() {}

    /**
     * Constructeur complet pour créer un monde avec ses quêtes.
     * 
     * @param id          L'id unique du monde
     * @param name        Le nom du monde
     * @param description La description détaillée du monde
     * @param quests      La liste des quêtes dispo dans ce monde
     */
    public World(String id, String name, String description, List<Quest> quests) {
        this.id = id;
        this.name = name;
        this.description = description;
        setQuests(quests);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests != null ? quests : new ArrayList<>();
        // Assure la cohérence de la relation bidirectionnelle
        for (Quest quest : this.quests) {
            quest.setWorld(this);
        }
    }
}
