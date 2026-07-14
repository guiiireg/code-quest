package com.example.codequest;

import java.util.List;

/**
 * Représente un monde dans l'application.
 * Un monde contient un id unique, un nom, une description et une liste de
 * quêtes.
 * Ce record est immutable : une fois créé, ses champs ne peuvent être modifiés.
 * 
 * @param id          L'id unique du monde
 * @param name        Le nom du monde
 * @param description La description détaillée du monde
 * @param quests      La liste des quêtes dispo dans ce monde
 */
public record World(
    /**
     * Id unique du monde.
     * Ne doit pas être null.
     */
    String id,

    /**
     * Nom du monde.
     */
    String name,

    /**
     * Description du monde.
     */
    String description,

    /**
     * Liste des quêtes associés à ce monde.
     * Peut être vide, mais pas null.
     */
    List<Quest> quests) {
}
