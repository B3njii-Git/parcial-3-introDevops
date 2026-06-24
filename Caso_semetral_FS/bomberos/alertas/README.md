# MS-Alertas

Microservicio responsable de emitir notificaciones de emergencia a la comunidad o a los cuerpos de bomberos cuando ocurre un evento crítico.

## Instrucciones de Uso
1. Requiere conexión a RabbitMQ y un servidor SMTP (ver `application.yml`).
2. Ejecutar con `mvn spring-boot:run` o vía Docker.
3. El puerto por defecto es `8083`.

## Características Clave
- Funciona como un **Consumer** de RabbitMQ escuchando la creación de nuevos reportes para gatillar el envío de correos electrónicos.
