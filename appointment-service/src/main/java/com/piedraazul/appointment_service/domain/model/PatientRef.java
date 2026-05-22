package com.piedraazul.appointment_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRef {
    private Long codPatient;
    private Long idPatient;
    private String namePatient;
    private String lastNamePatient;
    private Long phonePatient;
    private String genderPatient;
}
