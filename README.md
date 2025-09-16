# 🎓 CRUD Courses API

Uma API REST desenvolvida em Spring Boot para gerenciamento de cursos de programação, implementada como parte do **Desafio 01** da trilha de Java do Ignite da Rocketseat.

## 📋 Sobre o Projeto

Esta API foi desenvolvida para uma empresa fictícia de cursos de programação, oferecendo funcionalidades completas de CRUD (Create, Read, Update, Delete) para gerenciamento de cursos. O projeto implementa uma arquitetura limpa com separação de responsabilidades, utilizando Spring Boot, JPA/Hibernate e PostgreSQL.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **Spring Web**
- **Spring Validation**
- **PostgreSQL 16**
- **Docker & Docker Compose**
- **Lombok**
- **Swagger/OpenAPI 3**
- **Maven**

## 🏗️ Arquitetura do Projeto

O projeto segue uma arquitetura em camadas bem definida:

```
src/main/java/com/carlosalexandredevv/crud_courses/
├── config/                 # Configurações (Swagger)
├── exceptions/             # Tratamento de exceções
├── modules/course/         # Módulo de cursos
│   ├── controllers/        # Controladores REST
│   ├── DTOs/              # Data Transfer Objects
│   ├── entities/          # Entidades JPA
│   ├── repositories/      # Repositórios de dados
│   └── useCases/          # Casos de uso (regras de negócio)
└── shared/                # Classes compartilhadas
```

## 📊 Estrutura da Entidade Course

A entidade `Course` possui as seguintes propriedades:

- **`id`** - Identificador único (UUID)
- **`name`** - Nome do curso (obrigatório, 3-255 caracteres)
- **`category`** - Categoria do curso (obrigatório, 3-25 caracteres)
- **`active`** - Status ativo/inativo do curso (obrigatório)
- **`createdAt`** - Data de criação (preenchida automaticamente)
- **`updatedAt`** - Data de atualização (atualizada automaticamente)

## 🛠️ Funcionalidades Implementadas

### ✅ CRUD Completo

- ✅ **Criar curso** - `POST /courses`
- ✅ **Listar cursos** - `GET /courses`
- ✅ **Atualizar curso** - `PUT /courses/{id}`
- ✅ **Deletar curso** - `DELETE /courses/{id}`
- ✅ **Toggle status ativo** - `PATCH /courses/{id}/active`

### ✅ Funcionalidades Extras

- ✅ **Filtros de busca** por nome e categoria
- ✅ **Validação de dados** com Bean Validation
- ✅ **Documentação automática** com Swagger/OpenAPI
- ✅ **Tratamento de exceções** personalizado
- ✅ **Testes unitários** abrangentes
- ✅ **Docker Compose** para ambiente de desenvolvimento

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Docker e Docker Compose

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/crud-courses.git
cd crud-courses
```

### 2. Inicie o banco de dados

```bash
docker-compose up -d
```

### 3. Execute a aplicação

```bash
# Usando Maven
./mvnw spring-boot:run

# Ou compilando e executando
./mvnw clean package
java -jar target/crud-courses-0.0.1-SNAPSHOT.jar
```

### 4. Acesse a documentação

- **Swagger UI**: http://localhost:3001/swagger-ui.html
- **API Base URL**: http://localhost:3001

## 📚 Documentação da API

### Endpoints Disponíveis

#### 1. Criar Curso

```http
POST /courses
Content-Type: application/json

{
  "name": "Spring Boot Avançado",
  "category": "Backend",
  "active": true
}
```

#### 2. Listar Cursos

```http
# Listar todos
GET /courses

# Filtrar por nome
GET /courses?name=Spring

# Filtrar por categoria
GET /courses?category=Backend

# Filtrar por ambos
GET /courses?name=Spring&category=Backend
```

#### 3. Atualizar Curso

```http
PUT /courses/{id}
Content-Type: application/json

{
  "name": "Spring Boot ExpIert",
  "category": "Backend"
}
```

#### 4. Deletar Curso

```http
DELETE /courses/{id}
```

#### 5. Toggle Status Ativo

```http
PATCH /courses/{id}/active
```

## 🧪 Testes

O projeto inclui testes unitários abrangentes para todos os casos de uso:

```bash
# Executar todos os testes
./mvnw test

# Executar testes com relatório de cobertura
./mvnw test jacoco:report
```

## 🐳 Docker

### Banco de Dados

```bash
# Iniciar PostgreSQL
docker-compose up -d

# Parar PostgreSQL
docker-compose down

# Remover volumes (cuidado: apaga dados)
docker-compose down -v
```

### Aplicação (Opcional)

```dockerfile
# Dockerfile incluído para containerização da aplicação
docker build -t crud-courses .
docker run -p 3001:3001 crud-courses
```

## 🔧 Configurações

### Banco de Dados

- **Host**: localhost
- **Porta**: 5433
- **Database**: crud_courses
- **Usuário**: crud
- **Senha**: crud

### Aplicação

- **Porta**: 3001
- **Context Path**: /
- **Swagger UI**: /swagger-ui.html

## 📝 Regras de Negócio Implementadas

1. **Validações**:

   - Nome obrigatório (3-255 caracteres)
   - Categoria obrigatória (3-25 caracteres)
   - Status ativo obrigatório

2. **Campos Automáticos**:

   - `id`: Gerado automaticamente (UUID)
   - `createdAt`: Preenchido na criação
   - `updatedAt`: Atualizado a cada modificação

3. **Filtros**:

   - Busca por nome (parcial)
   - Busca por categoria (exata)
   - Combinação de filtros

4. **Atualização**:
   - Apenas `name` e `category` podem ser atualizados via PUT
   - Status `active` é ignorado no PUT (use PATCH)

## 🎯 Melhorias Implementadas

- ✅ **Validação robusta** com Bean Validation
- ✅ **Tratamento de exceções** personalizado
- ✅ **Documentação automática** com Swagger
- ✅ **Testes unitários** completos
- ✅ **Arquitetura limpa** com separação de responsabilidades
- ✅ **Docker Compose** para ambiente de desenvolvimento
- ✅ **Logs estruturados** e informativos
