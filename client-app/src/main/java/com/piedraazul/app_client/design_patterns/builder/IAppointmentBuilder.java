package com.piedraazul.app_client.design_patterns.builder;

import com.piedraazul.app_client.models.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Interfaz Constructora del patrón Builder.
 *
 * <p>Declara todos los pasos de construcción de un {@link Appointment}.
 * Cualquier clase que quiera construir citas debe implementar esta interfaz,
 * lo que garantiza que todos los constructores concretos exponen el mismo
 * conjunto de pasos y son intercambiables desde el {@link AppointmentDirector}.</p>
 *
 * <p>Cada método retorna el propio builder (fluent interface) para permitir
 * encadenamiento de llamadas.</p>
 */
public interface IAppointmentBuilder {

    /** Reinicia el builder a un estado limpio para reutilizarlo. */
    IAppointmentBuilder reset();

    // ── Datos del slot horario (obligatorios) ────────────────────────────────

    IAppointmentBuilder setProfessionalId(Long professionalId);

    IAppointmentBuilder setDate(LocalDate date);

    IAppointmentBuilder setTime(LocalTime time);

    // ── Datos auxiliares del profesional (para la vista) ─────────────────────

    IAppointmentBuilder setProfessionalName(String name);

    IAppointmentBuilder setSpecialityName(String speciality);

    IAppointmentBuilder setTypeProfName(String type);

    // ── Datos del paciente ───────────────────────────────────────────────────

    IAppointmentBuilder setPatientId(Long patientId);

    IAppointmentBuilder setPatientName(String name);

    // ── Datos clínicos ───────────────────────────────────────────────────────

    IAppointmentBuilder setDescription(String description);

    IAppointmentBuilder setStatus(String status);

    // ── Construcción final ───────────────────────────────────────────────────

    /**
     * Construye y retorna el objeto {@link Appointment} con los datos acumulados.
     *
     * @throws IllegalStateException si faltan campos obligatorios.
     */
    Appointment build();
}