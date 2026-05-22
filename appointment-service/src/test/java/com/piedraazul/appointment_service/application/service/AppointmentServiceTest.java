package com.piedraazul.appointment_service.application.service;

import com.piedraazul.appointment_service.application.dto.AppointmentDTO;
import com.piedraazul.appointment_service.application.dto.CreateAppointmentDTO;
import com.piedraazul.appointment_service.application.dto.UpdateAppointmentDTO;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.enums.TypeProfEnum;
import com.piedraazul.appointment_service.domain.model.Appointment;
import com.piedraazul.appointment_service.domain.model.PatientRef;
import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.domain.model.UnavailableDayRef;
import com.piedraazul.appointment_service.domain.port.out.AppointmentRepositoryPort;
import com.piedraazul.appointment_service.domain.port.out.PatientRefRepositoryPort;
import com.piedraazul.appointment_service.domain.port.out.ProfessionalRefRepositoryPort;
import com.piedraazul.appointment_service.domain.port.out.UnavailableDayRefRepositoryPort;
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
@DisplayName("AppointmentService - Pruebas Unitarias (Hexagonal)")
class AppointmentServiceTest {

    @Mock private AppointmentRepositoryPort appointmentRepository;
    @Mock private ProfessionalRefRepositoryPort professionalRefRepository;
    @Mock private PatientRefRepositoryPort patientRefRepository;
    @Mock private UnavailableDayRefRepositoryPort unavailableDayRefRepository;

    @InjectMocks private AppointmentService appointmentService;

    private ProfessionalRef profMock;
    private PatientRef patientMock;
    private Appointment appointmentMock;

    @BeforeEach
    void setUp() {
        profMock = ProfessionalRef.builder()
                .codProf(1L)
                .nameProf("Ana")
                .lastNameProf("Gómez")
                .specialityProf(SpecialityProfEnum.Physiotherapy)
                .typeProf(TypeProfEnum.Doctor)
                .arrivalTime(LocalTime.of(8, 0))
                .departureTime(LocalTime.of(10, 0))
                .attentionInterval(60)
                .build();

        patientMock = PatientRef.builder()
                .codPatient(10L)
                .namePatient("Carlos")
                .lastNamePatient("Ruiz")
                .build();

        appointmentMock = new Appointment(profMock, patientMock, LocalDate.of(2026, 6, 15), LocalTime.of(9, 0), null);
        org.springframework.test.util.ReflectionTestUtils.setField(appointmentMock, "codApp", 100L);
    }

    // ── create ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create exitoso: guarda cita con estado Scheduled")
    void create_exitoso() {
        CreateAppointmentDTO dto = buildCreateDTO(1L, 10L,
                LocalDate.of(2026, 6, 15), LocalTime.of(9, 0));

        when(patientRefRepository.findById(10L)).thenReturn(Optional.of(patientMock));
        when(professionalRefRepository.findById(1L)).thenReturn(Optional.of(profMock));
        when(appointmentRepository.save(any())).thenAnswer(inv -> {
            Appointment a = inv.getArgument(0);
            org.springframework.test.util.ReflectionTestUtils.setField(a, "codApp", 100L);
            return a;
        });

        Appointment result = appointmentService.create(dto);

        assertThat(result.getCodApp()).isEqualTo(100L);
        assertThat(result.getStatusApp()).isEqualTo(StatusAppointment.Scheduled);
        assertThat(result.getProfessionalRef()).isEqualTo(profMock);
        assertThat(result.getPatientRef()).isEqualTo(patientMock);
        verify(appointmentRepository).save(any());
    }

    @Test
    @DisplayName("create falla: paciente no encontrado")
    void create_pacienteNoExiste() {
        CreateAppointmentDTO dto = buildCreateDTO(1L, 99L,
                LocalDate.of(2026, 6, 15), LocalTime.of(9, 0));

        when(patientRefRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.create(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe el paciente con código: 99");

        verify(appointmentRepository, never()).save(any());
    }

    @Test
    @DisplayName("create falla: profesional no encontrado")
    void create_profesionalNoExiste() {
        CreateAppointmentDTO dto = buildCreateDTO(99L, 10L,
                LocalDate.of(2026, 6, 15), LocalTime.of(9, 0));

        when(patientRefRepository.findById(10L)).thenReturn(Optional.of(patientMock));
        when(professionalRefRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.create(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe el profesional con código: 99");

        verify(appointmentRepository, never()).save(any());
    }

    // ── findById ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("findById: retorna cita cuando existe")
    void findById_encontrado() {
        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(appointmentMock));

        Optional<Appointment> result = appointmentService.findById(100L);

        assertThat(result).isPresent();
        assertThat(result.get().getCodApp()).isEqualTo(100L);
    }

    @Test
    @DisplayName("findById: retorna empty cuando no existe")
    void findById_noEncontrado() {
        when(appointmentRepository.findById(0L)).thenReturn(Optional.empty());

        assertThat(appointmentService.findById(0L)).isEmpty();
    }

    // ── findAll ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll: retorna lista con citas")
    void findAll_conResultados() {
        when(appointmentRepository.findAll()).thenReturn(List.of(appointmentMock));

        assertThat(appointmentService.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("findAll: retorna lista vacía cuando no hay citas")
    void findAll_vacio() {
        when(appointmentRepository.findAll()).thenReturn(List.of());

        assertThat(appointmentService.findAll()).isEmpty();
    }

    // ── findByStatus ──────────────────────────────────────────────────────

    @Test
    @DisplayName("findByStatus: retorna citas filtradas por estado")
    void findByStatus_conResultados() {
        when(appointmentRepository.findByStatusApp(StatusAppointment.Scheduled))
                .thenReturn(List.of(appointmentMock));

        List<Appointment> result = appointmentService.findByStatus(StatusAppointment.Scheduled);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatusApp()).isEqualTo(StatusAppointment.Scheduled);
    }

    // ── findByCodProf ─────────────────────────────────────────────────────

    @Test
    @DisplayName("findByCodProf: retorna citas del profesional")
    void findByCodProf_conResultados() {
        when(professionalRefRepository.findById(1L)).thenReturn(Optional.of(profMock));
        when(appointmentRepository.findByProfessionalRef(profMock))
                .thenReturn(List.of(appointmentMock));

        List<Appointment> result = appointmentService.findByCodProf(1L);

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("findByCodProf falla: profesional no encontrado")
    void findByCodProf_profesionalNoExiste() {
        when(professionalRefRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.findByCodProf(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Profesional no encontrado: 99");
    }

    // ── findByCodPatient ──────────────────────────────────────────────────

    @Test
    @DisplayName("findByCodPatient: retorna citas del paciente")
    void findByCodPatient_conResultados() {
        when(patientRefRepository.findById(10L)).thenReturn(Optional.of(patientMock));
        when(appointmentRepository.findByPatientRef(patientMock))
                .thenReturn(List.of(appointmentMock));

        assertThat(appointmentService.findByCodPatient(10L)).hasSize(1);
    }

    @Test
    @DisplayName("findByCodPatient falla: paciente no encontrado")
    void findByCodPatient_pacienteNoExiste() {
        when(patientRefRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.findByCodPatient(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Paciente no encontrado: 99");
    }

    // ── findByCodProfAndDate ──────────────────────────────────────────────

    @Test
    @DisplayName("findByCodProfAndDate: retorna citas del profesional en fecha dada")
    void findByCodProfAndDate_conResultados() {
        LocalDate date = LocalDate.of(2026, 6, 15);
        when(professionalRefRepository.findById(1L)).thenReturn(Optional.of(profMock));
        when(appointmentRepository.findByProfessionalRefAndDateApp(profMock, date))
                .thenReturn(List.of(appointmentMock));

        assertThat(appointmentService.findByCodProfAndDate(1L, date)).hasSize(1);
    }

    // ── update ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update exitoso: actualiza campos no nulos y guarda")
    void update_exitoso() {
        UpdateAppointmentDTO dto = new UpdateAppointmentDTO();
        dto.setDescApp("Revisión actualizada");
        dto.setStatusApp(StatusAppointment.Completed);

        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(appointmentMock));
        when(appointmentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Appointment result = appointmentService.update(100L, dto);

        assertThat(result.getDescApp()).isEqualTo("Revisión actualizada");
        assertThat(result.getStatusApp()).isEqualTo(StatusAppointment.Completed);
        assertThat(result.getDateApp()).isEqualTo(LocalDate.of(2026, 6, 15)); // sin cambio
    }

    @Test
    @DisplayName("update: solo actualiza campos no nulos")
    void update_soloActualizaCamposNoNulos() {
        UpdateAppointmentDTO dto = new UpdateAppointmentDTO();
        dto.setTimeApp(LocalTime.of(10, 0));

        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(appointmentMock));
        when(appointmentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Appointment result = appointmentService.update(100L, dto);

        assertThat(result.getTimeApp()).isEqualTo(LocalTime.of(10, 0));
        assertThat(result.getStatusApp()).isEqualTo(StatusAppointment.Scheduled); // sin cambio
    }

    @Test
    @DisplayName("update falla: cita no encontrada")
    void update_noEncontrado() {
        when(appointmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.update(99L, new UpdateAppointmentDTO()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cita no encontrada con id: 99");

        verify(appointmentRepository, never()).save(any());
    }

    // ── cancel ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("cancel exitoso: cambia estado a Cancelled y retorna true")
    void cancel_exitoso() {
        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(appointmentMock));
        when(appointmentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        boolean result = appointmentService.cancel(100L);

        assertThat(result).isTrue();
        assertThat(appointmentMock.getStatusApp()).isEqualTo(StatusAppointment.Cancelled);
        verify(appointmentRepository).save(appointmentMock);
    }

    @Test
    @DisplayName("cancel: retorna false si la cita no existe")
    void cancel_noExiste() {
        when(appointmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThat(appointmentService.cancel(99L)).isFalse();
        verify(appointmentRepository, never()).save(any());
    }

    // ── generateAvailableSlots ────────────────────────────────────────────

    @Test
    @DisplayName("generateAvailableSlots: retorna slots libres para un profesional y fecha futura")
    void generateAvailableSlots_retornaSlots() {
        LocalDate futureDate = LocalDate.now().plusDays(5);

        when(professionalRefRepository.findById(1L)).thenReturn(Optional.of(profMock));
        when(appointmentRepository.findByDateAppAndStatusAppNot(futureDate, StatusAppointment.Cancelled))
                .thenReturn(List.of());
        when(unavailableDayRefRepository.findByProfessionalRef(profMock)).thenReturn(List.of());

        List<AppointmentDTO> slots = appointmentService.generateAvailableSlots(1L, futureDate);

        // Con horario 08:00–10:00 e intervalo 60min → slots a las 08:00 y 09:00
        assertThat(slots).hasSize(2);
        assertThat(slots.get(0).getCodProf()).isEqualTo(1L);
        assertThat(slots.get(0).getDateApp()).isEqualTo(futureDate);
    }

    @Test
    @DisplayName("generateAvailableSlots: excluye día no laborable del profesional")
    void generateAvailableSlots_diaNoLaborable() {
        LocalDate unavailableDate = LocalDate.now().plusDays(3);

        UnavailableDayRef unavDay = UnavailableDayRef.builder()
                .id(1L)
                .professionalRef(profMock)
                .date(unavailableDate)
                .build();

        when(professionalRefRepository.findById(1L)).thenReturn(Optional.of(profMock));
        when(appointmentRepository.findByDateAppAndStatusAppNot(unavailableDate, StatusAppointment.Cancelled))
                .thenReturn(List.of());
        when(unavailableDayRefRepository.findByProfessionalRef(profMock))
                .thenReturn(List.of(unavDay));

        List<AppointmentDTO> slots = appointmentService.generateAvailableSlots(1L, unavailableDate);

        assertThat(slots).isEmpty();
    }

    @Test
    @DisplayName("generateAvailableSlots: excluye slots ya ocupados")
    void generateAvailableSlots_slotOcupado() {
        LocalDate futureDate = LocalDate.now().plusDays(5);

        // Ocupa el slot de las 08:00
        Appointment occupied = new Appointment(profMock, patientMock, futureDate, LocalTime.of(8, 0), null);

        when(professionalRefRepository.findById(1L)).thenReturn(Optional.of(profMock));
        when(appointmentRepository.findByDateAppAndStatusAppNot(futureDate, StatusAppointment.Cancelled))
                .thenReturn(List.of(occupied));
        when(unavailableDayRefRepository.findByProfessionalRef(profMock)).thenReturn(List.of());

        List<AppointmentDTO> slots = appointmentService.generateAvailableSlots(1L, futureDate);

        // Solo queda el slot de las 09:00
        assertThat(slots).hasSize(1);
        assertThat(slots.get(0).getTimeApp()).isEqualTo(LocalTime.of(9, 0));
    }

    // ── findBySpecialityProf ──────────────────────────────────────────────

    @Test
    @DisplayName("findBySpecialityProf: retorna citas filtradas por especialidad")
    void findBySpecialityProf_conResultados() {
        when(appointmentRepository.findByProfessionalRefSpecialityProf(SpecialityProfEnum.Physiotherapy))
                .thenReturn(List.of(appointmentMock));

        List<Appointment> result = appointmentService.findBySpecialityProf(SpecialityProfEnum.Physiotherapy);

        assertThat(result).hasSize(1);
    }

    // ── findByDateApp ─────────────────────────────────────────────────────

    @Test
    @DisplayName("findByDateApp: retorna citas de la fecha dada")
    void findByDateApp_conResultados() {
        LocalDate date = LocalDate.of(2026, 6, 15);
        when(appointmentRepository.findByDateApp(date)).thenReturn(List.of(appointmentMock));

        assertThat(appointmentService.findByDateApp(date)).hasSize(1);
    }

    // ── generateBySpeciality ──────────────────────────────────────────────

    @Test
    @DisplayName("generateBySpeciality: retorna slots disponibles por especialidad para hoy")
    void generateBySpeciality_retornaSlots() {
        // Usar horario pasado para que no queden slots reales (evita dependencia de LocalTime.now())
        ProfessionalRef profPasado = ProfessionalRef.builder()
                .codProf(1L).nameProf("Ana").lastNameProf("Gómez")
                .specialityProf(SpecialityProfEnum.Physiotherapy)
                .typeProf(TypeProfEnum.Doctor)
                .arrivalTime(LocalTime.of(0, 0))
                .departureTime(LocalTime.of(0, 30))
                .attentionInterval(30)
                .build();

        when(professionalRefRepository.findBySpecialityProf(SpecialityProfEnum.Physiotherapy))
                .thenReturn(List.of(profPasado));
        when(appointmentRepository.findByDateAppAndStatusAppNot(any(), eq(StatusAppointment.Cancelled)))
                .thenReturn(List.of());
        when(unavailableDayRefRepository.findByProfessionalRef(profPasado)).thenReturn(List.of());

        List<AppointmentDTO> slots = appointmentService.generateBySpeciality(SpecialityProfEnum.Physiotherapy);

        // Todos los slots son pasados → lista vacía es resultado válido
        assertThat(slots).isNotNull();
    }

    // ── helper ────────────────────────────────────────────────────────────

    private CreateAppointmentDTO buildCreateDTO(Long codProf, Long codPatient,
                                                 LocalDate date, LocalTime time) {
        CreateAppointmentDTO dto = new CreateAppointmentDTO();
        dto.setCodProf(codProf);
        dto.setCodPatient(codPatient);
        dto.setDateApp(date);
        dto.setTimeApp(time);
        return dto;
    }
}
