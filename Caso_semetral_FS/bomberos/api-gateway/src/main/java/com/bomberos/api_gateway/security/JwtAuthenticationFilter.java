package com.bomberos.api_gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    // Se definen las rutas que no requerirán token
    private final List<String> rutasPublicas = List.of(
            "/usuarios/login",
            "/v3/api-docs",
            "/swagger-ui"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // Se permite la ruta exacta /usuarios (para listar o registrar usuario)
        if (path.equals("/usuarios")) {
            return chain.filter(exchange);
        }

        // Comprueba si el path comienza con alguna de las rutas públicas
        boolean esRutaPublica = rutasPublicas.stream().anyMatch(path::startsWith);

        if (!esRutaPublica) {
            // 1. Validar que la petición traiga el header Authorization
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return devolverError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // 2. Validar que comience con "Bearer "
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            } else {
                return devolverError(exchange, HttpStatus.UNAUTHORIZED);
            }

            // 3. Validar el token usando jjwt
            try {
                jwtUtil.validarToken(authHeader);
            } catch (Exception e) {
                // Si expiró o es inválido, retorna un 401
                return devolverError(exchange, HttpStatus.UNAUTHORIZED);
            }
        }

        // Si todo está correcto, permite que la petición continúe hacia los microservicios
        return chain.filter(exchange);
    }

    private Mono<Void> devolverError(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    // Se le da la máxima prioridad para que se ejecute antes de enrutar
    @Override
    public int getOrder() {
        return -1;
    }
}