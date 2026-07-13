<div align="center">
  <h1>CodeQuest</h1>
  <p>Une plateforme d'apprentissage de la programmation par la gamification</p>

  <div>
    <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java 21" />
    <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot" />
    <img src="https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white" alt="Angular" />
    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
    <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  </div>
</div>

<br>

## À propos de CodeQuest

CodeQuest est une plateforme innovante conçue pour apprendre différentes technologies (Java, Spring Boot, Angular, Docker, Git, etc.) en réalisant des quêtes au sein de "mondes" thématiques.

Plutôt que de simples exercices théoriques, l'utilisateur est immergé dans un environnement gamifié proposant une progression par niveaux, des récompenses sous forme d'expérience (XP) et une validation automatisée du code en environnement réel.

**Public visé :** Étudiants en informatique et développeurs juniors cherchant à monter en compétence par la pratique.

## Fonctionnalités Principales (MVP)

- **Mondes thématiques :** Apprentissage catégorisé par technologie.
- **Quêtes interactives :** Résolution de problèmes concrets (ex: corriger une API REST).
- **Validation Automatisée :** Compilation et exécution des tests unitaires en environnement isolé (Docker).
- **Gamification :** Gain de points d'expérience (XP), montée en niveau, tableau de bord détaillé.
- **Système de comptes :** Authentification sécurisée et suivi complet de la progression.

## Technologies de pointe

<details>
  <summary><strong>Déplier pour voir les détails techniques</strong></summary>

### Backend

- **Langage principal :** Java 21
- **Framework :** Spring Boot
- **Sécurité :** Spring Security, JWT (JSON Web Tokens)
- **Base de données :** PostgreSQL
- **Exécution isolée :** Docker (pour la validation du code soumis)

### Frontend

- **Framework :** Angular
- **Composants UI :** Angular Material
- **Réactivité :** RxJS

</details>

## État d'avancement actuel

Le projet est en cours de développement (MVP). Voici ce qui a été implémenté :
- **Environnement & Docker** : PostgreSQL et Adminer configurés via `docker-compose`.
- **Base de données** : Schémas SQL générés via Flyway (`V1` et `V2`).
- **Couche Entités (JPA)** : `User`, `QuestCategory`, `Quest`, `QuestSubmission`, `Badge`, `Achievement`.
- **Couche Données (Repositories)** : Création des interfaces Spring Data JPA avec des méthodes de recherche personnalisées (`UserRepository`, `QuestCategoryRepository`, `QuestRepository`).
- **Tests Unitaires** : Couverture des tests d'intégration avec la base H2 en mémoire (`@SpringBootTest`).

## Documentation

La documentation détaillée du projet est disponible dans le répertoire `docs/` :

- [Spécifications Techniques et Fonctionnelles](docs/specifications.md)
- [Schéma et Architecture de la Base de Données](docs/database.md)
- [Feuille de Route (Roadmap)](docs/roadmap.md)

## Démarrage Rapide

_Les instructions pour le déploiement en environnement de développement local seront ajoutées lors de la finalisation du MVP._

```bash
# Exemple de processus d'installation :
# git clone https://github.com/guiiireg/code-quest.git
# cd code-quest
# docker-compose up -d
```
