# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package

# Package stage
FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=build /target/pets-1.0.0-SNAPSHOT.jar pets-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pets-app.jar"]