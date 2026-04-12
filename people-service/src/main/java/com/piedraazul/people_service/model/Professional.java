package com.piedraazul.people_service.model;

import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.enums.StatusProfEnum;
import com.piedraazul.people_service.enums.TypeProfEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROFESSIONAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODPROF")
    private Long codProf;

    @Column(name = "CODUSER", nullable = false)
    private Long codUser;

    @Column(name = "GENPROF", nullable = false)
    private String genProf;

    @Column(name = "PHONEPROF")
    private String phoneProf;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUSPROF", nullable = false)
    private StatusProfEnum statusProf = StatusProfEnum.Active;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPEPROF", nullable = false)
    private TypeProfEnum typeProf;

    @Enumerated(EnumType.STRING)
    @Column(name = "SPECIALITYPROF", nullable = false)
    private SpecialityProfEnum specialityProf;

    @Column(name = "ATTENTIONINTERVAL", nullable = false)
    private Integer attentionInterval;
}