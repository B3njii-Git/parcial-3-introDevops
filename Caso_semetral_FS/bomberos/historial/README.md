# MS-Historial

Microservicio dedicado a almacenar y gestionar el registro inmutable de las incidencias reportadas.

## Instrucciones de Uso
1. Configurar la cadena de conexión de PostgreSQL y la integración con RabbitMQ.
2. Ejecutar con `mvn spring-boot:run` o a través de `docker compose`.
3. El puerto por defecto es `8084`.

## Características Clave
- Se conecta a RabbitMQ como **Consumer** para registrar automáticamente cada reporte de incendio creado en el sistema general.
