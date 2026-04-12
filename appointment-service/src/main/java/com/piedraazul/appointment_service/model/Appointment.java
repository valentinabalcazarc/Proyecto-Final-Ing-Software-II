package com.piedraazul.appointment_service.model;

import com.piedraazul.appointment_service.enums.StatusAppointment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "APPOINTMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODAPP")
    private Long codApp;

    @Column(name = "CODPROF", nullable = false)
    private Long codProf;

    @Column(name = "CODPATIENT", nullable = false)
    private Long codPatient;

    @Column(name = "DATEAPP")
    private LocalDate dateApp;

    @Column(name = "TIMEAPP")
    private LocalTime timeApp;

    @Column(name = "DESCAPP")
    private String descApp;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUSAPP", nullable = false)
    private StatusAppointment statusApp = StatusAppointment.Scheduled;
}