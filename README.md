# 💰 Meu Financeiro

Sistema de gerenciamento financeiro pessoal desenvolvido com Spring Boot, PostgreSQL e documentação de API utilizando Swagger/OpenAPI.

O objetivo do projeto é permitir o controle de receitas, despesas, categorias e organização financeira através de uma API REST.

---

# 🚀 Tecnologias utilizadas

## Backend

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Swagger / OpenAPI
- Docker
- GitHub Actions CI/CD

## Frontend

Planejado:

- Angular
- React

---

# 📂 Estrutura do projeto

```

Meu-Financeiro
│
├── backend
│   └── API Spring Boot
│
├── frontend
│   └── Aplicação web
│
└── docker-compose.yml

```

---

# ⚙️ Executando o backend

## Pré-requisitos

- Java 21+
- Maven
- PostgreSQL
- Docker (opcional)

---

## Configuração do banco

Crie um banco PostgreSQL:

```sql
CREATE DATABASE financeiro;
````

Configure o arquivo:

```
backend/src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/financeiro
spring.datasource.username=postgres
spring.datasource.password=senha
```

---

## Executando

Entre na pasta backend:

```bash
cd backend
```

Execute:

Linux/Mac:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação estará disponível:

```
http://localhost:8080
```

---

# 📚 Documentação da API

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```
http://localhost:8080/v3/api-docs
```

---

# 🐳 Executando com Docker

Build da imagem:

```bash
docker build -t meu-financeiro .
```

Executar:

```bash
docker run -p 8080:8080 meu-financeiro
```

---

# 🧪 Testes

Executar testes:

```bash
./mvnw clean verify
```

O pipeline CI executa automaticamente:

* Build
* Testes automatizados
* Docker build
* Publicação da imagem

---
