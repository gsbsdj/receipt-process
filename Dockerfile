# Stage 1: Build
FROM maven:3.9.1-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy the Maven configuration
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests && ls -l target

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
