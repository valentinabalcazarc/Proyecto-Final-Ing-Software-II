package com.piedraazul.app_client.dto;

import com.piedraazul.app_client.enums.*;
import lombok.Data;
import java.time.LocalTime;

@Data
public class ProfessionalDTO {
    private Long codUser;
    private String genProf;
    private String phoneProf;
    private TypeProfEnum typeProf;
    private SpecialityProfEnum specialityProf;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private Integer attentionInterval;
    private String unavailableDays;
}