package com.piedraazul.api_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String secret;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/piedraAzul/auth/login",
            "/piedraAzul/auth/register"
    );

    private static final Map<String, List<String>> ROLE_RULES = Map.of(
            "/piedraAzul/auth/users", List.of("Admin"),
            "/piedraAzul/professionals", List.of("Admin", "Professional", "Scheduler", "Patient"),
            "/piedraAzul/patients", List.of("Admin", "Professional", "Patient","Scheduler"),
            "/piedraAzul/appointments", List.of("Admin", "Professional", "Patient","Scheduler"),
            "/piedraAzul/professionals/speciality/", List.of("Patient","Scheduler")
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return respondWith(exchange, HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        Claims claims;
        try {
            claims = validateToken(token);
        } catch (Exception e) {
            return respondWith(exchange, HttpStatus.UNAUTHORIZED);
        }

        String userId = claims.getSubject();
        String userRole = claims.get("role", String.class);

        System.out.println("===== JWT =====");
        System.out.println("User ID: " + userId);
        System.out.println("Role: " + userRole);
        System.out.println("=====================");

        if (!isRoleAllowed(path, userRole)) {
            return respondWith(exchange, HttpStatus.FORBIDDEN);
        }

        final String finalUserId = userId;
        final String finalUserRole = userRole;

        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders mutableHeaders = new HttpHeaders();
                mutableHeaders.putAll(super.getHeaders());
                mutableHeaders.set("X-User-Id", finalUserId);
                mutableHeaders.set("X-User-Role", finalUserRole);
                return mutableHeaders;
            }
        };

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream()
                .anyMatch(path::startsWith);
    }

    private boolean isRoleAllowed(String path, String userRole) {
        return ROLE_RULES.entrySet()
                .stream()
                .filter(entry -> path.startsWith(entry.getKey()))
                .findFirst()
                .map(entry -> entry.getValue().contains(userRole))
                .orElse(true);
    }

    private Claims validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Mono<Void> respondWith(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}