# Code Quest

**Live Website / Demo:** [https://code-quest-bv1.pages.dev](https://code-quest-bv1.pages.dev)  
**REST API (Backend):** [https://code-quest-api-klr7.onrender.com](https://code-quest-api-klr7.onrender.com)

> **Note pour les recruteurs (FR) :** *Code Quest est une plateforme full-stack gamifiée (style RPG) d'apprentissage du développement web et logiciel (Angular 19, Spring Boot 4.1, Java 21, PostgreSQL 16). La documentation et l'intégralité du code source sont rédigées en anglais conformément aux standards professionnels de l'ingénierie logicielle.*

---

**Code Quest** is an interactive, gamified RPG-style learning and training platform for web and software development.
Developers explore thematic regions, solve interactive coding challenges directly within an embedded code editor, earn experience points (XP), and level up.

---

## System Architecture

The project is structured as a monorepo containing two decoupled application modules and a containerized database service:

```text
code-quest/
├── backend/           # REST API (Java 21 / Spring Boot 4.1.0)
├── frontend/          # Single Page Application (Angular 19 / TypeScript 5.7)
├── docker-compose.yml # PostgreSQL 16 Alpine Database Service
└── README.md          # Project Documentation
```

### Data Flow & Communication
* **Frontend (Angular 19)**:
  - Runs on `http://localhost:4200`.
  - Built with modern **Standalone Components** and reactive state management via **Angular Signals**.
  - Integrated **Monaco Editor** with dynamic language syntax highlighting.
  - Development proxy (`proxy.conf.json`) forwarding `/api` requests to `http://localhost:8081`.
  - Stateless authentication via JWT tokens injected into outgoing HTTP headers (`Authorization: Bearer <token>`).
* **Backend (Spring Boot 4.1.0)**:
  - Runs on port `8081`.
  - Stateless security using **Spring Security** and JWT token validation.
  - Data transfer objects modeled with modern **Java Records**.
  - Dynamic code evaluation engine (isolated containerized sandbox or structural regex-based validation).
* **Database (PostgreSQL 16 Alpine)**:
  - Runs on port `5432` (Database: `codequest`).
  - Schema and educational quest dataset automatically initialized on startup via `schema.sql` and `data.sql`.

---

## Tech Stack

| Component | Technology | Description |
| :--- | :--- | :--- |
| **Backend** | **Java 21** / **Spring Boot 4.1.0** | REST API, quest/world domain logic, security, and code evaluation. |
| **Security** | **Spring Security** / **JJWT 0.11.5** | Stateless JWT authentication, role-based authorization, BCrypt hashing. |
| **Persistence** | **Spring Data JPA** / **Hibernate** | Relational entity mappings, CRUD repositories, cascading relationships. |
| **Database** | **PostgreSQL 16 Alpine** | Relational data storage for worlds, quests, users, and roles. |
| **Frontend** | **Angular 19** / **TypeScript 5.7** | Single Page Application (SPA), Standalone architecture, Angular Signals. |
| **UI Library** | **Angular Material 19** | RPG Dark Fantasy theme (`azure-blue.css`), responsive layouts. |
| **Code Editor** | **Monaco Editor** | Embedded in-browser code editor with multi-language syntax support. |
| **Build & Tooling** | **Maven Wrapper 3.9.6** / **npm** | Dependency management, compilation, and automated packaging. |
| **Containerization**| **Docker Compose** | Local orchestration for the PostgreSQL 16 database instance. |

---

## Educational Curriculum & Quest Content

The platform features a structured learning pathway divided into thematic realms:

### 1. Realm 1 — HTML5 (`world-1`)
Foundational web architecture and semantic structuring:
- **Core Structure**: Client / Server architecture, HTML parsing, DOM tree, DOCTYPE declaration, and core attributes (`id`, `class`, `lang`).
- **The `<head>` Grimoire**: Metadata, UTF-8 charset, responsive viewport, `<title>`, SEO description, favicon, canonical links, and Open Graph protocol.
- **Text & Hierarchy**: Headings `<h1>` to `<h6>`, paragraphs `<p>`, line breaks `<br>`, semantic emphasis `<strong>`/`<em>`, highlighting `<mark>`, blockquotes `<blockquote>`.
- **Lists & Inventories**: Unordered lists `<ul>`, ordered lists `<ol>`, nested structures, definition lists `<dl>`.
- **Hyperlinks & Navigation**: Anchor tags `<a>`, relative/absolute paths, fragment identifiers (anchors), `mailto:`/`tel:`, security attributes `target="_blank"` and `rel="noopener noreferrer"`.
- **Media & Embedding**: Images `<img>` with accessible `alt` text, `<figure>`/`<figcaption>`, responsive `<picture>`/`srcset`, native `<audio controls>` and `<video controls>`, subtitles `<track>`, and `<iframe>` embeds.
- **Semantic HTML5**: Modern structural landmarks (`<header>`, `<nav>`, `<main>`, `<section>`, `<article>`, `<aside>`, `<footer>`, `<address>`, `<time>`).
- **Forms & User Input**: Forms `<form>`, inputs `<input>`, multiline `<textarea>`, select menus `<select>`, autocompletion `<datalist>`, native validation, and fieldsets `<fieldset>`/`<legend>`.
- **Data Tables**: Table layouts `<table>`, `<thead>`, `<tbody>`, `<tfoot>`, header cells `<th>`, accessibility `scope`, cell spans `colspan`/`rowspan`.
- **Native Interactive Elements**: Collapsible accordions `<details>`/`<summary>`, modal dialogs `<dialog>`, Popover API.
- **Web Accessibility (A11y)**: Semantic landmarks vs generic containers, `alt` descriptions, ARIA roles, and accessible naming with `aria-label`.
- **Web Performance**: Asynchronous script execution `defer`/`async`, resource hints `preload`/`preconnect`, lazy loading `loading="lazy"`, modern WebP image formats.
- **Web Security**: Cross-Site Scripting (XSS) mitigation, `<iframe>` sandbox isolation, Content Security Policy (CSP), input pattern constraints.
- **Standards & Quality**: W3C compliance, deprecation cleanup, DevTools DOM inspection, and knowledge evaluation quizzes.

### 2. Realm 2 — Web Accessibility (A11y) (`world-2`)
Universal design and WCAG 2.2 AA standards:
- **A11y Fundamentals**: Accessible names, `aria-label`, disability spectrums, inclusive design principles.
- **Keyboard Navigation**: Natural tab order, focus management, `tabindex` rules, keyboard trap prevention, Skip Links.
- **Screen Readers & WAI-ARIA**: Semantic roles, dynamic live regions `aria-live="polite"`/`assertive`, interactive state flags `aria-expanded`, descriptions `aria-describedby`, hidden elements `aria-hidden`.
- **Accessible Forms & Color Contrast**: Explicit `<label for>` bindings, error message associations, WCAG-compliant contrast ratios.

### 3. Realm 3 — CSS3 (`world-3`)
Styling, modern layout systems, and responsive visual design:
- **Box Model & Fundamentals**: Margins, borders, padding, and `box-sizing: border-box`.
- **Flexbox Layout**: Main and cross axes, alignments, justifications, direction, flex wrap and item ordering.
- **CSS Grid Layout**: Two-dimensional grids, named template areas `grid-template-areas`, fractional units `fr`, `minmax()` functions.
- **Responsive Web Design**: Media queries `@media`, mobile-first paradigms, relative viewport units (`rem`, `vw`, `vh`, `cqw`).
- **CSS Variables & Theming**: Custom properties (`--rpg-gold`, etc.), cascading inheritance, dynamic theme switching.
- **Transitions & Keyframe Animations**: Smooth state transitions, 2D/3D transforms, `@keyframes` timeline animations.

---

## RPG Gamification System

- **Levels & Experience (XP) Thresholds**:
  1. *Level 1* — **Code Initiate** (0 to 199 XP)
  2. *Level 2* — **Apprentice Developer** (200 to 499 XP)
  3. *Level 3* — **Code Journeyman** (500 to 999 XP)
  4. *Level 4* — **Archmage** (1000 to 1999 XP)
  5. *Level 5* — **Legend of CodeQuest** (2000+ XP)
- **Sequential Progression**: Quests in each realm unlock sequentially upon successful completion of prerequisite quests.
- **Character Dashboard**: Real-time visualization of current level, XP progress bar, completed quest counter, unlocked skills, and achievement trophies.
- **Persistent Code Drafts**: In-progress code solutions are continuously auto-saved in local browser storage per quest.

---

## REST API Specification

### Authentication (`/api/auth`)
* `POST /api/auth/signup`: Register a new user (`username`, `email`, `password`).
* `POST /api/auth/signin`: Authenticate credentials and receive JWT bearer token (`token`, `id`, `username`, `email`, `roles`).

### Worlds & Quests (`/api`)
* `GET /api/worlds`: List all available realms with nested quest metadata.
* `GET /api/worlds/{id}`: Retrieve realm details and its quest list.
* `GET /api/quests/{id}`: Retrieve quest details (instructions, theory lesson, initial template, XP reward, difficulty).
* `POST /api/quests/{id}/submit`: Submit solution code for validation and experience attribution.

---

## Quick Start Guide

### Prerequisites
- **Java 21** (OpenJDK or compatible distribution).
- **Node.js** (v20+ or v26) and **npm**.
- **Docker** and **Docker Compose**.

---

### 1. Start the Database (PostgreSQL 16)
Ensure Docker is running, then start the PostgreSQL container:
```bash
docker compose up -d
```
> The database will be available on `localhost:5432` (Database: `codequest`, username: `postgres`, password: `postgres`).

---

### 2. Start the Backend (Spring Boot)
In a first terminal:
```bash
cd backend
./mvnw spring-boot:run
```
> The REST API will be accessible on **`http://localhost:8081`**. The database tables and seed data are initialized automatically via `schema.sql` and `data.sql`.

---

### 3. Start the Frontend (Angular 19)
In a second terminal:
```bash
cd frontend
npm install
npm start
```
> Open your browser and navigate to **`http://localhost:4200`**. Outgoing API calls to `/api` are automatically proxied to the backend.

---

## Running Tests

### Backend Tests (JUnit 5 & MockMvc)
To run the automated backend test suite:
```bash
cd backend
./mvnw test
```

### Frontend Tests (Karma & Jasmine)
To run unit tests for Angular components and services:
```bash
cd frontend
npm test
```

---

## Maintenance & Conventions

- **Documentation Synchronization Rule**: Any update to the architecture, dependencies, endpoints, database schema, or quest curriculum must be reflected immediately in this `README.md` file.
