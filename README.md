# Code Quest

**Code Quest** est une plateforme d'apprentissage et d'entraînement à la programmation sous forme de jeu de rôle. Les développeurs résolvent des quêtes de code dans différents mondes virtuels pour gagner de l'expérience (XP) et monter en niveau.

---

## Architecture du Projet

Le projet est structuré sous forme de monorepo séparé en deux modules principaux et une base de données conteneurisée :

```text
code-quest/
├── backend/          # API REST Java Spring Boot
├── frontend/         # Application Web Angular 19
├── docker-compose.yml # Service PostgreSQL 16
└── README.md
```

---

## Stack Technique

### Base de Données
- **PostgreSQL 16** (Alpine)
- Gérée via Docker Compose sur le port **5432**
- Identifiants par défaut : `postgres` / `postgres` (Base: `codequest`)

### Backend
- **Java 21** / **Spring Boot 4.1**
- **Spring Security** avec authentification sans état via **JWT**
- **Spring Data JPA** & Hibernate ORM
- Compilation et exécution dynamique de code utilisateur Java
- Port du serveur backend : **8081**

### Frontend
- **Angular 19** & **Angular Material**
- Design réactif avec **RxJS**
- Dev Server écoutant sur **http://localhost:4200**
- Configuration de proxy dev (`proxy.conf.json`) redirigeant `/api` vers `http://localhost:8081`

---

## Contenu Actuel de l'Application

L'application propose les mondes et quêtes d'apprentissage suivants :

1. **Terre du Code** (`world-1`)
   - *Syntaxe & Variables* (`q1`) — Déclaration de variables et bases de Java (100 XP).
   - *Structures conditionnelles* (`q2`) — Manipulation des conditions `if/else` (250 XP).

2. **Archipel des APIs** (`world-2`)
   - *Design d'API REST* (`q3`) — Principes des codes de réponse HTTP et requêtes REST (400 XP).

---

## Guide de Démarrage Rapide

### 1. Démarrer la Base de Données
Assurez-vous que Docker est démarré, puis lancez le conteneur PostgreSQL :
```bash
docker compose up -d
```

### 2. Démarrer le Backend (Spring Boot)
Dans un terminal :
```bash
cd backend
./mvnw spring-boot:run
```
> Le backend sera accessible sur `http://localhost:8081`. Il réinitialise et pré-remplit automatiquement la base de données via `schema.sql` et `data.sql`.

### 3. Démarrer le Frontend (Angular)
Dans un second terminal :
```bash
cd frontend
npm start
```
> Ouvrez votre navigateur sur **`http://localhost:4200`**.

---

## Exécution des Tests

### Backend
Pour lancer la suite de tests automatisés Spring Boot / JUnit 5 (incluant la sécurité et le contrôleur de quêtes) :
```bash
cd backend
./mvnw test
```

### Frontend
Pour lancer les tests unitaires Angular avec Karma & Jasmine :
```bash
cd frontend
npm test
```

---

## Conventions et Maintenance

- **Mise à jour automatique de ce README** : À chaque ajout, modification de structure ou changement majeur dans le projet (nouvelles dépendances, nouvelles quêtes, modification de port ou d'architecture), ce fichier [`README.md`](file:///home/guiregnael/Documents/Personal/code-quest/README.md) doit être mis à jour.
