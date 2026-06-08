package com.piedraazul.people_service.model;

import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.enums.StatusProfEnum;
import com.piedraazul.people_service.enums.TypeProfEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Column(name = "UNAVAILABLE_DAYS")
    private String unavailableDays;

    /**
     * Convierte el String de días no laborables a una lista de DayOfWeek.
     * Ej: "MONDAY,WEDNESDAY" → [MONDAY, WEDNESDAY]
     */
    public List<DayOfWeek> getUnavailableDaysList() {
        if (unavailableDays == null || unavailableDays.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(unavailableDays.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());
    }
}