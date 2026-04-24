package com.piedraazul.app_client.models;


import lombok.Data;

@Data
public class Patient {
    private Long codPatient;
    private Long idPatient;
    private String namePatient;
    private String secondNamePatient;
    private String lastNamePatient;
    private String secondLastNamePatient;
    private Long phonePatient;
    private java.time.LocalDate dateBirthPatient;
    private String genderPatient;

}
