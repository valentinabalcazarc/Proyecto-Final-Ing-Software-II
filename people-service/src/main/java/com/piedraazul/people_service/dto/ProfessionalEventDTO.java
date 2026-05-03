package com.piedraazul.people_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
