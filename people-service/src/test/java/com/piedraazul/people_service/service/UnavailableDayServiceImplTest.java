package com.piedraazul.people_service.service;

import com.piedraazul.people_service.dto.UnavailableDayDTO;
import com.piedraazul.people_service.messaging.UnvDayPublisher;
import com.piedraazul.people_service.model.Professional;
import com.piedraazul.people_service.model.UnavailableDay;
import com.piedraazul.people_service.repository.ProfessionalRepository;
import com.piedraazul.people_service.repository.UnavailableDayRepository;
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
@DisplayName("UnavailableDayServiceImpl - Pruebas Unitarias")
class UnavailableDayServiceImplTest {

    @Mock private UnavailableDayRepository unavailableDayRepository;
    @Mock private ProfessionalRepository professionalRepository;
    @Mock private UnvDayPublisher unvDayPublisher;

    @InjectMocks private UnavailableDayServiceImpl unavailableDayService;

    private Professional professionalMock;
    private UnavailableDay dayMock;

    @BeforeEach
    void setUp() {
        professionalMock = new Professional();
        professionalMock.setCodProf(1L);

        dayMock = new UnavailableDay();
        dayMock.setId(10L);
        dayMock.setProfessional(professionalMock);
        dayMock.setDate(LocalDate.of(2026, 6, 15));
        dayMock.setReason("Vacaciones");
    }

    // ── create ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create exitoso: guarda día y publica evento")
    void create_exitoso() {
        UnavailableDayDTO dto = new UnavailableDayDTO();
        dto.setCodProf(1L);
        dto.setDate(LocalDate.of(2026, 6, 15));
        dto.setReason("Vacaciones");

        when(professionalRepository.findById(1L)).thenReturn(Optional.of(professionalMock));
        when(unavailableDayRepository.existsByProfessionalAndDate(professionalMock, dto.getDate()))
                .thenReturn(false);
        when(unavailableDayRepository.save(any())).thenAnswer(inv -> {
            UnavailableDay d = inv.getArgument(0);
            d.setId(10L);
            return d;
        });

        UnavailableDay result = unavailableDayService.create(dto);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getDate()).isEqualTo(LocalDate.of(2026, 6, 15));
        assertThat(result.getReason()).isEqualTo("Vacaciones");
        verify(unavailableDayRepository).save(any());
        verify(unvDayPublisher).publishUnavailableDayCreated(any());
    }

    @Test
    @DisplayName("create falla: profesional no encontrado")
    void create_profesionalNoExiste() {
        UnavailableDayDTO dto = new UnavailableDayDTO();
        dto.setCodProf(99L);
        dto.setDate(LocalDate.of(2026, 6, 15));

        when(professionalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> unavailableDayService.create(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Profesional no encontrado");

        verify(unavailableDayRepository, never()).save(any());
    }

    @Test
    @DisplayName("create falla: fecha duplicada para el mismo profesional")
    void create_fechaDuplicada() {
        UnavailableDayDTO dto = new UnavailableDayDTO();
        dto.setCodProf(1L);
        dto.setDate(LocalDate.of(2026, 6, 15));

        when(professionalRepository.findById(1L)).thenReturn(Optional.of(professionalMock));
        when(unavailableDayRepository.existsByProfessionalAndDate(professionalMock, dto.getDate()))
                .thenReturn(true);

        assertThatThrownBy(() -> unavailableDayService.create(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ya existe un día no laborable registrado para esa fecha");

        verify(unavailableDayRepository, never()).save(any());
    }

    @Test
    @DisplayName("create: razón nula se acepta")
    void create_sinRazon() {
        UnavailableDayDTO dto = new UnavailableDayDTO();
        dto.setCodProf(1L);
        dto.setDate(LocalDate.of(2026, 7, 1));

        when(professionalRepository.findById(1L)).thenReturn(Optional.of(professionalMock));
        when(unavailableDayRepository.existsByProfessionalAndDate(any(), any())).thenReturn(false);
        when(unavailableDayRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UnavailableDay result = unavailableDayService.create(dto);

        assertThat(result.getReason()).isNull();
    }

    // ── findByProfessional ────────────────────────────────────────────────

    @Test
    @DisplayName("findByProfessional: retorna días del profesional")
    void findByProfessional_conResultados() {
        when(professionalRepository.findById(1L)).thenReturn(Optional.of(professionalMock));
        when(unavailableDayRepository.findByProfessional(professionalMock))
                .thenReturn(List.of(dayMock));

        List<UnavailableDay> result = unavailableDayService.findByProfessional(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDate()).isEqualTo(LocalDate.of(2026, 6, 15));
    }

    @Test
    @DisplayName("findByProfessional: retorna lista vacía si no hay días")
    void findByProfessional_vacio() {
        when(professionalRepository.findById(1L)).thenReturn(Optional.of(professionalMock));
        when(unavailableDayRepository.findByProfessional(professionalMock)).thenReturn(List.of());

        assertThat(unavailableDayService.findByProfessional(1L)).isEmpty();
    }

    @Test
    @DisplayName("findByProfessional falla: profesional no encontrado")
    void findByProfessional_profesionalNoExiste() {
        when(professionalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> unavailableDayService.findByProfessional(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Profesional no encontrado");
    }

    // ── delete ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete exitoso: elimina día y publica evento, retorna true")
    void delete_exitoso() {
        when(unavailableDayRepository.findById(10L)).thenReturn(Optional.of(dayMock));
        doNothing().when(unavailableDayRepository).delete(dayMock);

        boolean result = unavailableDayService.delete(10L);

        assertThat(result).isTrue();
        verify(unavailableDayRepository).delete(dayMock);
        verify(unvDayPublisher).publishUnavailableDayDeleted(dayMock);
    }

    @Test
    @DisplayName("delete: retorna false si el día no existe")
    void delete_noExiste() {
        when(unavailableDayRepository.findById(99L)).thenReturn(Optional.empty());

        boolean result = unavailableDayService.delete(99L);

        assertThat(result).isFalse();
        verify(unavailableDayRepository, never()).delete(any());
        verify(unvDayPublisher, never()).publishUnavailableDayDeleted(any());
    }
}
