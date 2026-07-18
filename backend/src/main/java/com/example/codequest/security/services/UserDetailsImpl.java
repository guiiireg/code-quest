package com.example.codequest.security.services;

import com.example.codequest.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation de UserDetails pour Spring Security.
 */
public class UserDetailsImpl implements UserDetails {

    /**
     * Identifiant de l'utilisateur.
     */
    private String id;

    /**
     * Nom d'utilisateur.
     */
    private String username;

    /**
     * Adresse email.
     */
    private String email;

    /**
     * Mot de passe hashé.
     */
    @JsonIgnore
    private String password;

    /**
     * Rôles et habilitations de l'utilisateur.
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructeur pour initialiser les détails de sécurité de l'utilisateur.
     * 
     * @param id          L'identifiant de l'utilisateur
     * @param username    Le nom d'utilisateur
     * @param email       L'adresse email
     * @param password    Le mot de passe hashé
     * @param authorities La liste des habilitations/rôles
     */
    public UserDetailsImpl(String id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Méthode de fabrication (factory method) pour construire UserDetailsImpl à partir d'un objet User JPA.
     * 
     * @param user L'entité User JPA à convertir
     * @return L'instance UserDetailsImpl correspondante
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    /**
     * Récupère l'identifiant de l'utilisateur.
     * 
     * @return L'identifiant de l'utilisateur
     */
    public String getId() {
        return id;
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
     * Récupère les rôles/habilitations accordés à l'utilisateur.
     * 
     * @return Les habilitations accordées
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Récupère le mot de passe de l'utilisateur.
     * 
     * @return Le mot de passe
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Récupère le nom d'utilisateur.
     * 
     * @return Le nom d'utilisateur
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indique si le compte de l'utilisateur a expiré.
     * 
     * @return Vrai par défaut
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indique si l'utilisateur est verrouillé.
     * 
     * @return Vrai par défaut (non verrouillé)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indique si les identifiants (mot de passe) de l'utilisateur ont expiré.
     * 
     * @return Vrai par défaut
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indique si l'utilisateur est activé.
     * 
     * @return Vrai par défaut
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
