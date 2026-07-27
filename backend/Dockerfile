FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copia arquivos do Maven primeiro para aproveitar cache
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Garante permissão de execução do Maven Wrapper
RUN chmod +x mvnw

# Baixa dependências
RUN ./mvnw dependency:go-offline

# Copia código da aplicação
COPY src src

# Gera o jar
RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia somente o artefato gerado para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Usuário sem privilégio
RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ENTRYPOINT ["java", "-jar", "app.jar"]