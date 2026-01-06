# Estágio 1: Build
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
# Corrigido: o parâmetro correto é -DskipTests (com 's' no final)
RUN mvn package -DskipTests

# Estágio 2: Runtime (Imagem leve)
# Corrigido: openjdk:17-jre-slim não existe mais, usamos eclipse-temurin
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia o jar gerado no estágio anterior
COPY --from=builder /app/target/cardapio-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
