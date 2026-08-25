# Code Quest — Backend Service

> **Note pour les recruteurs (FR) :** *Module Backend REST développé avec Spring Boot 4.1.0 et Java 21, intégrant Spring Security (JWT), Spring Data JPA / PostgreSQL 16 et un environnement de compilation sécurisé en sandbox.*

---

## Overview

The `backend` module provides the RESTful API for Code Quest. It handles:
- User authentication and registration with stateless JWT tokens and BCrypt password encryption.
- Domain logic for realms, quests, quizzes, and difficulty tiers.
- Dynamic compilation and evaluation of user code submissions (containerized Docker/Podman sandbox with an in-process `JavaCompiler` fallback).
- Relational persistence with Spring Data JPA and Hibernate on PostgreSQL 16.

---

## Technical Stack & Versions

- **Language**: Java 21
- **Framework**: Spring Boot 4.1.0 (`spring-boot-starter-webmvc`, `spring-boot-starter-data-jpa`, `spring-boot-starter-security`)
- **Security & Tokens**: `io.jsonwebtoken:jjwt-api:0.11.5`
- **Database Driver**: `org.postgresql:postgresql`
- **Build Tool**: Apache Maven 3.9.6 via Wrapper (`./mvnw`)
- **Testing**: JUnit 5, Spring WebMvcTest, Mockito, Spring Security Test

---

## Package Architecture (`com.example.codequest`)

```text
com.example.codequest/
├── CodeQuestApplication.java   # Spring Boot Main Entry Point
├── controllers/                 # REST Controllers (@RestController)
│   ├── AuthController.java     # /api/auth (signin, signup)
│   └── WorldController.java    # /api/worlds, /api/quests
├── models/                      # JPA Entities (@Entity)
│   ├── World.java              # Realms / Worlds
│   ├── Quest.java              # Coding challenges
│   ├── User.java               # User accounts
│   └── Role.java               # Roles (ROLE_USER, ROLE_ADMIN)
├── repositories/                # Spring Data JPA Repositories
│   ├── WorldRepository.java
│   ├── QuestRepository.java
│   ├── UserRepository.java
│   └── RoleRepository.java
├── services/                    # Business Service Layer (@Service)
│   ├── AuthService.java        # Authentication and registration logic
│   ├── WorldService.java        # Realm data retrieval
│   ├── QuestService.java        # Quest retrieval and submission handling
│   └── CodeCompilerService.java # Dynamic sandbox compilation & execution
├── payload/                     # Request and Response DTOs (Java Records)
│   ├── request/                # LoginRequest, SignupRequest, SubmissionDTO
│   └── response/               # JwtResponse, MessageResponse, SubmissionResponse
├── security/                    # Security Configuration & JWT Filters
│   ├── WebSecurityConfig.java  # CORS, CSRF, SecurityFilterChain
│   ├── jwt/                    # AuthTokenFilter, JwtUtils, AuthEntryPointJwt
│   └── services/               # UserDetailsImpl, UserDetailsServiceImpl
└── exceptions/                  # Global Exception Handling
    ├── GlobalExceptionHandler.java # @ControllerAdvice
    ├── ErrorMessage.java       # Standardized error response record
    └── *NotFoundException.java # Domain-specific exceptions
```

---

## Available Commands

All commands should be executed from the `backend/` directory:

```bash
# Compile the project
./mvnw clean compile

# Run the Spring Boot application (port 8081)
./mvnw spring-boot:run

# Run automated tests (JUnit 5 / MockMvc)
./mvnw test

# Package into an executable JAR
./mvnw clean package
```
