package com.example.codequest;

import jakarta.persistence.*;

/**
 * Représente un rôle dans l'application (ex: ROLE_USER, ROLE_ADMIN).
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    private String id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    public Role() {}

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
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
}
