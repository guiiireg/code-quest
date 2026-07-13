# Base de données - CodeQuest

## Diagramme de la base

```mermaid
erDiagram
    USER ||--o| PROFILE : "possède"
    USER ||--o{ USER_QUEST : "tente"
    WORLD ||--o{ QUEST : "contient"
    QUEST ||--o{ USER_QUEST : "est réalisée par"
    
    USER {
        uuid id PK
        string username
        string email
        string password_hash
        string role
        timestamp created_at
    }

    PROFILE {
        uuid id PK
        uuid user_id FK
        int level
        int current_xp
        int total_xp
    }

    WORLD {
        uuid id PK
        string name
        string description
    }

    QUEST {
        uuid id PK
        uuid world_id FK
        string title
        text description
        text starting_code
        int xp_reward
        boolean is_boss
    }

    USER_QUEST {
        uuid id PK
        uuid user_id FK
        uuid quest_id FK
        string status
        timestamp completed_at
    }
```

## Entités

- **User** : Représente un compte utilisateur sur la plateforme avec ses informations de connexion et son identité.
- **Profile** : Contient les informations de progression de l'utilisateur (niveau actuel, points d'expérience en cours et total accumulé).
- **World** : Représente une technologie ou un domaine d'apprentissage spécifique (Java, Spring Boot, Git, Docker, etc.).
- **Quest** : Définition d'un défi ou d'un exercice technique avec son contexte, le code source initial, la récompense en XP et un indicateur si la quête est un boss.
- **UserQuest** : Table de liaison qui stocke l'état d'avancement (en cours, validée, échouée) d'une quête pour un utilisateur donné.

## Relations

- Un utilisateur (**User**) possède un et un seul profil de progression (**Profile**).
- Un monde (**World**) regroupe une ou plusieurs quêtes (**Quest**).
- Une quête (**Quest**) appartient obligatoirement à un seul monde (**World**).
- Un utilisateur (**User**) peut participer à plusieurs quêtes (**Quest**), l'historique étant tracé par **UserQuest**.
- Une quête (**Quest**) peut être tentée et résolue par de multiples utilisateurs (**User**).
