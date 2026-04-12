package com.piedraazul.people_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientDTO {
    @NotNull
    private Long idPatient;
    @NotBlank
    private String namePatient;
    private String secondNamePatient;
    @NotBlank
    private String lastNamePatient;
    private String secondLastNamePatient;
    private Long phonePatient;
    private LocalDate dateBirthPatient;
    @NotBlank
    private String genderPatient;
}