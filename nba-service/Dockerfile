FROM maven:3.8.6 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/nba-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
