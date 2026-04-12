package com.piedraazul.appointment_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROFESSIONAL_REF")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalRef {

    @Id
    @Column(name = "CODPROF")
    private Long codProf;

    @Column(name = "NAMEPROF")
    private String nameProf;

    @Column(name = "LASTNAMEPROF")
    private String lastNameProf;

    @Column(name = "SPECIALITYPROF")
    private String specialityProf;

    @Column(name = "TYPEPROF")
    private String typeProf;

    @Column(name = "ATTENTIONINTERVAL")
    private Integer attentionInterval;
}