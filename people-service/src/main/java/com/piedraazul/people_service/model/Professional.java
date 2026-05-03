package com.piedraazul.people_service.model;

import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.enums.StatusProfEnum;
import com.piedraazul.people_service.enums.TypeProfEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "CODUSER", nullable = false)
    private UserRef userRef;

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

    @Column(name = "ARRIVALTIME", nullable = false)
    private LocalTime arrivalTime;

    @Column(name = "DEPARTURETIME", nullable = false)
    private LocalTime departureTime;

    @Column(name = "ATTENTIONINTERVAL", nullable = false)
    private Integer attentionInterval;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UnavailableDay> unavailableDays;
}