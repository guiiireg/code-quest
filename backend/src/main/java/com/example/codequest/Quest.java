package com.example.codequest;

/**
 * Représente une quête dans l'application.
 * Une quête contient un id unique, un titre, une description, une récompense en
 * expérience et une difficulté.
 * Ce record est immutable : une fois créé, ses champs ne peuvent être modifiés.
 * 
 * @param id          L'id unique de la quête
 * @param title       Le titre de la quête
 * @param description La description détaillée de la quête
 * @param xpReward    La récompense en expérience de la quête
 * @param difficulty  Le niveau de difficulté de la quête
 */
public record Quest(
        /**
         * Id unique de la quête.
         * Ne doit pas être null.
         */
        String id,

        /**
         * Titre de la quête.
         */
        String title,

        /**
         * Description de la quête.
         */
        String description,

        /**
         * Récompense en expérience de la quête.
         */
        int xpReward,

        /**
         * Niveau de difficulté de la quête.
         */
        String difficulty) {
}