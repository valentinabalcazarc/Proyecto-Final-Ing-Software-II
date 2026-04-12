package com.piedraazul.appointment_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PATIENT_REF")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRef {

    @Id
    @Column(name = "CODPATIENT")
    private Long codPatient;

    @Column(name = "IDPATIENT")
    private Long idPatient;

    @Column(name = "NAMEPATIENT")
    private String namePatient;

    @Column(name = "LASTNAMEPATIENT")
    private String lastNamePatient;

    @Column(name = "PHONE_PATIENT")
    private Long phonePatient;

    @Column(name = "GENDER_PATIENT")
    private String genderPatient;
}