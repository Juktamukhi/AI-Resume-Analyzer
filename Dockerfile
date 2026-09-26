# Step 1: Build stage using official Maven image
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and source code from the inner folder
COPY resumeanalyzer/pom.xml ./pom.xml
COPY resumeanalyzer/src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Step 2: Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/resumeanalyzer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]