# ARSW-LAB4 - REST API Blueprints

Laboratorio de Arquitecturas de Software - API REST para gestión de blueprints con Spring Boot 3.3.x y Java 21.

## Requisitos

- Java 21
- Maven 3.9+
- PostgreSQL 16+ (o Docker)

## Arquitectura del Proyecto

```
src/main/java/edu/eci/arsw/blueprints
  ├── model/              # Entidades: Blueprint, Point, ApiResponse
  ├── persistence/        # Interfaces y repositorios (InMemory, PostgreSQL)
  ├── services/           # Lógica de negocio
  ├── filters/            # Filtros: Identity, Redundancy, Undersampling
  ├── controllers/        # REST Controllers
  └── config/             # Configuración OpenAPI/Swagger
```

## Configuración de PostgreSQL

### Opción 1: Docker (Recomendado)

```bash
docker-compose up -d
```

### Opción 2: PostgreSQL Local

Crear la base de datos manualmente:

```sql
CREATE DATABASE blueprintsdb;
```

Configurar credenciales en `application.properties` si son diferentes.

## Ejecución del Proyecto

### Con PostgreSQL (por defecto)

```bash
mvn clean install
mvn spring-boot:run
```

### Con persistencia en memoria

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=inmemory
```

### Con filtros activados

Filtro de redundancia:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=redundancy
```

Filtro de undersampling:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=undersampling
```

## Endpoints de la API

Base URL: `http://localhost:8080/api/v1/blueprints`

### GET /api/v1/blueprints
Obtiene todos los blueprints.

**Respuesta exitosa (200):**
```json
{
  "code": 200,
  "message": "execute ok",
  "data": [
    {
      "author": "john",
      "name": "house",
      "points": [{"x": 0, "y": 0}, {"x": 10, "y": 10}]
    }
  ]
}
```

### GET /api/v1/blueprints/{author}
Obtiene todos los blueprints de un autor.

**Ejemplo:**
```bash
curl http://localhost:8080/api/v1/blueprints/john
```

### GET /api/v1/blueprints/{author}/{name}
Obtiene un blueprint específico.

**Ejemplo:**
```bash
curl http://localhost:8080/api/v1/blueprints/john/house
```

### POST /api/v1/blueprints
Crea un nuevo blueprint.

**Ejemplo:**
```bash
curl -X POST http://localhost:8080/api/v1/blueprints \
  -H 'Content-Type: application/json' \
  -d '{
    "author": "john",
    "name": "kitchen",
    "points": [{"x": 1, "y": 1}, {"x": 2, "y": 2}]
  }'
```

**Respuesta exitosa (201):**
```json
{
  "code": 201,
  "message": "created successfully",
  "data": {
    "author": "john",
    "name": "kitchen",
    "points": [{"x": 1, "y": 1}, {"x": 2, "y": 2}]
  }
}
```

### PUT /api/v1/blueprints/{author}/{name}/points
Agrega un punto a un blueprint existente.

**Ejemplo:**
```bash
curl -X PUT http://localhost:8080/api/v1/blueprints/john/kitchen/points \
  -H 'Content-Type: application/json' \
  -d '{"x": 3, "y": 3}'
```

**Respuesta exitosa (202):**
```json
{
  "code": 202,
  "message": "accepted",
  "data": "Point added successfully"
}
```

## Códigos HTTP Utilizados

- **200 OK**: Consultas exitosas
- **201 Created**: Recurso creado exitosamente
- **202 Accepted**: Actualización aceptada
- **400 Bad Request**: Datos inválidos o blueprint duplicado
- **404 Not Found**: Recurso no encontrado

## Documentación OpenAPI/Swagger

Una vez iniciada la aplicación, acceder a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## Filtros de Blueprints

### IdentityFilter (por defecto)
Retorna el blueprint sin modificaciones.

### RedundancyFilter
Elimina puntos consecutivos duplicados.

**Activación:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=redundancy
```

**Ejemplo:**
- Entrada: `[(0,0), (0,0), (1,1), (1,1), (2,2)]`
- Salida: `[(0,0), (1,1), (2,2)]`

### UndersamplingFilter
Conserva 1 de cada 2 puntos (índices pares).

**Activación:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=undersampling
```

**Ejemplo:**
- Entrada: `[(0,0), (1,1), (2,2), (3,3), (4,4)]`
- Salida: `[(0,0), (2,2), (4,4)]`

## Pruebas

Ejecutar todas las pruebas:

```bash
mvn test
```

Las pruebas incluyen:
- Pruebas unitarias de servicios
- Pruebas de filtros
- Prueba de smoke test de la aplicación

## Implementaciones Realizadas

### 1. Clase ApiResponse Genérica
Respuesta uniforme para todos los endpoints con estructura:
```java
public record ApiResponse<T>(int code, String message, T data)
```

### 2. Persistencia en PostgreSQL
- Implementación de `PostgresBlueprintPersistence` usando JPA
- Repositorio `BlueprintRepository` con Spring Data JPA
- Anotaciones JPA en entidades `Blueprint` y `Point`

### 3. Versionamiento de API
- Path base cambiado a `/api/v1/blueprints`
- Preparado para futuras versiones

### 4. Códigos HTTP Correctos
- Uso apropiado de códigos de estado HTTP
- Manejo de errores con respuestas consistentes

### 5. Documentación OpenAPI
- Configuración de Swagger UI
- Anotaciones `@Operation` y `@ApiResponse` en endpoints
- Descripción completa de la API

### 6. Filtros de Blueprints
- `RedundancyFilter`: elimina duplicados consecutivos
- `UndersamplingFilter`: reduce densidad de puntos
- Activación mediante perfiles de Spring

### 7. Pruebas Unitarias
- Pruebas de servicios con Mockito
- Pruebas de filtros
- Cobertura básica de funcionalidad

## Buenas Prácticas Aplicadas

1. **Separación de capas**: Modelo, Persistencia, Servicios, Controladores
2. **Inyección de dependencias**: Constructor injection en todos los componentes
3. **Uso de Records**: Para DTOs y respuestas inmutables
4. **Perfiles de Spring**: Configuración flexible según entorno
5. **Manejo de excepciones**: Respuestas consistentes para errores
6. **Documentación automática**: OpenAPI/Swagger integrado
7. **Versionamiento de API**: Path `/api/v1/` para evolución futura

## Configuración Adicional

### Cambiar puerto del servidor

En `application.properties`:
```properties
server.port=8081
```

### Configurar pool de conexiones

```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

## Autor

Escuela Colombiana de Ingeniería - Arquitecturas de Software
