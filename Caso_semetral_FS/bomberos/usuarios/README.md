# MS-Usuarios

Microservicio encargado de la gestión de usuarios, roles y autenticación.

## Instrucciones de Uso
1. Configurar las variables de entorno para PostgreSQL, Redis y Eureka (ver `application.yml`).
2. Ejecutar con `mvn spring-boot:run` o a través de `docker compose`.
3. El puerto por defecto es `8081`.

## Documentación de Caché con Redis
- **¿Qué datos se cachean?**: Se cachean las peticiones para obtener un usuario por su ID y el listado general de usuarios (si aplica).
- **¿Por qué se cachean?**: La consulta de perfiles de usuario es una operación muy frecuente en el sistema. Al cachear estos datos en Redis (en memoria), reducimos significativamente la latencia y la carga de lectura en la base de datos PostgreSQL, mejorando el rendimiento global de las autenticaciones y validaciones de perfil.
- **TTL (Time To Live)**: La caché tiene un TTL configurado de 10 minutos para asegurar que la información no quede obsoleta. Si un usuario se actualiza o elimina, se usa `@CacheEvict` para limpiar la caché instantáneamente.
