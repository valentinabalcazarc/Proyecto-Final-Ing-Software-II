package com.piedraazul.people_service.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdatePatientDTO {
    private String namePatient;
    private String secondNamePatient;
    private String lastNamePatient;
    private String secondLastNamePatient;
    private Long phonePatient;
    private LocalDate dateBirthPatient;
    private String genderPatient;
}