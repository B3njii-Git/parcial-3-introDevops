# API Gateway

Componente BFF (Backend For Frontend) y pasarela de enlace entre el cliente y los microservicios de la red interna.

## Instrucciones de Uso
1. Requiere que `eureka-server` esté en ejecución.
2. Ejecutar con `mvn spring-boot:run` o vía Docker.
3. El puerto por defecto es `9090`.

## Características Clave
- Se encarga de la validación del **JWT** para asegurar que todos los endpoints de los microservicios estén 100% protegidos.
- Enruta las peticiones basándose en la configuración definida.
