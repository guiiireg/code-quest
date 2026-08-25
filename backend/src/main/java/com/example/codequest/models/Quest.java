package com.example.codequest.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a quest in the application.
 * A quest contains a unique id, title, description, XP reward, and difficulty.
 */
@Entity
@Table(name = "quests")
public class Quest {

    /**
     * Unique identifier for the quest.
     */
    @Id
    private String id;

    /**
     * Title of the quest.
     */
    private String title;

    /**
     * Detailed quest instructions and description.
     */
    private String description;

    /**
     * Experience points (XP) rewarded upon completion.
     */
    private int xpReward;

    /**
     * Difficulty level (e.g. EASY, MEDIUM, HARD).
     */
    private String difficulty;

    /**
     * Starter code template provided to the user.
     */
    private String codeTemplate;

    /**
     * Regular expression used to validate user code submission.
     */
    private String testValidationRegex;

    /**
     * Sub-region or category name of the quest.
     */
    private String category;

    /**
     * Programming languages associated with this quest.
     */
    private String languages;

    /**
     * Technical concept taught by the quest.
     */
    private String concept;

    /**
     * Theoretical course content provided to the student.
     */
    @jakarta.persistence.Column(columnDefinition = "TEXT")
    private String theory;

    /**
     * The realm (world) this quest belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "world_id")
    @JsonIgnore
    private World world;

    /**
     * Default constructor required by JPA.
     */
    public Quest() {}

    /**
     * Constructor to create a quest without starter code.
     * 
     * @param id          Unique quest identifier
     * @param title       Quest title
     * @param description Quest description
     * @param xpReward    Experience reward
     * @param difficulty  Difficulty level
     */
    public Quest(String id, String title, String description, int xpReward, String difficulty) {
        this(id, title, description, xpReward, difficulty, null, null, null, null, null, null);
    }

    /**
     * Full constructor to initialize a quest with all metadata.
     * 
     * @param id                  Unique quest identifier
     * @param title               Quest title
     * @param description         Detailed description and instructions
     * @param xpReward            Experience points reward
     * @param difficulty          Difficulty level
     * @param codeTemplate        Initial starter code
     * @param testValidationRegex Regex validation pattern
     * @param category            Sub-region or category name
     * @param languages           Associated languages
     * @param concept             Technical concept taught
     * @param theory              Theoretical lesson content
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
     * Gets the unique quest identifier.
     * 
     * @return The quest identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique quest identifier.
     * 
     * @param id The new quest identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the quest title.
     * 
     * @return The quest title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the quest title.
     * 
     * @param title The new quest title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the quest description.
     * 
     * @return The quest description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the quest description.
     * 
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the experience points reward.
     * 
     * @return The XP reward
     */
    public int getXpReward() {
        return xpReward;
    }

    /**
     * Sets the experience points reward.
     * 
     * @param xpReward The new XP reward
     */
    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    /**
     * Gets the difficulty level.
     * 
     * @return The difficulty level
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level.
     * 
     * @param difficulty The new difficulty level
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the world this quest belongs to.
     * 
     * @return The associated world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Sets the world this quest belongs to.
     * 
     * @param world The new associated world
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Gets the initial starter code template.
     * 
     * @return The code template
     */
    public String getCodeTemplate() {
        return codeTemplate;
    }

    /**
     * Sets the initial starter code template.
     * 
     * @param codeTemplate The new starter code template
     */
    public void setCodeTemplate(String codeTemplate) {
        this.codeTemplate = codeTemplate;
    }

    /**
     * Gets the validation regex pattern.
     * 
     * @return The validation regex
     */
    public String getTestValidationRegex() {
        return testValidationRegex;
    }

    /**
     * Sets the validation regex pattern.
     * 
     * @param testValidationRegex The new validation regex pattern
     */
    public void setTestValidationRegex(String testValidationRegex) {
        this.testValidationRegex = testValidationRegex;
    }

    /**
     * Gets the sub-region or category name.
     * 
     * @return The category name
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the sub-region or category name.
     * 
     * @param category The new category name
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the programming languages associated with this quest.
     * 
     * @return The quest languages
     */
    public String getLanguages() {
        return languages;
    }

    /**
     * Sets the programming languages associated with this quest.
     * 
     * @param languages The new languages string
     */
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    /**
     * Gets the technical concept taught by this quest.
     * 
     * @return The concept
     */
    public String getConcept() {
        return concept;
    }

    /**
     * Sets the technical concept taught by this quest.
     * 
     * @param concept The new concept
     */
    public void setConcept(String concept) {
        this.concept = concept;
    }

    /**
     * Gets the theoretical lesson content.
     * 
     * @return The theory text
     */
    public String getTheory() {
        return theory;
    }

    /**
     * Sets the theoretical lesson content.
     * 
     * @param theory The new theory text
     */
    public void setTheory(String theory) {
        this.theory = theory;
    }
}