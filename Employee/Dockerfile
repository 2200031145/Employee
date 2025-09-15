# -------------------------
# Stage 1: Build the JAR
# -------------------------
FROM public.ecr.aws/docker/library/maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app

# Copy pom.xml first to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the project
COPY src ./src
RUN mvn clean package -DskipTests

# -------------------------
# Stage 2: Run the JAR
# -------------------------
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose app port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
