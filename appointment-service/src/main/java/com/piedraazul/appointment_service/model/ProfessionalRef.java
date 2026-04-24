package com.piedraazul.appointment_service.model;

import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.TypeProfEnum;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "SPECIALITYPROF")
    private SpecialityProfEnum specialityProf;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPEPROF")
    private TypeProfEnum typeProf;

    @Column(name = "ATTENTIONINTERVAL")
    private Integer attentionInterval;
}