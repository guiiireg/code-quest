# Code Quest

**Code Quest** est une plateforme d'apprentissage et d'entraînement au développement web et logiciel sous forme de jeu de rôle (RPG).
Les développeurs explorent des régions thématiques, résolvent des épreuves de programmation interactives dans un éditeur de code embarqué, gagnent des points d'expérience (XP) et montent en niveau.

---

## Architecture du Projet

Le projet est structuré sous forme de monorepo composé de deux modules applicatifs indépendants et d'une base de données conteneurisée :

```text
code-quest/
├── backend/          # API REST Java 21 / Spring Boot 4.1.0
├── frontend/         # Application Web SPA Angular 19 / TypeScript
├── docker-compose.yml # Service de persistance PostgreSQL 16 Alpine
└── README.md         # Documentation du projet
```

### Flux de Données & Communication
* **Frontend (Angular 19)** :
  - Écoute sur `http://localhost:4200`.
  - Architecture moderne en composants **Standalone** et réactivité par **Angular Signals**.
  - Éditeur de code intégré **Monaco Editor** avec coloration syntaxique adaptative.
  - Proxy de développement (`proxy.conf.json`) redirigeant automatiquement les appels `/api` vers `http://localhost:8081`.
  - Authentification sans état via jeton JWT transmis par en-tête HTTP (`Authorization: Bearer <token>`).
* **Backend (Spring Boot 4.1.0)** :
  - Écoute sur le port `8081`.
  - Sécurité stateless via **Spring Security** et validation des jetons JWT.
  - Modélisation des requêtes/réponses d'API via les **Java Records**.
  - Évaluation et validation dynamique du code soumis (Sandbox sécurisée conteneurisée ou analyse structurelle par Regex).
* **Base de Données (PostgreSQL 16 Alpine)** :
  - Écoute sur le port `5432` (Base : `codequest`).
  - Schéma et jeux de données d'apprentissage initialisés via `schema.sql` et `data.sql`.

---

## Stack Technique

| Composant | Technologie | Rôle & Description |
| :--- | :--- | :--- |
| **Backend** | **Java 21** / **Spring Boot 4.1.0** | API REST, gestion des mondes/quêtes, sécurité et évaluation de code. |
| **Sécurité** | **Spring Security** / **JJWT 0.11.5** | Authentification JWT sans état, contrôle d'accès et hashage BCrypt. |
| **ORM & BDD** | **Spring Data JPA** / **Hibernate** | Gestion des entités relationnelles et repositories. |
| **Base de Données** | **PostgreSQL 16 Alpine** | Persistance relationnelle (mondes, quêtes, utilisateurs, rôles). |
| **Frontend** | **Angular 19** / **TypeScript 5.7** | Interface Single Page Application (SPA), Standalone Components, Signals. |
| **Interface UI** | **Angular Material 19** | Thème RPG Dark Fantasy (`azure-blue.css`), composants graphiques et navigation. |
| **Éditeur de Code** | **Monaco Editor** | Éditeur de code interactif avec support multi-langages et raccourcis. |
| **Build & Tooling** | **Maven Wrapper 3.9.6** / **npm** | Gestion des dépendances et cycles de vie de compilation. |
| **Conteneurisation** | **Docker Compose** | Orchestration de l'environnement local de base de données. |

---

## Contenu & Parcours Pédagogique

L'application propose un cursus complet d'apprentissage découpé en régions thématiques et sous-domaines :

### 1. 📜 Région 1 — HTML5 (`world-1`)
Le point de départ fondamental du développement web et de la structuration sémantique :
- **Structure ancestrale** : Modèle Client / Serveur, interprétation HTML, arbre DOM, DOCTYPE et attributs fondamentaux (`id`, `class`, `lang`).
- **Grimoire du `<head>`** : Métadonnées, encodage UTF-8, viewport responsive, `title`, SEO meta description, favicon, canonical et Open Graph.
- **Texte & Hiérarchie** : Titres `<h1>` à `<h6>`, paragraphes `<p>`, sauts de ligne `<br>`, emphase sémantique `<strong>`/`<em>`, surlignage `<mark>`, citations `<blockquote>`.
- **Listes & Inventaires** : Listes non ordonnées `<ul>`, ordonnées `<ol>`, imbrications et listes de définitions `<dl>`.
- **Liens & Navigation** : Balise `<a>`, chemins relatifs/absolus, ancres intra-page, protocoles `mailto:`/`tel:`, attributs de sécurité `target="_blank"` et `rel="noopener noreferrer"`.
- **Médias & Intégrations** : Images `<img>` avec texte alternatif `alt`, légendes `<figure>`/`<figcaption>`, images responsives `<picture>`/`srcset`, lecteurs audio `<audio controls>` et vidéo `<video controls>`, sous-titres `<track>` et intégrations `<iframe>`.
- **Sémantique HTML5** : Découpage structurel moderne (`<header>`, `<nav>`, `<main>`, `<section>`, `<article>`, `<aside>`, `<footer>`, `<address>`, `<time>`).
- **Formulaires & Collecte** : Formulaires `<form>`, champs `<input>`, zones de texte `<textarea>`, menus déroulants `<select>`, suggestions `<datalist>`, validation native et groupements `<fieldset>`/`<legend>`.
- **Tableaux de Données** : Structuration `<table>`, `<thead>`, `<tbody>`, `<tfoot>`, cellules d'en-tête `<th>`, attributs de portée `scope` et fusions `colspan`/`rowspan`.
- **Éléments Interactifs Natifs** : Accordéons repliables `<details>`/`<summary>`, modales natives `<dialog>` et API Popover.
- **Accessibilité (a11y)** : Balises sémantiques vs génériques, attributs `alt`, rôles et noms accessibles `aria-label`.
- **Performance Web** : Chargement asynchrone des scripts `defer`/`async`, resource hints `preload`/`preconnect`, lazy loading `loading="lazy"` et formats modernes WebP.
- **Sécurité Web** : Neutralisation des injections XSS, sandbox `<iframe>`, politiques CSP (`Content-Security-Policy`) et contraintes de validation `pattern`.
- **Qualité & Évaluation** : Validation standard W3C, nettoyage d'éléments obsolètes, inspection DevTools et quiz de validation de connaissances.

### 2. ♿ Région 2 — Accessibilité Web (A11y) (`world-2`)
Le Royaume de l'Inclusion et des standards WCAG 2.2 AA :
- **Fondements de l'A11y** : Noms accessibles, attributs `aria-label`, typologie des handicaps et design universel.
- **Navigation au Clavier** : Ordre naturel du focus, gestion du `tabindex`, pièges au clavier et raccourcis de saut de contenu (*Skip Links*).
- **Lecteurs d'Écran & WAI-ARIA** : Rôles sémantiques, alertes dynamiques `aria-live="polite"`/`assertive`, états interactifs `aria-expanded`, descriptions `aria-describedby` et masquage contrôlé `aria-hidden`.
- **Formulaires & Contrastes** : Associations strictes `<label for>`, messages d'erreur accessibles et ratios de contraste conformes aux normes WCAG.

### 3. 🎨 Région 3 — CSS3 (`world-3`)
Le Domaine des Formes, des Couleurs et de la Mise en Page moderne :
- **Box Model & Fondations** : Marges, bordures, espacements internes (`padding`) et `box-sizing: border-box`.
- **Mise en Page Flexbox** : Axes principaux et secondaires, alignements, justifications, direction et flexibilité réactive.
- **Mise en Page CSS Grid** : Grilles bidimensionnelles, zones nommées `grid-template-areas`, unités fractionnaires `fr` et fonctions `minmax()`.
- **Responsive Design** : Media queries `@media`, approches *Mobile-First*, unités relatives (`rem`, `vw`, `vh`, `cqw`).
- **Variables & Thèmes** : Custom properties CSS (`--rpg-gold`, etc.), cascades et thématisation dynamique.
- **Animations & Effets** : Transitions fluides, transformations 2D/3D et animations temporelles `@keyframes`.

---

## Système de Gamification RPG

- **Expérience & Niveaux** :
  1. *Niveau 1* — **Initié du Code** (0 à 199 XP)
  2. *Niveau 2* — **Apprenti Développeur** (200 à 499 XP)
  3. *Niveau 3* — **Compagnon du Code** (500 à 999 XP)
  4. *Niveau 4* — **Archimage** (1000 à 1999 XP)
  5. *Niveau 5* — **Légende de CodeQuest** (2000+ XP)
- **Déverrouillage Séquentiel** : Chaque épreuve d'une région requiert l'accomplissement de l'épreuve précédente pour être débloquée.
- **Fiche de Personnage** : Visualisation en temps réel de la progression d'XP, du niveau actuel, du total de quêtes réussies, des trophées obtenus et des compétences acquises.
- **Sauvegarde Locale des Brouillons** : Tous les codes saisis dans l'éditeur sont persistés en continu dans le stockage local du navigateur.

---

## Contrats d'API REST

### Authentification (`/api/auth`)
* `POST /api/auth/signup` : Inscription d'un nouvel utilisateur (nom d'utilisateur, email, mot de passe).
* `POST /api/auth/signin` : Connexion et récupération du jeton JWT (`token`, `username`, `email`, `roles`).

### Mondes & Quêtes (`/api`)
* `GET /api/worlds` : Récupération de la liste complète des mondes avec leurs quêtes associées.
* `GET /api/worlds/{id}` : Récupération des détails d'un monde spécifique.
* `GET /api/quests/{id}` : Récupération des données d'une quête (consignes, cours théorique, template initial, XP, difficulté).
* `POST /api/quests/{id}/submit` : Soumission de code utilisateur pour évaluation et attribution d'XP.

---

## Guide de Démarrage Rapide

### Prérequis
- **Java 21** (OpenJDK ou équivalent).
- **Node.js** (v20+ ou v26) et **npm**.
- **Docker** et **Docker Compose**.

---

### 1. Démarrer la Base de Données (PostgreSQL 16)
Assurez-vous que le service Docker est actif, puis lancez le conteneur PostgreSQL :
```bash
docker compose up -d
```
> Le serveur de base de données sera disponible sur `localhost:5432` (Base : `codequest`, utilisateur : `postgres`).

---

### 2. Démarrer le Backend (Spring Boot)
Dans un premier terminal :
```bash
cd backend
./mvnw spring-boot:run
```
> L'API REST sera accessible sur **`http://localhost:8081`**. Spring Boot initialise et pré-remplit les mondes et quêtes via `schema.sql` et `data.sql`.

---

### 3. Démarrer le Frontend (Angular 19)
Dans un second terminal :
```bash
cd frontend
npm install
npm start
```
> L'application web démarre sur **`http://localhost:4200`** avec redirection transparente des requêtes `/api` vers le backend via le proxy de développement.

---

## Exécution des Tests

### Backend (JUnit 5 & MockMvc)
Pour exécuter la suite de tests automatisés Spring Boot (contrôleurs, validation et sécurité) :
```bash
cd backend
./mvnw test
```

### Frontend (Karma & Jasmine)
Pour lancer les tests unitaires des composants et services Angular :
```bash
cd frontend
npm test
```

---

## Maintenance & Conventions

- **Règle de Synchronisation** : Toute modification de l'architecture, ajout de dépendances majeures, évolution du schéma relationnel, changement de ports ou ajout de nouvelles quêtes pédagogiques doit obligatoirement s'accompagner de la mise à jour de ce fichier `README.md`.
