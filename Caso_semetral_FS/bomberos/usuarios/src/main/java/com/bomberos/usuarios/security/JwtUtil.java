package com.bomberos.usuarios.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Clave secreta para firmar digitalmente el token
    private final String SECRET_KEY = "FirmaSecretaSuperLargaYSeguraParaElProyectoBomberosValleDelSol2026";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // El token expirará en 10 horas
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    // Creamos la credencial digital con los datos de Autenticación y Autorización
    public String generarToken(String email, String rol) {
        Map<String, Object> claims = new HashMap<>();

        // AUTORIZACIÓN: Guardamos el rol para saber a dónde puede acceder
        claims.put("rol", rol);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // AUTENTICACIÓN: Guardamos quién eres (su email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validarToken(String token) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public String extraerEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String extraerRol(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("rol", String.class);
    }
}