# Code Quest — Frontend Application

> **Note pour les recruteurs (FR) :** *Application Web Single Page (SPA) moderne conçue avec Angular 19, TypeScript 5.7, composants Standalone, gestion d'état réactive par Angular Signals, intégration de Monaco Editor et thème Dark Fantasy avec Angular Material.*

---

## Overview

The `frontend` module is the user-facing web application for Code Quest. It provides:
- A gamified RPG player experience (interactive dashboard, level progression bar, character sheet, quest log).
- An embedded code editor powered by **Monaco Editor** with dynamic language syntax highlighting and automatic local draft saving.
- Secure routing with functional route guards (`authGuard`) and automatic JWT Bearer token injection via `authInterceptor`.
- Modern, clean state management leveraging **Angular Signals** (`signal()`, `computed()`).

---

## Technical Stack & Versions

- **Framework**: Angular 19 (`@angular/core: ^19.2.0`, `@angular/cli: ^19.2.27`)
- **Language**: TypeScript ~5.7.2
- **UI Components**: Angular Material & CDK `^19.2.19` (Azure Blue RPG Theme)
- **State & Reactivity**: Angular Signals & RxJS ~7.8.0
- **Code Editor**: Monaco Editor (asynchronously loaded via AMD loader)
- **Unit Testing**: Jasmine 5.6 & Karma 6.4 (Headless runner)

---

## Project Structure (`src/app`)

```text
src/app/
├── app.component.ts            # Root component with navigation bar & router outlet
├── app.component.html
├── app.component.css
├── app.component.spec.ts       # Root component unit tests
├── app.config.ts               # ApplicationConfig (provideRouter, provideHttpClient, interceptors)
├── app.routes.ts               # Route definitions and auth guards
├── components/                 # Standalone UI Components
│   ├── login/                  # User login form
│   ├── register/               # New player registration form
│   ├── profile/                # RPG Character Sheet & statistics dashboard
│   ├── world-list/             # Available realms & active quest recommendation
│   ├── world-detail/           # Realm quest journal and sub-region breakdown
│   ├── quest-play/             # Monaco code playground & submission console
│   └── not-found/              # 404 Error page
├── models/                     # Strongly-typed TypeScript interfaces
│   ├── auth.model.ts           # LoginRequest, SignupRequest, JwtResponse
│   └── world.model.ts          # World, Quest, SubmissionResponse
└── services/                   # Injectable root services and functional guards
    ├── auth.service.ts         # User session & authentication state signals
    ├── auth.guard.ts           # Functional route guard
    ├── auth.interceptor.ts     # Functional HTTP interceptor (Bearer JWT)
    ├── world.service.ts        # REST API client for realms and quests
    └── user-progress.service.ts # XP, level thresholds, and completed quest tracking
```

---

## Development & Build Commands

All commands should be executed from the `frontend/` directory:

```bash
# Install dependencies
npm install

# Start local development server (port 4200 with API proxy)
npm start

# Build for production (output in dist/frontend)
npm run build

# Run unit tests with Karma / Jasmine
npm test
```
