package com.piedraazul.people_service.dto;

import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.enums.TypeProfEnum;
import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;

@Data
public class UpdateProfessionalDTO {

    private String genProf;
    private String phoneProf;
    private TypeProfEnum typeProf;
    private SpecialityProfEnum specialityProf;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private Integer attentionInterval;
}