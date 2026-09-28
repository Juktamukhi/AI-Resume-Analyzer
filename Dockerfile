# Step 1: Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy all files into the container
COPY . .

# Run maven pointing directly to the inner project folder directory
RUN mvn -f resumeanalyzer/pom.xml clean package -DskipTests

# Step 2: Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built jar file from the target directory inside resumeanalyzer
COPY --from=build /app/resumeanalyzer/target/resumeanalyzer-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
