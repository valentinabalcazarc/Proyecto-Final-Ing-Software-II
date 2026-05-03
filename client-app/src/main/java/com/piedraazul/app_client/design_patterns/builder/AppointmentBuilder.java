package com.piedraazul.app_client.design_patterns.builder;

import com.piedraazul.app_client.models.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Builder pattern for Appointment.
 * Separates the construction of a complex Appointment object from its model.
 *
 * Usage:
 *   Appointment app = new AppointmentBuilder(codProf, date, time)
 *           .patientId(123L)
 *           .description("Consulta general")
 *           .professionalName("Dr. García")
 *           .build();
 */
public class AppointmentBuilder {

    // Campos obligatorios
    private final Long professionalId;
    private final LocalDate date;
    private final LocalTime time;

    // Campos opcionales
    private Long id;
    private Long patientId;
    private String status;
    private String description;
    private String professionalName;
    private String specialityName;
    private String typeProfName;
    private String patientName;

    public AppointmentBuilder(Long professionalId, LocalDate date, LocalTime time) {
        this.professionalId = professionalId;
        this.date = date;
        this.time = time;
    }

    public AppointmentBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public AppointmentBuilder patientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public AppointmentBuilder status(String status) {
        this.status = status;
        return this;
    }

    public AppointmentBuilder description(String description) {
        this.description = description;
        return this;
    }

    public AppointmentBuilder professionalName(String professionalName) {
        this.professionalName = professionalName;
        return this;
    }

    public AppointmentBuilder specialityName(String specialityName) {
        this.specialityName = specialityName;
        return this;
    }

    public AppointmentBuilder typeProfName(String typeProfName) {
        this.typeProfName = typeProfName;
        return this;
    }

    public AppointmentBuilder patientName(String patientName) {
        this.patientName = patientName;
        return this;
    }

    public Appointment build() {
        Appointment a = new Appointment();
        a.setProfessionalId(professionalId);
        a.setDate(date);
        a.setTime(time);
        a.setId(id);
        a.setPatientId(patientId);
        a.setStatus(status);
        a.setDescription(description);
        a.setProfessionalName(professionalName);
        a.setSpecialityName(specialityName);
        a.setTypeProfName(typeProfName);
        a.setPatientName(patientName);
        return a;
    }
}