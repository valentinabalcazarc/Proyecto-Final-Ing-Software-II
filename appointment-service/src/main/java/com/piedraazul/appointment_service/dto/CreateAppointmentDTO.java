package com.piedraazul.appointment_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateAppointmentDTO {

    @NotNull(message = "el código del profesional es obligatorio")
    private Long codProf;

    @NotNull(message = "el código del paciente es obligatorio")
    private Long codPatient;

    @NotNull(message = "la fecha es obligatoria")
    private LocalDate dateApp;

    @NotNull(message = "la hora es obligatoria")
    private LocalTime timeApp;

    private String descApp;
}