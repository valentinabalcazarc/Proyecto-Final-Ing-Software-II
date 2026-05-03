package com.piedraazul.appointment_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ProfessionalEventDTO {
    private Long codProf;
    private String nameProf;
    private String lastNameProf;
    private String specialityProf;
    private String typeProf;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private Integer attentionInterval;
}