package com.piedraazul.appointment_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnavailableDayRef {
    private Long id;
    private ProfessionalRef professionalRef;
    private LocalDate date;
    private String reason;
}
