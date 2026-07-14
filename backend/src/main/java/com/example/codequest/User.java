package com.example.codequest;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Représente un utilisateur dans l'application.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Identifiant unique de l'utilisateur.
     */
    @Id
    private String id;

    /**
     * Nom d'utilisateur unique.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Adresse email unique.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Mot de passe hashé.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Liste des rôles attribués à l'utilisateur.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /**
     * Constructeur par défaut requis par JPA.
     */
    public User() {}

    /**
     * Constructeur complet pour créer un utilisateur.
     * 
     * @param id       L'identifiant unique de l'utilisateur
     * @param username Le nom de l'utilisateur
     * @param email    L'adresse email de l'utilisateur
     * @param password Le mot de passe hashé de l'utilisateur
     */
    public User(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Récupère l'identifiant de l'utilisateur.
     * 
     * @return L'identifiant unique
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'utilisateur.
     * 
     * @param id Le nouvel identifiant unique
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Récupère le nom d'utilisateur.
     * 
     * @return Le nom d'utilisateur
     */
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur.
     * 
     * @param username Le nouveau nom d'utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Récupère l'adresse email de l'utilisateur.
     * 
     * @return L'adresse email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse email de l'utilisateur.
     * 
     * @param email La nouvelle adresse email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Récupère le mot de passe hashé de l'utilisateur.
     * 
     * @return Le mot de passe hashé
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe hashé de l'utilisateur.
     * 
     * @param password Le nouveau mot de passe hashé
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Récupère la liste des rôles associés à l'utilisateur.
     * 
     * @return Les rôles de l'utilisateur
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Définit la liste des rôles de l'utilisateur.
     * 
     * @param roles L'ensemble des rôles de l'utilisateur
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
