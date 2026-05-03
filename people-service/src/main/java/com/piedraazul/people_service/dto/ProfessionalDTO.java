package com.piedraazul.people_service.dto;

import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.enums.TypeProfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;

@Data
public class ProfessionalDTO {
    @NotNull
    private Long codUser;
    @NotBlank
    private String genProf;
    private String phoneProf;
    @NotNull
    private TypeProfEnum typeProf;
    @NotNull
    private SpecialityProfEnum specialityProf;
    @NotNull
    private LocalTime arrivalTime;
    @NotNull
    private LocalTime departureTime;
    @NotNull
    private Integer attentionInterval;
}