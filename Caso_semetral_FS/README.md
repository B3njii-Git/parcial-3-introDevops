# Sistema de Gestión de Emergencias Municipales (Backend)

Este proyecto es una plataforma basada en una **Arquitectura de Microservicios** diseñada para gestionar de manera eficiente las emergencias a nivel municipal (bomberos, rescates y operaciones).

El sistema está completamente contenerizado, garantizando un despliegue ágil y estandarizado en cualquier entorno de desarrollo o producción.

## Arquitectura del Sistema

El proyecto se compone de 5 microservicios de negocio y 2 servicios de orquestación central:

1. **API Gateway (`ms-gateway`):** Único punto de entrada al sistema (Puerto 9090). Enruta dinámicamente las peticiones y valida los JWT.
2. **Eureka Server (`ms-eureka`):** Servidor de descubrimiento (Service Discovery). Registra y localiza dinámicamente cada microservicio activo.
3. **Microservicio de Alertas:** Gestión y emisión de alertas de emergencia (Consumer de RabbitMQ).
4. **Microservicio de Monitoreo:** Supervisión en tiempo real de los recursos y eventos.
5. **Microservicio de Reportes:** Generación de denuncias ciudadanas y reportes de incidentes (Producer de RabbitMQ).
6. **Microservicio de Usuarios:** Gestión de personal, bomberos y roles de acceso (Implementa Caché en Redis).
7. **Microservicio de Historial:** Auditoría y registro histórico inmutable de las emergencias atendidas (Consumer de RabbitMQ).

## Stack Tecnológico Actualizado

* **Backend:** Java 21, Spring Boot 3.x
* **Seguridad:** JWT (JSON Web Tokens) y Spring Security
* **Orquestación y Red:** Spring Cloud (Netflix Eureka, Spring Cloud Gateway)
* **Base de Datos:** PostgreSQL (Supabase)
* **Caché en Memoria:** Redis
* **Mensajería Asíncrona:** RabbitMQ
* **Infraestructura:** Docker, Docker Compose (Multi-stage builds)
* **Gestión de Dependencias:** Maven
* **Testing y Calidad:** JaCoCo para la cobertura de código y JUnit
* **Documentación:** Swagger / OpenAPI y JavaDocs

---

## Guía de Despliegue (Local)

Gracias a la Infraestructura como Código (IaC), levantar el sistema completo requiere un solo comando. No es necesario instalar Java ni Maven en la máquina anfitriona, ya que la compilación se realiza internamente en Docker.

### Requisitos Previos
* [Git](https://git-scm.com/)
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Asegurarse de tener habilitado WSL 2 en Windows).

### Pasos de Instalación

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/lobyte2/Caso_semetral_FS.git
   cd Caso_semetral_FS/bomberos
   ```

2. **Levantar el sistema con Docker Compose:**
   ```bash
   docker compose up -d --build
   ```

3. **Acceder a la aplicación:**
   - **Eureka Server:** [http://localhost:8761/](http://localhost:8761/)
   - **API Gateway:** `http://localhost:9090/`

4. **Detener el sistema:**
   ```bash
   docker compose down
   ```

---

## Nuevas Características

### 1. Mensajería Asíncrona con RabbitMQ
Para evitar cuellos de botella y acoplamiento directo, implementamos **RabbitMQ**. Cuando un ciudadano crea un nuevo reporte en el **MS-Reportes** (Producer), se envía un evento asíncrono a la cola. Los microservicios **MS-Historial** y **MS-Alertas** actúan como Consumers, escuchando estos eventos en tiempo real para guardar el registro inmutable y disparar notificaciones por correo electrónico, respectivamente.

### 2. Optimización de Rendimiento con Redis Cache
Se integró **Redis** en el microservicio de **Usuarios**. Al aplicar anotaciones `@Cacheable` y `@CacheEvict`, los perfiles de usuario frecuentemente consultados se guardan en memoria ultrarrápida. Se configuró estrictamente un **TTL (Time To Live) de 10 minutos** a través de `RedisCacheConfiguration` para evitar que los datos queden obsoletos (Stale Data), reduciendo dramáticamente la carga de lectura en PostgreSQL.

### 3. Manejo Global de Excepciones 
Se estandarizó la respuesta de errores en todos los microservicios. A través del patrón `@RestControllerAdvice` y la creación de un paquete `exception` global, cualquier fallo interno o petición a un recurso inexistente (`ResourceNotFoundException`) es interceptado y convertido en un `ErrorResponse` limpio, coherente y amigable para el Frontend, en lugar de exponer los stacktraces de Spring Boot.

### 4. Swagger / OpenAPI
Cada microservicio cuenta con la dependencia de Springdoc OpenAPI, lo que auto-genera interfaces visuales interactivas para revisar, probar y entender cada uno de los endpoints expuestos de manera gráfica y auto-documentada.

---

## Seguridad, Calidad y Documentación

- **Seguridad (JWT):** Modelo de seguridad robusto que centraliza la validación de tokens en el API Gateway, protegiendo al 100% las rutas de la malla de microservicios y garantizando la autorización por roles.
- **Calidad (JaCoCo):** Todas las clases de lógica de negocio (Services y Controllers) están respaldadas por Pruebas Unitarias con JUnit. JaCoCo analiza el porcentaje de cobertura para mantener un umbral mínimo de 85%-90%.
- **JavaDocs:** Código ampliamente documentado mediante JavaDocs estándar para una fácil mantención por parte del equipo.

---

## Oportunidades de Mejora
Como parte del ciclo de mejora continua abordado durante la EA3, identificamos y solucionamos las siguientes debilidades que presentaba el ecosistema:

1. **Fuerte Acoplamiento en la Comunicación:** Anteriormente, dependíamos en gran medida de llamadas HTTP directas, lo que incrementaba el riesgo de fallos en cascada. **Solución:** Migración a patrones asíncronos orientados a eventos mediante RabbitMQ.
2. **Consultas Redundantes a BD:** El microservicio de usuarios saturaba la base de datos consultando repetidamente los mismos perfiles de usuario. **Solución:** Introducción de Redis Caché con políticas de TTL para aliviar la latencia de red.
3. **Manejo de Errores Inconsistente:** El frontend recibía respuestas HTML genéricas de error (WhiteLabel Error Page) que rompían la aplicación cliente en excepciones no controladas. **Solución:** Estandarización de todas las respuestas de error en un JSON predecible gracias al `GlobalExceptionHandler`.
