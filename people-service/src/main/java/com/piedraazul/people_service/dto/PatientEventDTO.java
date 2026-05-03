package com.piedraazul.people_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientEventDTO {
    private Long codPatient;
    private Long idPatient;
    private String namePatient;
    private String secondNamePatient;
    private String lastNamePatient;
    private String secondLastNamePatient;
    private Long phonePatient;
    private String genderPatient;
}