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

    /**
     * Récupère l'identifiant unique du monde.
     * 
     * @return L'identifiant unique du monde
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du monde.
     * 
     * @param id Le nouvel identifiant du monde
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Récupère le nom du monde.
     * 
     * @return Le nom du monde
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom du monde.
     * 
     * @param name Le nouveau nom du monde
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Récupère la description du monde.
     * 
     * @return La description du monde
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description du monde.
     * 
     * @param description La nouvelle description du monde
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Récupère la liste des quêtes associées à ce monde.
     * 
     * @return La liste des quêtes
     */
    public List<Quest> getQuests() {
        return quests;
    }

    /**
     * Définit la liste des quêtes associées à ce monde et assure la cohérence bidirectionnelle.
     * 
     * @param quests La nouvelle liste de quêtes
     */
    public void setQuests(List<Quest> quests) {
        this.quests = quests != null ? quests : new ArrayList<>();
        // Assure la cohérence de la relation bidirectionnelle
        for (Quest quest : this.quests) {
            quest.setWorld(this);
        }
    }
}
