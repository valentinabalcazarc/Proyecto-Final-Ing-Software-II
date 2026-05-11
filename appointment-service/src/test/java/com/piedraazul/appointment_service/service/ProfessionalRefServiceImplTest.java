package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.TypeProfEnum;
import com.piedraazul.appointment_service.model.ProfessionalRef;
import com.piedraazul.appointment_service.repository.ProfessionalRefRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProfessionalRefServiceImpl - Pruebas Unitarias")
class ProfessionalRefServiceImplTest {

    @Mock private ProfessionalRefRepository professionalRefRepository;

    @InjectMocks private ProfessionalRefServiceImpl professionalRefService;

    private ProfessionalRef profRefMock;

    @BeforeEach
    void setUp() {
        profRefMock = new ProfessionalRef();
        profRefMock.setCodProf(1L);
        profRefMock.setNameProf("Ana");
        profRefMock.setLastNameProf("Gómez");
        profRefMock.setSpecialityProf(SpecialityProfEnum.Physiotherapy);
        profRefMock.setTypeProf(TypeProfEnum.Doctor);
        profRefMock.setArrivalTime(LocalTime.of(8, 0));
        profRefMock.setDepartureTime(LocalTime.of(17, 0));
        profRefMock.setAttentionInterval(30);
    }

    // ── save ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("save: delega al repositorio y retorna la entidad guardada")
    void save_exitoso() {
        when(professionalRefRepository.save(profRefMock)).thenReturn(profRefMock);

        ProfessionalRef result = professionalRefService.save(profRefMock);

        assertThat(result).isNotNull();
        assertThat(result.getCodProf()).isEqualTo(1L);
        assertThat(result.getSpecialityProf()).isEqualTo(SpecialityProfEnum.Physiotherapy);
        verify(professionalRefRepository).save(profRefMock);
    }

    @Test
    @DisplayName("save: persiste un profesional con campos mínimos")
    void save_camposMinimos() {
        ProfessionalRef minimal = new ProfessionalRef();
        minimal.setCodProf(5L);
        minimal.setNameProf("Luis");
        minimal.setArrivalTime(LocalTime.of(7, 0));
        minimal.setDepartureTime(LocalTime.of(15, 0));

        when(professionalRefRepository.save(minimal)).thenReturn(minimal);

        ProfessionalRef result = professionalRefService.save(minimal);

        assertThat(result.getCodProf()).isEqualTo(5L);
        verify(professionalRefRepository).save(minimal);
    }

    // ── existsById ────────────────────────────────────────────────────────

    @Test
    @DisplayName("existsById: retorna true cuando el profesional existe")
    void existsById_existe() {
        when(professionalRefRepository.existsById(1L)).thenReturn(true);

        assertThat(professionalRefService.existsById(1L)).isTrue();
    }

    @Test
    @DisplayName("existsById: retorna false cuando el profesional no existe")
    void existsById_noExiste() {
        when(professionalRefRepository.existsById(99L)).thenReturn(false);

        assertThat(professionalRefService.existsById(99L)).isFalse();
    }
}
