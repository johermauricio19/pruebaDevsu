# Proyecto de Microservicios Java - Prueba Devsu

Este proyecto implementa dos microservicios en Java que gestionan operaciones CRUD para clientes y cuentas bancarias, comunicándose de forma asíncrona utilizando RabbitMQ. Utiliza arquitectura limpia (Clean Architecture) con capas de dominio, aplicación e infraestructura.

## Arquitectura

### Microservicios

- **clients-service**: Gestiona operaciones CRUD para personas y clientes. Corre en puerto 8081.
- **cuentas-service**: Gestiona operaciones CRUD para cuentas y movimientos bancarios. Corre en puerto 8082.

### Patrón Arquitectónico

- **Clean Architecture**: Separación en capas (Domain, Application, Infrastructure)
- **Ports & Adapters**: Inversión de dependencias con puertos y adaptadores
- **Repository Pattern**: Abstracción de acceso a datos
- **Service Layer**: Lógica de negocio centralizada

## Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** con Hibernate
- **MySQL 8**
- **RabbitMQ** para mensajería asíncrona
- **Maven** para gestión de dependencias
- **SLF4J** para logging
- **OpenAPI/Swagger** para documentación de APIs

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8 (instalado y corriendo)
- RabbitMQ (instalado y corriendo en localhost:5672)

## Configuración de Base de Datos

Crear la base de datos MySQL:

```sql
CREATE DATABASE prueba_devsu;
```

Las tablas se crearán automáticamente con JPA (ddl-auto: update).

## Instalación y Ejecución

1. Clona el repositorio
2. Asegúrate de que MySQL y RabbitMQ estén corriendo
3. Construye el proyecto completo:
   ```bash
   mvn clean install
   ```
4. Ejecuta el microservicio de clients:
   ```bash
   cd clients && mvn spring-boot:run
   ```
5. Ejecuta el microservicio de cuentas (en otra terminal):
   ```bash
   cd cuentas && mvn spring-boot:run
   ```

## APIs REST

### Clients Service (puerto 8081)

#### Personas
- `GET /personas` - Listar todas las personas
- `GET /personas/{id}` - Obtener persona por ID
- `POST /personas` - Crear nueva persona
- `PUT /personas/{id}` - Actualizar persona
- `DELETE /personas/{id}` - Eliminar persona

#### Clientes
- `GET /clientes` - Listar todos los clientes
- `GET /clientes/{id}` - Obtener cliente por ID
- `POST /clientes` - Crear nuevo cliente
- `PUT /clientes/{id}` - Actualizar cliente
- `DELETE /clientes/{id}` - Eliminar cliente

### Cuentas Service (puerto 8082)

#### Cuentas
- `GET /cuentas` - Listar todas las cuentas
- `GET /cuentas/{id}` - Obtener cuenta por ID
- `POST /cuentas` - Crear nueva cuenta
- `PUT /cuentas/{id}` - Actualizar cuenta
- `DELETE /cuentas/{id}` - Eliminar cuenta
- `POST /cuentas/{id}/deposito` - Realizar depósito
- `POST /cuentas/{id}/retiro` - Realizar retiro

#### Movimientos
- `GET /movimientos` - Listar todos los movimientos
- `GET /movimientos/{id}` - Obtener movimiento por ID
- `POST /movimientos` - Crear nuevo movimiento
- `PUT /movimientos/{id}` - Actualizar movimiento
- `DELETE /movimientos/{id}` - Eliminar movimiento

## Documentación API

Ambos microservicios incluyen documentación OpenAPI/Swagger:
- Clients: http://localhost:8081/swagger-ui.html
- Cuentas: http://localhost:8082/swagger-ui.html

## Comunicación Asíncrona

Los microservicios se comunican vía RabbitMQ para eventos como:
- Creación/actualización de clientes -> Notificación a cuentas
- Movimientos en cuentas -> Actualización de saldos

## Logging

Los logs se configuran en nivel DEBUG para desarrollo. Los archivos de log incluyen:
- Operaciones de negocio
- Consultas SQL (opcional)
- Errores y excepciones

## Manejo de Errores

Ambos microservicios incluyen manejo global de excepciones con respuestas estandarizadas:
- 400 Bad Request: Errores de validación
- 404 Not Found: Recursos no encontrados
- 500 Internal Server Error: Errores del servidor

## Pruebas

Para ejecutar las pruebas:
```bash
mvn test
```

## Despliegue con Docker

El proyecto incluye configuración completa para despliegue con Docker usando docker-compose.

### Requisitos Previos

- Docker
- Docker Compose

### Despliegue

1. Construir y ejecutar todos los servicios:
   ```bash
   docker-compose up --build
   ```

2. Los servicios estarán disponibles en:
   - Clientes Service: http://localhost:8081
   - Cuentas Service: http://localhost:8082
   - RabbitMQ Management: http://localhost:15672 (guest/guest)
   - MySQL: localhost:3306

### Servicios Incluidos

- **mysql**: Base de datos MySQL 8.0
- **rabbitmq**: Message broker RabbitMQ con interfaz de gestión
- **clientes-service**: Microservicio de clientes
- **cuentas-service**: Microservicio de cuentas

### Comandos Útiles

```bash
# Ejecutar en segundo plano
docker-compose up -d

# Ver logs
docker-compose logs -f [service-name]

# Detener servicios
docker-compose down

# Limpiar volúmenes
docker-compose down -v
```