# Laboratorio 1 · Sistema Bancario de Transferencias

[![Backend CI](https://github.com/spalacioc05/lab12026p/actions/workflows/build.yml/badge.svg)](https://github.com/spalacioc05/lab12026p/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=spalacioc05_lab12026p&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=spalacioc05_lab12026p)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=spalacioc05_lab12026p&metric=bugs)](https://sonarcloud.io/summary/new_code?id=spalacioc05_lab12026p)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=spalacioc05_lab12026p&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=spalacioc05_lab12026p)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=spalacioc05_lab12026p&metric=coverage)](https://sonarcloud.io/summary/new_code?id=spalacioc05_lab12026p)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=spalacioc05_lab12026p&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=spalacioc05_lab12026p)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=spalacioc05_lab12026p&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=spalacioc05_lab12026p)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=spalacioc05_lab12026p&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=spalacioc05_lab12026p)

<div align="center">

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.11-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supabase-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Wrapper-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![React](https://img.shields.io/badge/React-19-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Vite](https://img.shields.io/badge/Vite-7-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-BD1E1E?style=for-the-badge)

</div>

## Descripción

Este proyecto implementa un sistema bancario básico para el curso Arquitectura de Software. El sistema permite registrar clientes, consultar cuentas, ejecutar transferencias y revisar el historial de movimientos. La solución está separada en dos módulos:

- `backend`: API REST construida con Spring Boot.
- `frontend`: aplicación web construida con React y Vite.

Además de la implementación funcional del laboratorio 1, el proyecto ya incorpora parte del enfoque del laboratorio 2: pruebas automáticas con JUnit 5, validación de cobertura con JaCoCo y pipeline de integración continua con GitHub Actions.

## Logros del proyecto

- Backend y frontend integrados para operar un flujo bancario completo.
- Reglas de negocio centralizadas en servicios y manejo global de errores.
- Pruebas automáticas para servicios, controladores y endpoints auxiliares.
- Cobertura validada con JaCoCo por encima del umbral mínimo configurado.
- Análisis continuo con SonarCloud para calidad, cobertura y duplicación.
- Construcción y publicación automática de imagen Docker del backend en Docker Hub desde GitHub Actions.

## Información académica

| Elemento | Detalle |
| --- | --- |
| Curso | Arquitectura de Software |
| Institución | Universidad de Antioquia |
| Profesor | Diego José Luis Botia Valderrama |
| Integrantes | Santiago Palacio Cárdenas, Sarai Restrepo Rodríguez, Juan Pablo Herrera Jaramillo, Jimena Muñoz Gómez |

## Arquitectura general

```mermaid
flowchart LR
    A[Frontend React + Vite\nlocalhost:5173] -->|HTTP / JSON| B[Backend Spring Boot\nlocalhost:8080]
    B -->|JPA / Hibernate| C[(PostgreSQL / Supabase)]
    D[Postman] --> B
    E[GitHub Actions] --> B
```

## Stack tecnológico

| Capa | Tecnologías |
| --- | --- |
| Backend | Java 17, Spring Boot, Spring Web, Spring Data JPA, Jakarta Validation, MapStruct, Maven Wrapper |
| Base de datos | PostgreSQL en Supabase |
| Frontend | React 19, Vite, Axios, React Router |
| Pruebas | JUnit 5, Mockito, MockMvc |
| Cobertura | JaCoCo |
| Calidad | SonarCloud |
| CI | GitHub Actions |
| Despliegue | Docker, Vercel-ready para frontend |

## Despliegue del frontend en Vercel

El frontend quedó preparado para desplegarse en Vercel.

Configuración recomendada:

- Root Directory: `frontend`
- Framework Preset: `Vite`
- Build Command: `npm run build`
- Output Directory: `dist`
- Variable requerida: `VITE_API_BASE_URL` con la URL pública del backend

También se agregó `frontend/vercel.json` para soportar correctamente las rutas del `BrowserRouter`.

## Estructura del proyecto

```text
lab12026p/
├── .github/
│   └── workflows/
│       ├── build.yml
│       └── sonar.yml
├── ArquiSoft.postman_collection.json
├── README.md
├── backend/
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/udea/lab12026p/
│       │   ├── config/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── entity/
│       │   ├── exception/
│       │   ├── mapper/
│       │   ├── repository/
│       │   └── service/
│       └── test/java/com/udea/lab12026p/
│           ├── controller/
│           └── service/
└── frontend/
    ├── package.json
    └── src/
```

## Backend

El backend sigue una arquitectura por capas.

- `controller`: expone los endpoints REST.
- `service`: implementa la lógica de negocio.
- `repository`: acceso a datos con Spring Data JPA.
- `entity`: modelo persistente.
- `dto`: objetos de transferencia.
- `mapper`: conversión entidad ↔ DTO.
- `exception`: manejo centralizado de errores.

### Funcionalidades bancarias

- Crear clientes con número de cuenta y saldo inicial.
- Consultar clientes por id o por número de cuenta.
- Transferir dinero entre cuentas.
- Validar que las cuentas existan.
- Validar que origen y destino sean diferentes.
- Validar saldo suficiente antes de transferir.
- Consultar historial de transacciones por cuenta.

### Endpoints principales

| Método | Endpoint | Descripción |
| --- | --- | --- |
| GET | `/api/customers` | Lista todos los clientes |
| GET | `/api/customers/{id}` | Consulta un cliente por id |
| GET | `/api/customers/account/{accountNumber}` | Consulta un cliente por número de cuenta |
| POST | `/api/customers` | Registra un nuevo cliente |
| POST | `/api/transactions/transfer` | Ejecuta una transferencia |
| GET | `/api/transactions/account/{accountNumber}` | Lista transacciones de una cuenta |

### Endpoints auxiliares con Faker

Se agregó un `DataController` para pruebas, demostraciones y apoyo al laboratorio 2.

| Método | Endpoint | Descripción |
| --- | --- | --- |
| GET | `/api/faker/health` | Health check del backend |
| GET | `/api/faker/version` | Versión actual de la API |
| GET | `/api/faker/customers` | Genera 10 clientes ficticios |
| GET | `/api/faker/transactions` | Genera 10 transacciones ficticias |
| GET | `/api/faker/cards` | Genera 10 tarjetas ficticias |
| GET | `/api/faker/currencies` | Genera 10 monedas ficticias |

## Base de datos

La aplicación ya no está configurada para MySQL local. Actualmente usa PostgreSQL en Supabase mediante pooler.

Configuración actual:

- Motor: PostgreSQL
- Proveedor: Supabase
- Conexión: pooler IPv4
- Persistencia: Hibernate con `spring.jpa.hibernate.ddl-auto=update`

Tablas principales:

| Tabla | Propósito |
| --- | --- |
| `customers` | Guarda clientes y saldo actual |
| `transactions` | Guarda transferencias entre cuentas |

## Ejecución local

### Prerrequisitos

- JDK 17
- Node.js y npm
- Acceso a la base de datos configurada en Supabase

### Backend

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Disponible en:

```text
http://localhost:8080
```

### Frontend

```powershell
cd frontend
npm install
npm run dev
```

Disponible en:

```text
http://localhost:5173
```

## Pruebas automáticas

El proyecto ya incluye pruebas automatizadas alineadas con el enfoque del laboratorio 2.

### Qué se prueba

- Lógica de negocio en servicios con JUnit 5 y Mockito.
- Validaciones y respuestas HTTP en controladores con MockMvc.
- Endpoints del `DataController`.
- Reglas de error como cuenta inexistente, saldo insuficiente o payload inválido.

### Ejecutar pruebas

```powershell
cd backend
.\mvnw.cmd test
```

### Ejecutar pruebas con coverage

```powershell
cd backend
.\mvnw.cmd verify
```

## Cobertura con JaCoCo

JaCoCo ya está integrado en [backend/pom.xml](backend/pom.xml) y ejecuta lo siguiente:

- instrumentación de pruebas
- generación de reporte de coverage
- validación de umbral mínimo de cobertura

Estado actual medido sobre las clases relevantes incluidas en la regla:

- cobertura de líneas: 93.2%
- umbral mínimo configurado: 90%

Reporte local generado en:

- [backend/target/site/jacoco/index.html](backend/target/site/jacoco/index.html)

## Integración continua

El proyecto ya tiene workflow de GitHub Actions en [\.github/workflows/build.yml](.github/workflows/build.yml).

Este workflow hace lo siguiente:

- checkout del repositorio
- configuración de Java 17
- caché de dependencias Maven
- ejecución de `mvn verify`
- generación del JAR del backend
- validación de construcción de imagen Docker
- publicación de la imagen `spalacioc05/lab12026p:latest` en Docker Hub en eventos `push`
- publicación de artefactos de JaCoCo y Surefire

## SonarCloud

El proyecto ya tiene integración con SonarCloud mediante [\.github/workflows/sonar.yml](.github/workflows/sonar.yml).

Configuración usada:

- organización: `spalacioc05`
- project key: `spalacioc05_lab12026p`
- secret requerido en GitHub: `SONAR_TOKEN`
- reporte de coverage consumido por Sonar: `backend/target/site/jacoco/jacoco.xml`
- exclusiones de Sonar alineadas con JaCoCo para evitar que DTO, entidades, repositorios, mapeadores, configuración y excepciones distorsionen la métrica de cobertura

Las insignias del README ya quedaron enlazadas a este análisis.

## Docker y despliegue

El backend ya tiene contenedor definido en [backend/Dockerfile](backend/Dockerfile).

Imagen publicada por el pipeline:

- `docker.io/spalacioc05/lab12026p:latest`

### Construcción local de imagen

```powershell
cd backend
docker build -t lab12026p-backend .
```

### Ejecución local del contenedor

```powershell
docker run -p 8080:8080 `
    -e SPRING_DATASOURCE_URL="jdbc:postgresql://TU_HOST:5432/postgres" `
    -e SPRING_DATASOURCE_USERNAME="TU_USUARIO" `
    -e SPRING_DATASOURCE_PASSWORD="TU_PASSWORD" `
    lab12026p-backend
```

Variables de entorno soportadas por el backend:

- `SERVER_PORT`
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

## Postman

El repositorio incluye la colección [ArquiSoft.postman_collection.json](ArquiSoft.postman_collection.json), útil para probar manualmente los endpoints bancarios y los auxiliares con Faker.

## Estado frente a laboratorio 2

Ya quedó implementado en este proyecto:

- pruebas unitarias con JUnit 5
- mocking con Mockito
- pruebas web con MockMvc
- coverage con JaCoCo
- workflow de GitHub Actions
- integración con SonarCloud
- empaquetado JAR del backend
- Dockerfile para despliegue

Pendiente para completar más del laboratorio 2:

- integración con Snyk
- publicación más formal de métricas de calidad en el pipeline

## Recomendaciones

- No dejar credenciales reales de base de datos en texto plano si el repositorio será público.
- Mover credenciales a secrets o variables de entorno cuando se complete la integración CI/CD.
- Mantener `mvn verify` como comando base de validación antes de hacer push.
- DBeaver puede ser útil para inspeccionar tablas, saldos y registros de transacciones durante las pruebas.
