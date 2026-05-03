package com.piedraazul.appointment_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfessionalEventDTO {
    private Long codProf;
    private String nameProf;
    private String lastNameProf;
    private String specialityProf;
    private String typeProf;
    private Integer attentionInterval;
}