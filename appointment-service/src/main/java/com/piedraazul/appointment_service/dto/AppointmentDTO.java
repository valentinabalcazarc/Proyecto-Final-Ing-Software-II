package com.piedraazul.appointment_service.dto;

import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.TypeProfEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {
    @NotNull
    private Long codProf;
    @NotNull
    private Long codPatient;
    @NotNull
    private LocalDate dateApp;
    @NotNull
    private LocalTime timeApp;
    private String descApp;
    private String professionalName;
    private TypeProfEnum typeProf;
    private SpecialityProfEnum specialityProf;
}