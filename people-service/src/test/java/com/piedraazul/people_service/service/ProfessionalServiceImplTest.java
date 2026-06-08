package com.piedraazul.people_service.service;

import com.piedraazul.people_service.dto.ProfessionalDTO;
import com.piedraazul.people_service.dto.UpdateProfessionalDTO;
import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.enums.StatusProfEnum;
import com.piedraazul.people_service.enums.TypeProfEnum;
import com.piedraazul.people_service.messaging.PeopleEventPublisher;
import com.piedraazul.people_service.model.Professional;
import com.piedraazul.people_service.model.UserRef;
import com.piedraazul.people_service.repository.ProfessionalRepository;
import com.piedraazul.people_service.repository.UserRefRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProfessionalServiceImpl - Pruebas Unitarias")
class ProfessionalServiceImplTest {

    @Mock private ProfessionalRepository professionalRepository;
    @Mock private UserRefRepository userRefRepository;
    @Mock private PeopleEventPublisher peopleEventPublisher;

    @InjectMocks private ProfessionalServiceImpl professionalService;

    private UserRef userRefMock;
    private Professional professionalMock;

    @BeforeEach
    void setUp() {
        userRefMock = new UserRef();
        userRefMock.setCodUser(10L);
        userRefMock.setNameUser("Juan");
        userRefMock.setLastNameUser("Pérez");

        professionalMock = new Professional();
        professionalMock.setCodProf(1L);
        professionalMock.setUserRef(userRefMock);
        professionalMock.setGenProf("M");
        professionalMock.setTypeProf(TypeProfEnum.Doctor);
        professionalMock.setSpecialityProf(SpecialityProfEnum.Physiotherapy);
        professionalMock.setArrivalTime(LocalTime.of(8, 0));
        professionalMock.setDepartureTime(LocalTime.of(17, 0));
        professionalMock.setAttentionInterval(30);
        professionalMock.setStatusProf(StatusProfEnum.Active);
    }

    // ── register ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("register exitoso: crea profesional y publica evento")
    void register_exitoso() {
        ProfessionalDTO dto = buildProfessionalDTO(10L,
                LocalTime.of(8, 0), LocalTime.of(17, 0));

        when(userRefRepository.findById(10L)).thenReturn(Optional.of(userRefMock));
        when(professionalRepository.existsByUserRef(userRefMock)).thenReturn(false);
        when(professionalRepository.save(any())).thenAnswer(inv -> {
            Professional p = inv.getArgument(0);
            p.setCodProf(1L);
            return p;
        });

        Professional result = professionalService.register(dto);

        assertThat(result.getCodProf()).isEqualTo(1L);
        assertThat(result.getStatusProf()).isEqualTo(StatusProfEnum.Active);
        verify(professionalRepository).save(any());
        verify(peopleEventPublisher).publishProfessionalRegistered(any());
    }

    @Test
    @DisplayName("register falla: usuario no existe")
    void register_usuarioNoExiste() {
        ProfessionalDTO dto = buildProfessionalDTO(99L,
                LocalTime.of(8, 0), LocalTime.of(17, 0));
        when(userRefRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professionalService.register(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("No existe un usuario con ese código");

        verify(professionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("register falla: ya existe profesional con ese usuario")
    void register_usuarioDuplicado() {
        ProfessionalDTO dto = buildProfessionalDTO(10L,
                LocalTime.of(8, 0), LocalTime.of(17, 0));
        when(userRefRepository.findById(10L)).thenReturn(Optional.of(userRefMock));
        when(professionalRepository.existsByUserRef(userRefMock)).thenReturn(true);

        assertThatThrownBy(() -> professionalService.register(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ya existe un profesional con ese usuario");
    }

    @Test
    @DisplayName("register falla: hora llegada mayor que hora salida")
    void register_horaInvalida() {
        ProfessionalDTO dto = buildProfessionalDTO(10L,
                LocalTime.of(18, 0), LocalTime.of(8, 0)); // llegada > salida
        when(userRefRepository.findById(10L)).thenReturn(Optional.of(userRefMock));
        when(professionalRepository.existsByUserRef(userRefMock)).thenReturn(false);

        assertThatThrownBy(() -> professionalService.register(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("La hora de llegada no puede ser mayor que la de salida");
    }

    // ── findByCodUser ─────────────────────────────────────────────────────

    @Test
    @DisplayName("findByCodUser: retorna profesional cuando existe")
    void findByCodUser_encontrado() {
        when(userRefRepository.findById(10L)).thenReturn(Optional.of(userRefMock));
        when(professionalRepository.findByUserRef(userRefMock))
                .thenReturn(Optional.of(professionalMock));

        Optional<Professional> result = professionalService.findByCodUser(10L);

        assertThat(result).isPresent();
        assertThat(result.get().getCodProf()).isEqualTo(1L);
    }

    @Test
    @DisplayName("findByCodUser falla: usuario no encontrado")
    void findByCodUser_usuarioNoExiste() {
        when(userRefRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professionalService.findByCodUser(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado");
    }

    // ── findAll ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll: retorna lista de profesionales")
    void findAll_conResultados() {
        when(professionalRepository.findAll()).thenReturn(List.of(professionalMock));

        assertThat(professionalService.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("findAll: retorna lista vacía")
    void findAll_vacio() {
        when(professionalRepository.findAll()).thenReturn(List.of());

        assertThat(professionalService.findAll()).isEmpty();
    }

    // ── findBySpeciality ──────────────────────────────────────────────────

    @Test
    @DisplayName("findBySpeciality: retorna lista filtrada por especialidad")
    void findBySpeciality_conResultados() {
        when(professionalRepository.findBySpecialityProf(SpecialityProfEnum.Physiotherapy))
                .thenReturn(List.of(professionalMock));

        List<Professional> result = professionalService.findBySpeciality(SpecialityProfEnum.Physiotherapy);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSpecialityProf()).isEqualTo(SpecialityProfEnum.Physiotherapy);
    }

    // ── update ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update exitoso: actualiza campos y publica evento")
    void update_exitoso() {
        UpdateProfessionalDTO dto = new UpdateProfessionalDTO();
        dto.setGenProf("F");
        dto.setPhoneProf("3001234567");

        when(professionalRepository.findById(1L)).thenReturn(Optional.of(professionalMock));
        when(professionalRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Professional result = professionalService.update(1L, dto);

        assertThat(result.getGenProf()).isEqualTo("F");
        assertThat(result.getPhoneProf()).isEqualTo("3001234567");
        verify(peopleEventPublisher).publishProfessionalUpdated(result);
    }

    @Test
    @DisplayName("update falla: horario inválido al actualizar")
    void update_horaInvalida() {
        UpdateProfessionalDTO dto = new UpdateProfessionalDTO();
        dto.setArrivalTime(LocalTime.of(20, 0)); // mayor que la salida actual (17:00)

        when(professionalRepository.findById(1L)).thenReturn(Optional.of(professionalMock));

        assertThatThrownBy(() -> professionalService.update(1L, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("La hora de llegada no puede ser mayor que la de salida");
    }

    @Test
    @DisplayName("update falla: profesional no encontrado")
    void update_noEncontrado() {
        when(professionalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professionalService.update(99L, new UpdateProfessionalDTO()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Profesional no encontrado");
    }

    // ── deactivate ────────────────────────────────────────────────────────

    @Test
    @DisplayName("deactivate: cambia status a Inactive")
    void deactivate_exitoso() {
        when(professionalRepository.findById(1L)).thenReturn(Optional.of(professionalMock));
        when(professionalRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        professionalService.deactivate(1L);

        assertThat(professionalMock.getStatusProf()).isEqualTo(StatusProfEnum.Inactive);
        verify(professionalRepository).save(professionalMock);
    }

    @Test
    @DisplayName("deactivate falla: profesional no encontrado")
    void deactivate_noEncontrado() {
        when(professionalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professionalService.deactivate(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Profesional no encontrado");
    }



    // ── helper ────────────────────────────────────────────────────────────

    private ProfessionalDTO buildProfessionalDTO(Long codUser,
                                                  LocalTime arrival,
                                                  LocalTime departure) {
        ProfessionalDTO dto = new ProfessionalDTO();
        dto.setCodUser(codUser);
        dto.setGenProf("M");
        dto.setTypeProf(TypeProfEnum.Doctor);
        dto.setSpecialityProf(SpecialityProfEnum.Physiotherapy);
        dto.setArrivalTime(arrival);
        dto.setDepartureTime(departure);
        dto.setAttentionInterval(30);
        return dto;
    }
}
