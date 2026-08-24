# Build Stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build

COPY pom.xml .
COPY src ./src

# Compilation du JAR sans exécuter les tests nécessitant une base active
RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Création d'un utilisateur non-root pour la sécurité
RUN addgroup -S codequest && adduser -S codequest -G codequest

COPY --from=builder /build/target/*.jar app.jar

USER codequest

# Optimisation de la mémoire JVM pour l'offre gratuite Render (512 Mo de RAM)
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Xss256k"
ENV PORT=8081

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
