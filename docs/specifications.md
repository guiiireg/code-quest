# CodeQuest - Spécifications

## Présentation du projet
CodeQuest est une plateforme d'apprentissage de la programmation gamifiée. Au lieu d'avoir simplement des exercices classiques, la plateforme permet d'apprendre différentes technologies en réalisant des quêtes au sein de différents "mondes" (par exemple : Java, Spring Boot, Angular, Docker, Git). Chaque monde propose une progression unique avec des quêtes, des boss, des succès, des objets déblocables et un niveau d'expérience. 

L'approche est centrée sur la pratique. Par exemple, pour une quête demandant de corriger une API REST qui retourne des erreurs 500, le joueur soumet son code, qui est ensuite automatiquement compilé, testé et validé par le système.

## Public visé
Ce site web est conçu pour apprendre le code de manière ludique et interactive. Il s'adresse principalement :
- Aux étudiants en informatique souhaitant consolider leurs connaissances.
- Aux développeurs juniors cherchant à s'entraîner sur des technologies spécifiques et à monter en compétence de manière pratique.

## Objectifs
- Rendre l'apprentissage de la programmation plus engageant grâce à des mécaniques de jeu (gamification).
- Fournir un environnement de validation automatique de code fiable et rapide.
- Permettre aux utilisateurs de suivre leur progression de manière visuelle et motivante.
- Faciliter la transition entre la théorie et la pratique via des cas d'usage réels.

## MVP (Minimum Viable Product)
Le MVP se concentre sur le cœur de l'expérience utilisateur et les fonctionnalités de base :
- Inscription et authentification des utilisateurs.
- Connexion sécurisée.
- Création et gestion du profil utilisateur.
- Liste des quêtes disponibles, organisées par monde.
- Affichage des détails d'une quête (contexte, objectifs).
- Soumission et validation automatique d'une quête via le backend (compilation et exécution des tests).
- Gain d'expérience (XP) suite à la validation d'une quête.
- Système de montée de niveau basé sur l'XP accumulée.
- Tableau de bord utilisateur récapitulant la progression actuelle.

## Fonctionnalités futures
Une fois le MVP stabilisé, les évolutions suivantes sont envisagées pour enrichir la plateforme :
- Système de classement entre les joueurs.
- Profils publics avec un historique complet et des statistiques détaillées.
- Succès et badges à débloquer lors d'actions spécifiques.
- Combats de boss : des quêtes complexes de fin de niveau.
- Objets virtuels débloqués pour récompenser la progression.
- Ajout continu de nouveaux mondes pour couvrir davantage de technologies.

## Contraintes techniques
Le projet s'appuie sur une architecture et des technologies modernes.

Backend :
- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- PostgreSQL
- Docker (essentiel pour l'isolation et la validation automatique du code soumis)

Frontend :
- Angular
- Angular Material
- RxJS
