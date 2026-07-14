-- Nettoyage des anciennes données
DELETE FROM quests;
DELETE FROM worlds;

-- Insertion des mondes
INSERT INTO worlds (id, name, description) VALUES
('world-test', 'Monde test', 'Maitrister'),
('world-1', 'Terre du Code', 'Le point de départ de tout développeur. Apprenez les bases de la syntaxe et des structures de données.'),
('world-2', 'Archipel des APIs', 'Maîtrisez les échanges HTTP, la conception de routes REST et la sécurité des données.');

-- Insertion des quêtes
INSERT INTO quests (id, title, description, xp_reward, difficulty, code_template, test_validation_regex, world_id) VALUES
('quest-1', 'corriger', 'api', 150, 'EASY', 
 '// Écrivez une méthode qui retourne "Hello World"
public class Solution {
    public static String getHello() {
        return "";
    }
}', 
 '(?s).*return\s+["'']Hello World["''].*', 
 'world-test'),

('q1', 'Syntaxe & Variables', 'Déclarez une variable nommée "monde" de type String contenant "CodeQuest".', 100, 'EASY', 
 '// Déclarez une variable nommée ''monde'' de type String contenant "CodeQuest"
public class Solution {
    public static void run() {
        // Votre code ici
        
    }
}', 
 '(?s).*String\s+monde\s*=\s*["'']CodeQuest["''].*', 
 'world-1'),

('q2', 'Structures conditionnelles', 'Écrivez une méthode estMajeur qui prend un int age et retourne true si l''âge est supérieur ou égal à 18, sinon false.', 250, 'MEDIUM', 
 '// Écrivez une méthode qui retourne true si l''âge est supérieur ou égal à 18, sinon false
public class Solution {
    public static boolean estMajeur(int age) {
        return false;
    }
}', 
 '(?s).*return\s+age\s*>=\s*18.*|(?s).*if\s*\(\s*age\s*>=\s*18\s*\).*return\s+true.*return\s+false.*', 
 'world-1'),

('q3', 'Design d''API REST', 'Complétez la méthode pour retourner le code HTTP de succès standard lors de la création d''une ressource.', 400, 'HARD', 
 '// Complétez la méthode pour retourner le code HTTP de succès standard d''une création de ressource (201)
public class Solution {
    public static int getCreatedStatusCode() {
        return 0;
    }
}', 
 '(?s).*return\s+201.*', 
 'world-2');
