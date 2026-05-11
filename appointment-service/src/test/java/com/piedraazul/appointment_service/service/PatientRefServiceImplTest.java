package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.model.PatientRef;
import com.piedraazul.appointment_service.repository.PatientRefRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PatientRefServiceImpl - Pruebas Unitarias")
class PatientRefServiceImplTest {

    @Mock private PatientRefRepository patientRefRepository;

    @InjectMocks private PatientRefServiceImpl patientRefService;

    private PatientRef patientRefMock;

    @BeforeEach
    void setUp() {
        patientRefMock = new PatientRef();
        patientRefMock.setCodPatient(10L);
        patientRefMock.setIdPatient(123456L);
        patientRefMock.setNamePatient("Carlos");
        patientRefMock.setLastNamePatient("Ruiz");
        patientRefMock.setGenderPatient("M");
    }

    // ── save ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("save: delega al repositorio y retorna la entidad guardada")
    void save_exitoso() {
        when(patientRefRepository.save(patientRefMock)).thenReturn(patientRefMock);

        PatientRef result = patientRefService.save(patientRefMock);

        assertThat(result).isNotNull();
        assertThat(result.getCodPatient()).isEqualTo(10L);
        assertThat(result.getNamePatient()).isEqualTo("Carlos");
        verify(patientRefRepository).save(patientRefMock);
    }

    @Test
    @DisplayName("save: persiste un paciente con campos mínimos")
    void save_camposMinimos() {
        PatientRef minimal = new PatientRef();
        minimal.setCodPatient(99L);
        minimal.setNamePatient("Ana");
        minimal.setLastNamePatient("López");

        when(patientRefRepository.save(minimal)).thenReturn(minimal);

        PatientRef result = patientRefService.save(minimal);

        assertThat(result.getCodPatient()).isEqualTo(99L);
        verify(patientRefRepository).save(minimal);
    }

    // ── existsById ────────────────────────────────────────────────────────

    @Test
    @DisplayName("existsById: retorna true cuando el paciente existe")
    void existsById_existe() {
        when(patientRefRepository.existsById(10L)).thenReturn(true);

        assertThat(patientRefService.existsById(10L)).isTrue();
    }

    @Test
    @DisplayName("existsById: retorna false cuando el paciente no existe")
    void existsById_noExiste() {
        when(patientRefRepository.existsById(99L)).thenReturn(false);

        assertThat(patientRefService.existsById(99L)).isFalse();
    }
}
