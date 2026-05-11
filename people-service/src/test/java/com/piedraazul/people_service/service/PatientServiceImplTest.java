package com.piedraazul.people_service.service;

import com.piedraazul.people_service.dto.PatientDTO;
import com.piedraazul.people_service.dto.UpdatePatientDTO;
import com.piedraazul.people_service.messaging.PeopleEventPublisher;
import com.piedraazul.people_service.model.Patient;
import com.piedraazul.people_service.repository.PatientRepository;
import com.piedraazul.people_service.repository.UserRefRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PatientServiceImpl - Pruebas Unitarias")
class PatientServiceImplTest {

    @Mock private PatientRepository patientRepository;
    @Mock private UserRefRepository userRefRepository;
    @Mock private PeopleEventPublisher peopleEventPublisher;

    @InjectMocks private PatientServiceImpl patientService;

    private Patient pacienteMock;

    @BeforeEach
    void setUp() {
        pacienteMock = new Patient();
        pacienteMock.setCodPatient(1L);
        pacienteMock.setIdPatient(123456L);
        pacienteMock.setNamePatient("Carlos");
        pacienteMock.setLastNamePatient("Ruiz");
        pacienteMock.setGenderPatient("M");
    }

    // ── register ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("register exitoso: guarda paciente y publica evento")
    void register_exitoso() {
        PatientDTO dto = buildPatientDTO(111L);

        when(patientRepository.existsByIdPatient(111L)).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenAnswer(inv -> {
            Patient p = inv.getArgument(0);
            p.setCodPatient(1L);
            return p;
        });

        Patient result = patientService.register(dto);

        assertThat(result).isNotNull();
        assertThat(result.getIdPatient()).isEqualTo(111L);
        assertThat(result.getNamePatient()).isEqualTo("Carlos");
        verify(patientRepository).save(any(Patient.class));
        verify(peopleEventPublisher).publishPatientRegistered(any(Patient.class));
    }

    @Test
    @DisplayName("register falla: identificación duplicada")
    void register_idDuplicado() {
        PatientDTO dto = buildPatientDTO(123456L);
        when(patientRepository.existsByIdPatient(123456L)).thenReturn(true);

        assertThatThrownBy(() -> patientService.register(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ya existe un paciente con esa identificación");

        verify(patientRepository, never()).save(any());
        verify(peopleEventPublisher, never()).publishPatientRegistered(any());
    }

    @Test
    @DisplayName("register: campos opcionales nulos se aceptan")
    void register_camposOpcionalesNulos() {
        PatientDTO dto = new PatientDTO();
        dto.setIdPatient(999L);
        dto.setNamePatient("Ana");
        dto.setLastNamePatient("López");
        dto.setGenderPatient("F");

        when(patientRepository.existsByIdPatient(999L)).thenReturn(false);
        when(patientRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Patient result = patientService.register(dto);

        assertThat(result.getSecondNamePatient()).isNull();
        assertThat(result.getPhonePatient()).isNull();
    }

    // ── findByIdPatient ───────────────────────────────────────────────────

    @Test
    @DisplayName("findByIdPatient: retorna paciente cuando existe")
    void findByIdPatient_encontrado() {
        when(patientRepository.findByIdPatient(123456L)).thenReturn(Optional.of(pacienteMock));

        Optional<Patient> result = patientService.findByIdPatient(123456L);

        assertThat(result).isPresent();
        assertThat(result.get().getIdPatient()).isEqualTo(123456L);
    }

    @Test
    @DisplayName("findByIdPatient: retorna empty cuando no existe")
    void findByIdPatient_noEncontrado() {
        when(patientRepository.findByIdPatient(0L)).thenReturn(Optional.empty());

        assertThat(patientService.findByIdPatient(0L)).isEmpty();
    }

    // ── findAll ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll: retorna lista con pacientes")
    void findAll_conResultados() {
        when(patientRepository.findAll()).thenReturn(List.of(pacienteMock));

        List<Patient> result = patientService.findAll();

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("findAll: retorna lista vacía cuando no hay pacientes")
    void findAll_vacio() {
        when(patientRepository.findAll()).thenReturn(List.of());

        assertThat(patientService.findAll()).isEmpty();
    }

    // ── update ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update exitoso: actualiza campos y publica evento")
    void update_exitoso() {
        UpdatePatientDTO dto = new UpdatePatientDTO();
        dto.setNamePatient("CarlosActualizado");
        dto.setGenderPatient("M");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(pacienteMock));
        when(patientRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Patient result = patientService.update(1L, dto);

        assertThat(result.getNamePatient()).isEqualTo("CarlosActualizado");
        verify(peopleEventPublisher).publishPatientUpdated(result);
    }

    @Test
    @DisplayName("update: solo actualiza campos no nulos")
    void update_soloActualizaCamposNoNulos() {
        UpdatePatientDTO dto = new UpdatePatientDTO();
        dto.setLastNamePatient("NuevoApellido");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(pacienteMock));
        when(patientRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Patient result = patientService.update(1L, dto);

        assertThat(result.getLastNamePatient()).isEqualTo("NuevoApellido");
        assertThat(result.getNamePatient()).isEqualTo("Carlos"); // sin cambio
    }

    @Test
    @DisplayName("update falla: paciente no encontrado")
    void update_noEncontrado() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.update(99L, new UpdatePatientDTO()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Paciente no encontrado");

        verify(patientRepository, never()).save(any());
    }

    // ── delete ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: llama deleteById con el id correcto")
    void delete_exitoso() {
        doNothing().when(patientRepository).deleteById(1L);

        patientService.delete(1L);

        verify(patientRepository).deleteById(1L);
    }

    // ── helper ────────────────────────────────────────────────────────────

    private PatientDTO buildPatientDTO(Long id) {
        PatientDTO dto = new PatientDTO();
        dto.setIdPatient(id);
        dto.setNamePatient("Carlos");
        dto.setLastNamePatient("Ruiz");
        dto.setGenderPatient("M");
        dto.setDateBirthPatient(LocalDate.of(1990, 5, 10));
        return dto;
    }
}
