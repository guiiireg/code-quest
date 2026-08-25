package com.example.codequest.models;

import jakarta.persistence.*;

/**
 * Represents a user role in the application (e.g. ROLE_USER, ROLE_ADMIN).
 */
@Entity
@Table(name = "roles")
public class Role {

    /**
     * Unique identifier for the role.
     */
    @Id
    private String id;

    /**
     * Name of the role (e.g. ROLE_USER, ROLE_ADMIN).
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * Default constructor required by JPA.
     */
    public Role() {}

    /**
     * Constructor to create a role with its identifier and name.
     * 
     * @param id   The unique role identifier
     * @param name The name of the role
     */
    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the role identifier.
     * 
     * @return The unique role identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the role identifier.
     * 
     * @param id The new unique role identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the role.
     * 
     * @return The name of the role
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     * 
     * @param name The new name of the role
     */
    public void setName(String name) {
        this.name = name;
    }
}

