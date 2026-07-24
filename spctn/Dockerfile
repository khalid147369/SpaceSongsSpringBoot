# ETAPA 1: Compilación
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build

COPY . .

# Desactivamos completamente la compilación y ejecución de tests
RUN mvn clean package -Dmaven.test.skip=true

# ETAPA 2: Ejecución
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]