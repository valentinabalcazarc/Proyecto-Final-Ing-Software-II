package com.piedraazul.people_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UnavailableDayDTO {

    @NotNull(message = "el código del profesional es obligatorio")
    private Long codProf;

    @NotNull(message = "la fecha es obligatoria")
    private LocalDate date;

    private String reason;
}