# RACEFLOW — API Gateway

> [!IMPORTANT]
> Este repositorio contiene el **API Gateway** de RaceFlow: punto de entrada unico del sistema.

> Para informacion general consulta el [perfil de la organizacion](https://github.com/RaceFlowECI).

---

## Tabla de contenido
- [Descripcion general](#descripcion-general)
- [Stack tecnologico](#stack-tecnologico)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Configuracion local](#configuracion-local)
- [Tabla de rutas](#tabla-de-rutas)
- [Pruebas y calidad](#pruebas-y-calidad)
- [CI/CD](#cicd)

---

## Descripcion general

> [!NOTE]
> Punto de entrada unico del sistema. Autentica el JWT antes de enrutar, aplica rate limiting por IP y agrega los headers de correlacion para trazabilidad distribuida.

### Responsabilidades principales

| Responsabilidad | Descripcion |
|---|---|
| **Autenticacion** | Valida el JWT en cada request antes de enrutar al microservicio destino. |
| **Enrutamiento** | Redirige cada ruta al microservicio correspondiente via Spring Cloud Gateway. |
| **Rate limiting** | Limita peticiones por IP para proteger los servicios internos. |
| **CORS** | Centraliza la politica de CORS para todos los servicios. |

---

## Stack tecnologico

### Backend
![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring WebFlux](https://img.shields.io/badge/Spring_WebFlux-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Cloud Gateway](https://img.shields.io/badge/Spring_Cloud_Gateway-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

### Testing y calidad
![JUnit](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=java&logoColor=white)
![JaCoCo](https://img.shields.io/badge/JaCoCo-BB0A30?style=for-the-badge)
![SonarQube](https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white)

### DevOps
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

---

## Estructura del proyecto

```text
raceflow-api-gateway/
├── .github/workflows/
├── .env.example
├── .gitignore
├── Dockerfile
├── pom.xml
└── src/main/java/edu/eci/arsw/raceflow/gateway/
    ├── GatewayApplication.java
    ├── config/
    │   ├── GatewayConfig.java
    │   ├── SecurityConfig.java
    │   └── CorsConfig.java
    ├── filter/
    │   ├── JwtAuthFilter.java
    │   └── CorrelationIdFilter.java
    └── resources/
        └── application.yml
```

---

## Configuracion local

---

## Tabla de rutas

---

## Pruebas y calidad

---

## CI/CD

