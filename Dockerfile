# Estágio 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia o pom.xml
COPY pom.xml .

# Adicionamos -U para forçar a atualização e ignorar falhas de cache
# E removemos o go-offline se ele continuar dando erro, indo direto para o package
RUN mvn dependency:go-offline -U

COPY src ./src
RUN mvn package -DskipTests -U

# Estágio 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]