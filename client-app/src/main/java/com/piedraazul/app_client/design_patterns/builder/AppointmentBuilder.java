package com.piedraazul.app_client.design_patterns.builder;

import com.piedraazul.app_client.models.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Constructor Concreto del patrón Builder.
 *
 * <p>Implementa {@link IAppointmentBuilder} para construir objetos
 * {@link Appointment} paso a paso. Cada llamada a un método {@code set...()}
 * acumula un dato en el estado interno del builder; la llamada final a
 * {@link #build()} valida los campos obligatorios y produce el objeto.</p>
 *
 * <p>Uso directo (sin Director):</p>
 * <pre>
 *   Appointment cita = new AppointmentBuilder()
 *       .setProfessionalId(prof.getCodProf())
 *       .setDate(slot.getDate())
 *       .setTime(slot.getTime())
 *       .setDescription("Revisión mensual")
 *       .build();
 * </pre>
 *
 * <p>Uso con Director (configuraciones predefinidas):</p>
 * <pre>
 *   AppointmentBuilder builder = new AppointmentBuilder();
 *   AppointmentDirector director = new AppointmentDirector(builder);
 *   Appointment cita = director.buildAutonomousAppointment(slot, patientId);
 * </pre>
 */
public class AppointmentBuilder implements IAppointmentBuilder {

    // ── Estado interno ───────────────────────────────────────────────────────

    private Long professionalId;
    private LocalDate date;
    private LocalTime time;

    private Long patientId;
    private String description;
    private String status;

    // Campos auxiliares para la vista (tablas, labels, etc.)
    private String professionalName;
    private String specialityName;
    private String typeProfName;
    private String patientName;

    // ── Constructor ──────────────────────────────────────────────────────────

    public AppointmentBuilder() {
        reset();
    }

    // ── IAppointmentBuilder ──────────────────────────────────────────────────

    @Override
    public IAppointmentBuilder reset() {
        this.professionalId   = null;
        this.date             = null;
        this.time             = null;
        this.patientId        = null;
        this.description      = null;
        this.status           = null;
        this.professionalName = null;
        this.specialityName   = null;
        this.typeProfName     = null;
        this.patientName      = null;
        return this;
    }

    @Override
    public IAppointmentBuilder setProfessionalId(Long professionalId) {
        this.professionalId = professionalId;
        return this;
    }

    @Override
    public IAppointmentBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    @Override
    public IAppointmentBuilder setTime(LocalTime time) {
        this.time = time;
        return this;
    }

    @Override
    public IAppointmentBuilder setProfessionalName(String name) {
        this.professionalName = name;
        return this;
    }

    @Override
    public IAppointmentBuilder setSpecialityName(String speciality) {
        this.specialityName = speciality;
        return this;
    }

    @Override
    public IAppointmentBuilder setTypeProfName(String type) {
        this.typeProfName = type;
        return this;
    }

    @Override
    public IAppointmentBuilder setPatientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    @Override
    public IAppointmentBuilder setPatientName(String name) {
        this.patientName = name;
        return this;
    }

    @Override
    public IAppointmentBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IAppointmentBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    // ── build() ──────────────────────────────────────────────────────────────

    /**
     * Construye el {@link Appointment}.
     * Valida que los tres campos obligatorios estén presentes antes de crear
     * el objeto; de lo contrario lanza {@link IllegalStateException}.
     *
     * <p>Tras llamar a {@code build()}, el builder se reinicia automáticamente
     * para poder ser reutilizado por el Director en construcciones sucesivas.</p>
     */
    @Override
    public Appointment build() {
        if (professionalId == null) {
            throw new IllegalStateException(
                    "[AppointmentBuilder] El ID del profesional es obligatorio.");
        }
        if (date == null) {
            throw new IllegalStateException(
                    "[AppointmentBuilder] La fecha es obligatoria.");
        }
        if (time == null) {
            throw new IllegalStateException(
                    "[AppointmentBuilder] La hora es obligatoria.");
        }

        Appointment appointment = new Appointment();
        appointment.setProfessionalId(professionalId);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setPatientId(patientId);
        appointment.setDescription(description);
        appointment.setStatus(status);
        appointment.setProfessionalName(professionalName);
        appointment.setSpecialityName(specialityName);
        appointment.setTypeProfName(typeProfName);
        appointment.setPatientName(patientName);

        // Reset automático para reutilización del builder
        reset();

        return appointment;
    }
}