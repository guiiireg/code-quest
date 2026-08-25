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
 * Spring Security UserDetails implementation.
 */
public class UserDetailsImpl implements UserDetails {

    /**
     * User identifier.
     */
    private String id;

    /**
     * Username.
     */
    private String username;

    /**
     * Email address.
     */
    private String email;

    /**
     * Encrypted password.
     */
    @JsonIgnore
    private String password;

    /**
     * Granted authorities (roles and permissions).
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructor to initialize user security details.
     * 
     * @param id          User identifier
     * @param username    Username
     * @param email       Email address
     * @param password    Hashed password
     * @param authorities Collection of granted authorities
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
     * Factory method to build a UserDetailsImpl instance from a JPA User entity.
     * 
     * @param user The JPA User entity
     * @return Corresponding UserDetailsImpl instance
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
     * Gets the user identifier.
     * 
     * @return The user identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the user email address.
     * 
     * @return The email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets granted authorities for the user.
     * 
     * @return Granted authorities collection
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Gets the hashed user password.
     * 
     * @return The password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Gets the username.
     * 
     * @return The username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired.
     * 
     * @return True by default
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * 
     * @return True by default (unlocked)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * 
     * @return True by default
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * 
     * @return True by default
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

