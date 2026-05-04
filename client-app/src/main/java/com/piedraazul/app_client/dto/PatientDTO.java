package com.piedraazul.app_client.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDTO {
    private Long codPatient;
    private Long idPatient;
    private String namePatient;
    private String secondNamePatient;
    private String lastNamePatient;
    private String secondLastNamePatient;
    private Long phonePatient;
    private LocalDate dateBirthPatient;
    private String genderPatient;
}
