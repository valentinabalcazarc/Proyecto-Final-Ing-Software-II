package com.piedraazul.people_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "UNAVAILABLE_DAY")
@Data
public class UnavailableDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_UNAVAILABLE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CODPROF", nullable = false)
    private Professional professional;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "REASON")
    private String reason;
}