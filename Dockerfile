FROM maven:3.9-eclipse-temurin-25 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn --batch-mode dependency:go-offline
COPY src ./src
RUN mvn --batch-mode package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /build/target/transacoes-api-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
