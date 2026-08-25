package com.example.codequest.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user in the application.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    private String id;

    /**
     * Unique username.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Unique email address.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Encrypted/hashed password.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Set of roles assigned to the user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /**
     * Default constructor required by JPA.
     */
    public User() {}

    /**
     * Full constructor to initialize a user.
     * 
     * @param id       The unique user identifier
     * @param username The username
     * @param email    The email address
     * @param password The encrypted password
     */
    public User(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the user identifier.
     * 
     * @return The unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user identifier.
     * 
     * @param id The new unique identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the username.
     * 
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username The new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address.
     * 
     * @return The email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     * 
     * @param email The new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the hashed password.
     * 
     * @return The hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the hashed password.
     * 
     * @param password The new hashed password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the set of roles assigned to the user.
     * 
     * @return User roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the roles for the user.
     * 
     * @param roles Set of assigned roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

