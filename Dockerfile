# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Package stage
FROM openjdk:21
WORKDIR /app
COPY --from=build /target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT["java", "-jar", "app.jar"]
