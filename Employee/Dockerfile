# -------------------------
# Stage 1: Build the JAR
# -------------------------
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# -------------------------
# Stage 2: Run the JAR
# -------------------------
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
WORKDIR /app

# Copy the built jar from stage 1
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
