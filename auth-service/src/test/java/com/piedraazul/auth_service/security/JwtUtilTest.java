package com.piedraazul.auth_service.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JwtUtil - Pruebas Unitarias")
class JwtUtilTest {

    private JwtUtil jwtUtil;

    // Clave de al menos 256 bits (32 chars) requerida por jjwt para HS256
    private static final String SECRET = "clave-super-secreta-de-pruebas-1234567890!!";
    private static final Long EXPIRATION = 3600000L; // 1 hora

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", SECRET);
        ReflectionTestUtils.setField(jwtUtil, "expiration", EXPIRATION);
    }

    @Test
    @DisplayName("generateToken: genera un token no nulo y no vacío")
    void generateToken_noNulo() {
        String token = jwtUtil.generateToken("123456789", "DOCTOR");

        assertThat(token).isNotNull().isNotBlank();
    }

    @Test
    @DisplayName("extractCedula: extrae correctamente el subject del token")
    void extractCedula_correcta() {
        String token = jwtUtil.generateToken("123456789", "Professional");

        assertThat(jwtUtil.extractCedula(token)).isEqualTo("123456789");
    }

    @Test
    @DisplayName("extractRole: extrae correctamente el rol del token")
    void extractRole_correcto() {
        String token = jwtUtil.generateToken("987654321", "Admin");

        assertThat(jwtUtil.extractRole(token)).isEqualTo("Admin");
    }

    @Test
    @DisplayName("isTokenValid: token recién generado es válido")
    void isTokenValid_tokenValido() {
        String token = jwtUtil.generateToken("111", "Professional");

        assertThat(jwtUtil.isTokenValid(token)).isTrue();
    }

    @Test
    @DisplayName("isTokenValid: token expirado es inválido")
    void isTokenValid_tokenExpirado() throws Exception {
        // Generar token con expiración en el pasado
        ReflectionTestUtils.setField(jwtUtil, "expiration", -1000L);
        String expiredToken = jwtUtil.generateToken("222", "Patient");

        assertThat(jwtUtil.isTokenValid(expiredToken)).isFalse();
    }

    @Test
    @DisplayName("isTokenValid: token manipulado es inválido")
    void isTokenValid_tokenManipulado() {
        String token = jwtUtil.generateToken("333", "Professional");
        String manipulated = token.substring(0, token.length() - 5) + "XXXXX";

        assertThat(jwtUtil.isTokenValid(manipulated)).isFalse();
    }

    @Test
    @DisplayName("isTokenValid: token completamente inválido devuelve false")
    void isTokenValid_tokenBasura() {
        assertThat(jwtUtil.isTokenValid("esto.no.es.un.token")).isFalse();
    }
}