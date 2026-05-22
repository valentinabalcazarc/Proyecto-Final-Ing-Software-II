package com.piedraazul.appointment_service.domain.model;

import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.TypeProfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalRef {
    private Long codProf;
    private String nameProf;
    private String lastNameProf;
    private SpecialityProfEnum specialityProf;
    private TypeProfEnum typeProf;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private Integer attentionInterval;
    private List<UnavailableDayRef> unavailableDays;
}
