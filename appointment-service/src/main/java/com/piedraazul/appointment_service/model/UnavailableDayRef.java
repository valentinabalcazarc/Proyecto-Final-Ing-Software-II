package com.piedraazul.appointment_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "UNAVAILABLE_DAY_REF")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnavailableDayRef {

    @Id
    @Column(name = "COD_UNAVAILABLE")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CODPROF", nullable = false)
    private ProfessionalRef professionalRef;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "REASON")
    private String reason;
}
