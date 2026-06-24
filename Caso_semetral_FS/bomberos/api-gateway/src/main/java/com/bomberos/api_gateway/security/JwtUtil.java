package com.bomberos.api_gateway.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    // Se utiliza la misma clave exacta del microservicio de usuarios
    private final String SECRET_KEY = "FirmaSecretaSuperLargaYSeguraParaElProyectoBomberosValleDelSol2026";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public void validarToken(final String token) {
        // Si el token es inválido, ha sido alterado o expiró, esto lanzará una excepción
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}