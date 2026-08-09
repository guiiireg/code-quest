package com.example.codequest.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Représente une quête dans l'application.
 * Une quête contient un id unique, un titre, une description, une récompense en
 * expérience et une difficulté.
 */
@Entity
@Table(name = "quests")
public class Quest {

    /**
     * Id unique de la quête.
     */
    @Id
    private String id;

    /**
     * Titre de la quête.
     */
    private String title;

    /**
     * Description de la quête.
     */
    private String description;

    /**
     * Récompense en expérience de la quête.
     */
    private int xpReward;

    /**
     * Niveau de difficulté de la quête.
     */
    private String difficulty;

    /**
     * Modèle de code initial fourni à l'utilisateur.
     */
    private String codeTemplate;

    /**
     * Expression régulière de validation pour vérifier la soumission.
     */
    private String testValidationRegex;

    /**
     * Sous-région ou catégorie de la quête.
     */
    private String category;

    /**
     * Langage(s) utilisé(s) dans la quête.
     */
    private String languages;

    /**
     * Concept technique abordé par la quête.
     */
    private String concept;

    /**
     * Explications théoriques détaillées fournies à l'élève.
     */
    @jakarta.persistence.Column(columnDefinition = "TEXT")
    private String theory;

    /**
     * Le monde auquel appartient cette quête.
     */
    @ManyToOne
    @JoinColumn(name = "world_id")
    @JsonIgnore
    private World world;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Quest() {}

    /**
     * Constructeur pour créer une quête sans modèle de code de départ.
     * 
     * @param id          L'id unique de la quête
     * @param title       Le titre de la quête
     * @param description La description détaillée de la quête
     * @param xpReward    La récompense en expérience de la quête
     * @param difficulty  Le niveau de difficulté de la quête
     */
    public Quest(String id, String title, String description, int xpReward, String difficulty) {
        this(id, title, description, xpReward, difficulty, null, null, null, null, null, null);
    }

    /**
     * Constructeur complet pour créer une instance de quête avec tous les détails.
     * 
     * @param id                  L'id unique de la quête
     * @param title               Le titre de la quête
     * @param description         La description détaillée de la quête
     * @param xpReward            La récompense en expérience de la quête
     * @param difficulty          Le niveau de difficulté de la quête
     * @param codeTemplate        Le modèle de code de départ
     * @param testValidationRegex L'expression de validation de la quête
     * @param category            La sous-région ou catégorie de la quête
     * @param languages           Les langages associés à l'épreuve
     * @param concept             Le concept théorique/pratique enseigné
     * @param theory              Le contenu du cours théorique
     */
    public Quest(String id, String title, String description, int xpReward, String difficulty, String codeTemplate, String testValidationRegex, String category, String languages, String concept, String theory) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.xpReward = xpReward;
        this.difficulty = difficulty;
        this.codeTemplate = codeTemplate;
        this.testValidationRegex = testValidationRegex;
        this.category = category;
        this.languages = languages;
        this.concept = concept;
        this.theory = theory;
    }

    public Quest(String id, String title, String description, int xpReward, String difficulty, String codeTemplate, String testValidationRegex, String category, String languages, String concept) {
        this(id, title, description, xpReward, difficulty, codeTemplate, testValidationRegex, category, languages, concept, null);
    }

    public Quest(String id, String title, String description, int xpReward, String difficulty, String codeTemplate, String testValidationRegex, String category) {
        this(id, title, description, xpReward, difficulty, codeTemplate, testValidationRegex, category, null, null, null);
    }

    public Quest(String id, String title, String description, int xpReward, String difficulty, String codeTemplate, String testValidationRegex) {
        this(id, title, description, xpReward, difficulty, codeTemplate, testValidationRegex, null, null, null, null);
    }

    /**
     * Récupère l'identifiant unique de la quête.
     * 
     * @return L'identifiant de la quête
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la quête.
     * 
     * @param id Le nouvel identifiant de la quête
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Récupère le titre de la quête.
     * 
     * @return Le titre de la quête
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit le titre de la quête.
     * 
     * @param title Le nouveau titre de la quête
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Récupère la description de la quête.
     * 
     * @return La description de la quête
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description de la quête.
     * 
     * @param description La nouvelle description de la quête
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Récupère l'expérience gagnée comme récompense de la quête.
     * 
     * @return Le nombre de points d'expérience
     */
    public int getXpReward() {
        return xpReward;
    }

    /**
     * Définit l'expérience gagnée comme récompense de la quête.
     * 
     * @param xpReward Le nombre de points d'expérience de récompense
     */
    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    /**
     * Récupère la difficulté de la quête.
     * 
     * @return La difficulté de la quête
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Définit la difficulté de la quête.
     * 
     * @param difficulty La nouvelle difficulté de la quête
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Récupère le monde auquel appartient cette quête.
     * 
     * @return Le monde associé
     */
    public World getWorld() {
        return world;
    }

    /**
     * Définit le monde auquel appartient cette quête.
     * 
     * @param world Le nouveau monde associé
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Récupère le modèle de code fourni pour démarrer la quête.
     * 
     * @return Le modèle de code source
     */
    public String getCodeTemplate() {
        return codeTemplate;
    }

    /**
     * Définit le modèle de code fourni pour démarrer la quête.
     * 
     * @param codeTemplate Le nouveau modèle de code source
     */
    public void setCodeTemplate(String codeTemplate) {
        this.codeTemplate = codeTemplate;
    }

    /**
     * Récupère l'expression régulière de validation de la quête.
     * 
     * @return L'expression régulière de validation
     */
    public String getTestValidationRegex() {
        return testValidationRegex;
    }

    /**
     * Définit l'expression régulière de validation de la quête.
     * 
     * @param testValidationRegex La nouvelle expression de validation
     */
    public void setTestValidationRegex(String testValidationRegex) {
        this.testValidationRegex = testValidationRegex;
    }

    /**
     * Récupère la sous-région ou catégorie de la quête.
     * 
     * @return La sous-région ou catégorie de la quête
     */
    public String getCategory() {
        return category;
    }

    /**
     * Définit la sous-région ou catégorie de la quête.
     * 
     * @param category La nouvelle sous-région ou catégorie
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Récupère les langages associés à la quête.
     * 
     * @return Les langages de la quête
     */
    public String getLanguages() {
        return languages;
    }

    /**
     * Définit les langages associés à la quête.
     * 
     * @param languages Les nouveaux langages
     */
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    /**
     * Récupère le concept technique enseigné par la quête.
     * 
     * @return Le concept technique de la quête
     */
    public String getConcept() {
        return concept;
    }

    /**
     * Définit le concept technique enseigné par la quête.
     * 
     * @param concept Le nouveau concept technique
     */
    public void setConcept(String concept) {
        this.concept = concept;
    }

    /**
     * Récupère le contenu théorique du cours de la quête.
     * 
     * @return Le cours théorique
     */
    public String getTheory() {
        return theory;
    }

    /**
     * Définit le contenu théorique du cours de la quête.
     * 
     * @param theory Le nouveau cours théorique
     */
    public void setTheory(String theory) {
        this.theory = theory;
    }
}