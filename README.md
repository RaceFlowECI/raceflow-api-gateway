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
- [Observabilidad](#observabilidad)
- [Logs estructurados](#logs-estructurados)
- [Trazas distribuidas (OpenTelemetry)](#trazas-distribuidas-opentelemetry)

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

### 1. Clonar el repositorio
```bash
git clone https://github.com/RaceFlowECI/raceflow-api-gateway.git
cd raceflow-api-gateway
```

### 2. Compilar
```bash
mvn clean install
```

### 3. Configurar variables de entorno
```bash
cp .env.example .env
```
```env
JWT_SECRET=raceflow-dev-secret-key-for-local-dev-only-32chars
AUTH_SERVICE_URL=http://localhost:8081
ROOM_SERVICE_URL=http://localhost:8082
REALTIME_SERVICE_URL=http://localhost:8083
SESSION_SERVICE_URL=http://localhost:8084
METRICS_SERVICE_URL=http://localhost:8085
```

### 4. Ejecutar
```bash
mvn spring-boot:run
```
> [!TIP]
> El gateway arranca en `http://localhost:8080`.

---

## Tabla de rutas

| Metodo | Ruta publica | Servicio destino | Auth |
|---|---|---|---|
| `POST` | `/auth/register` | Auth Service :8081 | No |
| `POST` | `/auth/login` | Auth Service :8081 | No |
| `GET` | `/auth/me` | Auth Service :8081 | JWT |
| `POST` | `/rooms` | Room Service :8082 | JWT |
| `GET` | `/rooms/{code}` | Room Service :8082 | JWT |
| `POST` | `/sessions` | Session Service :8084 | JWT |
| `POST` | `/sessions/{id}/finish` | Session Service :8084 | JWT |
| `GET` | `/metrics/**` | Metrics Service :8085 | JWT |
| `WS` | `/ws/rooms/{code}` | Realtime Service :8083 | JWT |

---

## Pruebas y calidad
```bash
mvn test
mvn clean test jacoco:report
```

---

## CI/CD

| Campo | Valor |
|---|---|
| Puerto | 8080 |
| Plataforma | _por definir_ |
| Ultima version | ![CI](https://github.com/RaceFlowECI/raceflow-api-gateway/actions/workflows/ci.yml/badge.svg) |

---

## Observabilidad

### Endpoint de métricas
```
GET http://localhost:8080/actuator/prometheus
```
También disponibles: `/actuator/health`, `/actuator/info`, `/actuator/metrics`.

### Métricas de negocio

> Solo expone el endpoint de Prometheus. No tiene métricas de negocio propias; las métricas HTTP de cada microservicio se recolectan a través de sus propios endpoints.


### Verificación local
```bash
# Con el servicio corriendo:
curl -s http://localhost:8080/actuator/prometheus | grep raceflow_
```

> [!NOTE]
> Micrometer convierte puntos a guiones bajos: `raceflow.rooms.created` → `raceflow_rooms_created_total` en Prometheus.

---

## Logs estructurados

Los logs se emiten en formato **JSON (Logstash)** tanto a consola como a archivo, lo que permite ingestión directa por Promtail → Loki.

### Archivos generados
```
logs/<nombre-servicio>.log               ← archivo activo
logs/<nombre-servicio>.2026-07-05.log    ← rotado por fecha (retención 7 días)
```

### Estructura de un log entry
```json
{
  "@timestamp": "2026-07-05T10:00:00.000-05:00",
  "@version":   "1",
  "message":    "User registered successfully",
  "logger_name":"edu.eci.arsw.raceflow.auth.service.AuthService",
  "thread_name":"http-nio-8081-exec-1",
  "level":      "INFO",
  "level_value": 20000
}
```

### Consulta en Loki (LogQL)
```logql
{service="raceflow-auth-service"} | json | level="ERROR"
```

---

## Trazas distribuidas (OpenTelemetry)

Este servicio forma parte del **flujo critico de tiempo real** y tiene el OpenTelemetry Java Agent adjunto al contenedor Docker (sin cambios en el codigo fuente).

### Como funciona

```
[ raceflow-api-gateway ]
  └─ opentelemetry-javaagent.jar (adjunto via -javaagent)
       └─ OTLP gRPC ──► Tempo :4317
                              └─ Grafana (Explore → Tempo)
```

### Variables de entorno requeridas

| Variable | Valor local | Valor Docker |
|---|---|---|
| `OTEL_SERVICE_NAME` | `raceflow-api-gateway` | `raceflow-api-gateway` |
| `OTEL_EXPORTER_OTLP_ENDPOINT` | `http://localhost:4317` | `http://tempo:4317` |
| `OTEL_TRACES_EXPORTER` | `otlp` | `otlp` |
| `OTEL_METRICS_EXPORTER` | `none` | `none` |
| `OTEL_LOGS_EXPORTER` | `none` | `none` |

### Ejecucion local con agente

```bash
# Descargar el agente (una sola vez)
curl -L -o opentelemetry-javaagent.jar \
  https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.3.0/opentelemetry-javaagent.jar

# Ejecutar con tracing
java -javaagent:opentelemetry-javaagent.jar \
  -DOTEL_SERVICE_NAME=raceflow-api-gateway \
  -DOTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317 \
  -DOTEL_TRACES_EXPORTER=otlp \
  -DOTEL_METRICS_EXPORTER=none \
  -DOTEL_LOGS_EXPORTER=none \
  -jar target/*.jar
```

> [!NOTE]
> Con `mvn spring-boot:run` el agente NO se activa. Usa el comando anterior o Docker para trazas reales.
