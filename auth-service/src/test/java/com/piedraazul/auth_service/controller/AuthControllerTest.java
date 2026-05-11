package com.piedraazul.auth_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piedraazul.auth_service.dto.LoginRequestDTO;
import com.piedraazul.auth_service.dto.LoginResponseDTO;
import com.piedraazul.auth_service.dto.RegisterUserDTO;
import com.piedraazul.auth_service.dto.UpdateUserDTO;
import com.piedraazul.auth_service.enums.RoleUserEnum;
import com.piedraazul.auth_service.enums.StatusUserEnum;
import com.piedraazul.auth_service.model.User;
import com.piedraazul.auth_service.security.SecurityConfig;
import com.piedraazul.auth_service.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
@DisplayName("AuthController - Pruebas de Capa Web")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private User usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new User();
        usuarioMock.setCodUser(1L);
        usuarioMock.setCedUser(123456789L);
        usuarioMock.setNameUser("Juan");
        usuarioMock.setLastNameUser("Pérez");
        usuarioMock.setRoleUser(RoleUserEnum.Professional);
        usuarioMock.setStatusUser(StatusUserEnum.Active);
        usuarioMock.setSecurityQuestion("¿Pregunta?");
        usuarioMock.setSecurityAnswer("Respuesta");
    }

    // POST /auth/login

    @Test
    @WithMockUser
    @DisplayName("POST /auth/login - 200 OK con credenciales correctas")
    void login_exitoso() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setCedUser(123456789L);
        request.setPassword("pass123");

        LoginResponseDTO response = new LoginResponseDTO("jwt.token", "Professional", 1L, 123456789L, "Juan");
        when(authService.login(any(LoginRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt.token"))
                .andExpect(jsonPath("$.role").value("Professional"))
                .andExpect(jsonPath("$.nameUser").value("Juan"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /auth/login - 401 UNAUTHORIZED si credenciales incorrectas")
    void login_credencialesIncorrectas() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setCedUser(123456789L);
        request.setPassword("wrong");

        when(authService.login(any())).thenThrow(new RuntimeException("Contraseña incorrecta"));

        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /auth/login - 400 BAD REQUEST si body tiene campos nulos")
    void login_camposNulos() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // POST /auth/register

    @Test
    @WithMockUser
    @DisplayName("POST /auth/register - 201 CREATED con datos válidos")
    void register_exitoso() throws Exception {
        RegisterUserDTO dto = buildRegisterDTO(111111L);
        when(authService.register(any(RegisterUserDTO.class))).thenReturn(usuarioMock);

        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codUser").value(1));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /auth/register - 409 CONFLICT si cédula ya existe")
    void register_conflicto() throws Exception {
        RegisterUserDTO dto = buildRegisterDTO(123456789L);
        when(authService.register(any())).thenThrow(new RuntimeException("Ya existe un usuario con esa cédula"));

        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /auth/register - 400 BAD REQUEST si faltan campos obligatorios")
    void register_datosInvalidos() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cedUser\": 111111}"))
                .andExpect(status().isBadRequest());
    }

    // GET /auth/users?role=

    @Test
    @WithMockUser
    @DisplayName("GET /auth/users?role=Professional - 200 OK con lista de usuarios")
    void findByRole_conResultados() throws Exception {
        when(authService.findByRole(RoleUserEnum.Professional)).thenReturn(List.of(usuarioMock));

        mockMvc.perform(get("/auth/users").param("role", "Professional"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nameUser").value("Juan"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /auth/users?role=Admin - 204 NO CONTENT si lista vacía")
    void findByRole_sinResultados() throws Exception {
        when(authService.findByRole(RoleUserEnum.Admin)).thenReturn(List.of());

        mockMvc.perform(get("/auth/users").param("role", "Admin"))
                .andExpect(status().isNoContent());
    }

    // GET /auth/users/{cedula}

    @Test
    @WithMockUser
    @DisplayName("GET /auth/users/{cedula} - 200 OK cuando usuario existe")
    void findByCedula_encontrado() throws Exception {
        when(authService.findByCedula(123456789L)).thenReturn(Optional.of(usuarioMock));

        mockMvc.perform(get("/auth/users/123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cedUser").value(123456789));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /auth/users/{cedula} - 404 NOT FOUND cuando no existe")
    void findByCedula_noEncontrado() throws Exception {
        when(authService.findByCedula(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/auth/users/999"))
                .andExpect(status().isNotFound());
    }

    // PUT /auth/users/{id}

    @Test
    @WithMockUser
    @DisplayName("PUT /auth/users/{id} - 200 OK con datos válidos")
    void update_exitoso() throws Exception {
        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setNameUser("JuanActualizado");
        when(authService.update(eq(1L), any(UpdateUserDTO.class))).thenReturn(usuarioMock);

        mockMvc.perform(put("/auth/users/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("PUT /auth/users/{id} - 404 NOT FOUND si usuario no existe")
    void update_usuarioNoExiste() throws Exception {
        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setNameUser("Fantasma");
        when(authService.update(eq(99L), any())).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(put("/auth/users/99")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    // DELETE /auth/users/{id}

    @Test
    @WithMockUser
    @DisplayName("DELETE /auth/users/{id} - 204 NO CONTENT al desactivar")
    void deactivate_exitoso() throws Exception {
        doNothing().when(authService).deactivate(1L);

        mockMvc.perform(delete("/auth/users/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("DELETE /auth/users/{id} - 404 NOT FOUND si usuario no existe")
    void deactivate_noExiste() throws Exception {
        doThrow(new RuntimeException("Usuario no encontrado")).when(authService).deactivate(99L);

        mockMvc.perform(delete("/auth/users/99").with(csrf()))
                .andExpect(status().isNotFound());
    }

    // Helper

    private RegisterUserDTO buildRegisterDTO(Long ced) {
        RegisterUserDTO dto = new RegisterUserDTO();
        dto.setCedUser(ced);
        dto.setPassUser("pass123");
        dto.setNameUser("Juan");
        dto.setLastNameUser("Pérez");
        dto.setRoleUser(RoleUserEnum.Professional);
        dto.setSecurityQuestion("¿Pregunta?");
        dto.setSecurityAnswer("Respuesta");
        return dto;
    }
}