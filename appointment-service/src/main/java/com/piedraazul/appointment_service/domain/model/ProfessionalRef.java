package com.piedraazul.appointment_service.domain.model;

import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.TypeProfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalRef {
    private Long codProf;
    private String nameProf;
    private String lastNameProf;
    private SpecialityProfEnum specialityProf;
    private TypeProfEnum typeProf;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private Integer attentionInterval;
    private String unavailableDays;

    /**
     * Convierte el String de días no laborables a una lista de DayOfWeek.
     * Ej: "MONDAY,WEDNESDAY" → [MONDAY, WEDNESDAY]
     */
    public List<DayOfWeek> getUnavailableDaysList() {
        if (unavailableDays == null || unavailableDays.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(unavailableDays.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());
    }
}
