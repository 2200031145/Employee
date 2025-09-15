# Stage 1: Build the Spring Boot app
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS build
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY pom.xml .
RUN chmod +x mvnw

# Copy source and build JAR
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
