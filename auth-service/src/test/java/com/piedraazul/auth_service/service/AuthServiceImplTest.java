package com.piedraazul.auth_service.service;

import com.piedraazul.auth_service.dto.LoginRequestDTO;
import com.piedraazul.auth_service.dto.LoginResponseDTO;
import com.piedraazul.auth_service.dto.RegisterUserDTO;
import com.piedraazul.auth_service.dto.UpdateUserDTO;
import com.piedraazul.auth_service.enums.RoleUserEnum;
import com.piedraazul.auth_service.enums.StatusUserEnum;
import com.piedraazul.auth_service.messaging.UserEventPublisher;
import com.piedraazul.auth_service.model.User;
import com.piedraazul.auth_service.repository.UserRepository;
import com.piedraazul.auth_service.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImpl - Pruebas Unitarias")
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserEventPublisher userEventPublisher;

    @InjectMocks
    private AuthServiceImpl authService;

    private User usuarioActivo;
    private User usuarioInactivo;

    @BeforeEach
    void setUp() {
        usuarioActivo = new User();
        usuarioActivo.setCodUser(1L);
        usuarioActivo.setCedUser(123456789L);
        usuarioActivo.setPassUser("$2a$10$hashedPassword");
        usuarioActivo.setNameUser("Juan");
        usuarioActivo.setLastNameUser("Pérez");
        usuarioActivo.setRoleUser(RoleUserEnum.Professional);
        usuarioActivo.setStatusUser(StatusUserEnum.Active);
        usuarioActivo.setSecurityQuestion("¿Nombre de tu mascota?");
        usuarioActivo.setSecurityAnswer("Firulais");

        usuarioInactivo = new User();
        usuarioInactivo.setCodUser(2L);
        usuarioInactivo.setCedUser(987654321L);
        usuarioInactivo.setPassUser("$2a$10$hashedPassword");
        usuarioInactivo.setNameUser("Ana");
        usuarioInactivo.setLastNameUser("García");
        usuarioInactivo.setRoleUser(RoleUserEnum.Patient);
        usuarioInactivo.setStatusUser(StatusUserEnum.Inactive);
        usuarioInactivo.setSecurityQuestion("¿Ciudad natal?");
        usuarioInactivo.setSecurityAnswer("Bogotá");
    }

    // ─────────────────────────────────────────────
    //  login()
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("login exitoso: retorna token y datos del usuario")
    void login_exitoso() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setCedUser(123456789L);
        request.setPassword("password123");

        when(userRepository.findByCedUser(123456789L)).thenReturn(Optional.of(usuarioActivo));
        when(passwordEncoder.matches("password123", "$2a$10$hashedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("123456789", "Professional")).thenReturn("jwt.token.mock");

        LoginResponseDTO response = authService.login(request);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt.token.mock");
        assertThat(response.getRole()).isEqualTo("Professional");
        assertThat(response.getCodUser()).isEqualTo(1L);
        assertThat(response.getCedUser()).isEqualTo(123456789L);
        assertThat(response.getNameUser()).isEqualTo("Juan");
    }

    @Test
    @DisplayName("login falla: usuario no existe")
    void login_usuarioNoEncontrado() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setCedUser(999L);
        request.setPassword("cualquier");

        when(userRepository.findByCedUser(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Usuario no encontrado");
    }

    @Test
    @DisplayName("login falla: contraseña incorrecta")
    void login_contrasenaIncorrecta() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setCedUser(123456789L);
        request.setPassword("wrongPassword");

        when(userRepository.findByCedUser(123456789L)).thenReturn(Optional.of(usuarioActivo));
        when(passwordEncoder.matches("wrongPassword", "$2a$10$hashedPassword")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Contraseña incorrecta");
    }

    @Test
    @DisplayName("login falla: usuario inactivo")
    void login_usuarioInactivo() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setCedUser(987654321L);
        request.setPassword("password123");

        when(userRepository.findByCedUser(987654321L)).thenReturn(Optional.of(usuarioInactivo));
        when(passwordEncoder.matches("password123", "$2a$10$hashedPassword")).thenReturn(true);

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Usuario inactivo");
    }

    // ─────────────────────────────────────────────
    //  register()
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("register exitoso: guarda usuario y publica evento")
    void register_exitoso() {
        RegisterUserDTO dto = buildRegisterDTO(111111L, "Carlos", "Ruiz", RoleUserEnum.Patient);

        when(userRepository.existsByCedUser(111111L)).thenReturn(false);
        when(passwordEncoder.encode("pass123")).thenReturn("$2a$10$encodedPass");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> {
            User u = inv.getArgument(0);
            u.setCodUser(10L);
            return u;
        });

        User result = authService.register(dto);

        assertThat(result).isNotNull();
        assertThat(result.getCedUser()).isEqualTo(111111L);
        assertThat(result.getNameUser()).isEqualTo("Carlos");
        assertThat(result.getStatusUser()).isEqualTo(StatusUserEnum.Active);
        assertThat(result.getPassUser()).isEqualTo("$2a$10$encodedPass");

        verify(userRepository).save(any(User.class));
        verify(userEventPublisher).publishUserRegistered(any(User.class));
    }

    @Test
    @DisplayName("register falla: cédula ya registrada")
    void register_cedulaDuplicada() {
        RegisterUserDTO dto = buildRegisterDTO(123456789L, "Otro", "Usuario", RoleUserEnum.Professional);

        when(userRepository.existsByCedUser(123456789L)).thenReturn(true);

        assertThatThrownBy(() -> authService.register(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ya existe un usuario con esa cédula");

        verify(userRepository, never()).save(any());
        verify(userEventPublisher, never()).publishUserRegistered(any());
    }

    @Test
    @DisplayName("register: campos opcionales nulos se aceptan")
    void register_camposOpcionalesNulos() {
        RegisterUserDTO dto = new RegisterUserDTO();
        dto.setCedUser(222222L);
        dto.setPassUser("pass");
        dto.setNameUser("Laura");
        dto.setLastNameUser("López");
        dto.setRoleUser(RoleUserEnum.Patient);
        dto.setSecurityQuestion("¿Color favorito?");
        dto.setSecurityAnswer("Azul");
        // secondNameUser y secondLastNameUser son null

        when(userRepository.existsByCedUser(222222L)).thenReturn(false);
        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = authService.register(dto);

        assertThat(result.getSecondNameUser()).isNull();
        assertThat(result.getSecondLastNameUser()).isNull();
    }

    // ─────────────────────────────────────────────
    //  findByCedula()
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("findByCedula: retorna usuario cuando existe")
    void findByCedula_encontrado() {
        when(userRepository.findByCedUser(123456789L)).thenReturn(Optional.of(usuarioActivo));

        Optional<User> result = authService.findByCedula(123456789L);

        assertThat(result).isPresent();
        assertThat(result.get().getCedUser()).isEqualTo(123456789L);
    }

    @Test
    @DisplayName("findByCedula: retorna empty cuando no existe")
    void findByCedula_noEncontrado() {
        when(userRepository.findByCedUser(0L)).thenReturn(Optional.empty());

        Optional<User> result = authService.findByCedula(0L);

        assertThat(result).isEmpty();
    }

    // ─────────────────────────────────────────────
    //  findByRole()
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("findByRole: retorna lista de usuarios con el rol dado")
    void findByRole_conResultados() {
        when(userRepository.findByRoleUser(RoleUserEnum.Professional))
                .thenReturn(List.of(usuarioActivo));

        List<User> result = authService.findByRole(RoleUserEnum.Professional);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRoleUser()).isEqualTo(RoleUserEnum.Professional);
    }

    @Test
    @DisplayName("findByRole: retorna lista vacía si no hay usuarios con ese rol")
    void findByRole_sinResultados() {
        when(userRepository.findByRoleUser(RoleUserEnum.Admin))
                .thenReturn(List.of());

        List<User> result = authService.findByRole(RoleUserEnum.Admin);

        assertThat(result).isEmpty();
    }

    // ─────────────────────────────────────────────
    //  update()
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("update exitoso: actualiza campos y publica evento")
    void update_exitoso() {
        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setNameUser("JuanActualizado");
        dto.setPassUser("nuevaPass");

        when(userRepository.findById(1L)).thenReturn(Optional.of(usuarioActivo));
        when(passwordEncoder.encode("nuevaPass")).thenReturn("$2a$10$newHash");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = authService.update(1L, dto);

        assertThat(result.getNameUser()).isEqualTo("JuanActualizado");
        assertThat(result.getPassUser()).isEqualTo("$2a$10$newHash");
        verify(userEventPublisher).publishUserUpdated(result);
    }

    @Test
    @DisplayName("update: solo actualiza campos no nulos del DTO")
    void update_soloActualizaCamposNoNulos() {
        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setLastNameUser("NuevoApellido");
        // El resto de campos quedan null → no deben sobreescribir

        when(userRepository.findById(1L)).thenReturn(Optional.of(usuarioActivo));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = authService.update(1L, dto);

        assertThat(result.getLastNameUser()).isEqualTo("NuevoApellido");
        assertThat(result.getNameUser()).isEqualTo("Juan"); // sin cambio
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    @DisplayName("update falla: usuario no encontrado")
    void update_usuarioNoEncontrado() {
        UpdateUserDTO dto = new UpdateUserDTO();
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.update(99L, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Usuario no encontrado");

        verify(userRepository, never()).save(any());
        verify(userEventPublisher, never()).publishUserUpdated(any());
    }

    // ─────────────────────────────────────────────
    //  deactivate()
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("deactivate exitoso: cambia status a Inactive y publica evento")
    void deactivate_exitoso() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(usuarioActivo));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        authService.deactivate(1L);

        assertThat(usuarioActivo.getStatusUser()).isEqualTo(StatusUserEnum.Inactive);
        verify(userRepository).save(usuarioActivo);
        verify(userEventPublisher).publishUserDeactivated(usuarioActivo);
    }

    @Test
    @DisplayName("deactivate falla: usuario no encontrado")
    void deactivate_usuarioNoEncontrado() {
        when(userRepository.findById(55L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.deactivate(55L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Usuario no encontrado");

        verify(userRepository, never()).save(any());
        verify(userEventPublisher, never()).publishUserDeactivated(any());
    }

    // ─────────────────────────────────────────────
    //  existsByCedula()
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("existsByCedula: retorna true cuando la cédula existe")
    void existsByCedula_existe() {
        when(userRepository.existsByCedUser(123456789L)).thenReturn(true);

        assertThat(authService.existsByCedula(123456789L)).isTrue();
    }

    @Test
    @DisplayName("existsByCedula: retorna false cuando la cédula no existe")
    void existsByCedula_noExiste() {
        when(userRepository.existsByCedUser(0L)).thenReturn(false);

        assertThat(authService.existsByCedula(0L)).isFalse();
    }

    // ─────────────────────────────────────────────
    //  Helper
    // ─────────────────────────────────────────────

    private RegisterUserDTO buildRegisterDTO(Long ced, String name, String lastName, RoleUserEnum role) {
        RegisterUserDTO dto = new RegisterUserDTO();
        dto.setCedUser(ced);
        dto.setPassUser("pass123");
        dto.setNameUser(name);
        dto.setLastNameUser(lastName);
        dto.setRoleUser(role);
        dto.setSecurityQuestion("¿Pregunta?");
        dto.setSecurityAnswer("Respuesta");
        return dto;
    }
}
