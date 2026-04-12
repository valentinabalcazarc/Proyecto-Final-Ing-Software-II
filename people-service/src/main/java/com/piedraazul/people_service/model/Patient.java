package com.piedraazul.people_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "PATIENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODPATIENT")
    private Long codPatient;

    @Column(name = "IDPATIENT", unique = true, nullable = false)
    private Long idPatient;

    @Column(name = "NAMEPATIENT", nullable = false)
    private String namePatient;

    @Column(name = "SECOND_NAMEPATIENT")
    private String secondNamePatient;

    @Column(name = "LASTNAMEPATIENT", nullable = false)
    private String lastNamePatient;

    @Column(name = "SECOND_LASTNAMEPATIENT")
    private String secondLastNamePatient;

    @Column(name = "PHONE_PATIENT")
    private Long phonePatient;

    @Column(name = "DATE_BIRTH_PATIENT")
    private LocalDate dateBirthPatient;

    @Column(name = "GENDER_PATIENT", nullable = false)
    private String genderPatient;
}