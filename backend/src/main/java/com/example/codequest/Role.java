package com.example.codequest;

import jakarta.persistence.*;

/**
 * Représente un rôle dans l'application (ex: ROLE_USER, ROLE_ADMIN).
 */
@Entity
@Table(name = "roles")
public class Role {

    /**
     * Identifiant unique du rôle.
     */
    @Id
    private String id;

    /**
     * Nom du rôle (par exemple : ROLE_USER, ROLE_ADMIN).
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Role() {}

    /**
     * Constructeur pour créer un rôle avec son identifiant et son nom.
     * 
     * @param id   L'identifiant unique du rôle
     * @param name Le nom du rôle
     */
    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Récupère l'identifiant du rôle.
     * 
     * @return L'identifiant unique du rôle
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant du rôle.
     * 
     * @param id Le nouvel identifiant unique du rôle
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Récupère le nom du rôle.
     * 
     * @return Le nom du rôle
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom du rôle.
     * 
     * @param name Le nouveau nom du rôle
     */
    public void setName(String name) {
        this.name = name;
    }
}
