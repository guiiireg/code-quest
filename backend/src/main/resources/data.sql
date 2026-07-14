-- Nettoyage des anciennes données
DELETE FROM quests;
DELETE FROM worlds;

-- Insertion des mondes
INSERT INTO worlds (id, name, description) VALUES
('world-test', 'Monde test', 'Maitrister'),
('world-1', 'Terre du Code', 'Le point de départ de tout développeur. Apprenez les bases de la syntaxe et des structures de données.'),
('world-2', 'Archipel des APIs', 'Maîtrisez les échanges HTTP, la conception de routes REST et la sécurité des données.');

-- Insertion des quêtes
INSERT INTO quests (id, title, description, xp_reward, difficulty, world_id) VALUES
('quest-1', 'corriger', 'api', 150, 'EASY', 'world-test'),
('q1', 'Syntaxe & Variables', 'Déclarez des variables de différents types et comprenez leur portée.', 100, 'EASY', 'world-1'),
('q2', 'Structures conditionnelles', 'Créez des algorithmes de décision complexes utilisant des conditions imbriquées.', 250, 'MEDIUM', 'world-1'),
('q3', 'Design d''API REST', 'Modélisez et documentez une ressource REST complète avec ses différents verbes HTTP.', 400, 'HARD', 'world-2');
