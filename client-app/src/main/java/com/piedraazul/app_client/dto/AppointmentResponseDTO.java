package com.piedraazul.app_client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentResponseDTO {
    private Long codApp;
    private LocalDate dateApp;
    private LocalTime timeApp;
    private String statusApp;
    private String descApp;
    
    // Flattened fields for slots
    private String professionalName;
    private Long codProf;
    private String specialityProf;
    private String typeProf;
    private Long codPatient;

    // Nested objects for real appointments
    private PatientRefDTO patientRef;
    private ProfessionalRefDTO professionalRef;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PatientRefDTO {
        private Long codPatient;
        private String namePatient;
        private String lastNamePatient;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProfessionalRefDTO {
        private Long codProf;
        private String nameProf;
        private String lastNameProf;
        private String specialityProf;
        private String typeProf;
    }
}
