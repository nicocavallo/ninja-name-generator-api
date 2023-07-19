# Stage 1: build the project
FROM hseeberger/scala-sbt:11.0.12_1.5.5_2.13.6 as builder
WORKDIR /app
COPY . .
RUN sbt clean update compile assembly

# Stage 2: create the Docker image
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=builder /app/target/scala-3.3.0/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]