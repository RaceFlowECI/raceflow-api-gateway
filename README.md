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

---

## Estructura del proyecto

---

## Configuracion local

---

## Tabla de rutas

---

## Pruebas y calidad

---

## CI/CD

