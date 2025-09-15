# Stage 1: Build the Spring Boot app
FROM amazoncorretto:17 AS build
WORKDIR /app

# Copy Maven wrapper & pom first (for dependency caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source and build JAR
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app
FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
