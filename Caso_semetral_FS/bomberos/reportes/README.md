# MS-Reportes

Microservicio encargado de recibir y gestionar las denuncias y reportes de incendios enviados por la ciudadanía.

## Instrucciones de Uso
1. Configurar las credenciales de BD y de RabbitMQ en `application.yml`.
2. Ejecutar con `mvn spring-boot:run` o mediante `docker compose`.
3. El puerto por defecto es `8080`.

## Características Clave
- Integra un **Producer** de RabbitMQ que emite eventos cuando un nuevo reporte es creado.
