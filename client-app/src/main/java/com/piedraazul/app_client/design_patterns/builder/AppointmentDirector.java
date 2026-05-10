package com.piedraazul.app_client.design_patterns.builder;

import com.piedraazul.app_client.models.Appointment;

/**
 * Director del patrón Builder.
 *
 * <p>Define el <strong>orden</strong> en que se invocan los pasos de
 * construcción y encapsula recetas predefinidas para los distintos tipos
 * de cita que maneja la aplicación. El Director conoce <em>qué</em> pasos
 * ejecutar y en qué secuencia; el {@link IAppointmentBuilder} concreto
 * sabe <em>cómo</em> ejecutar cada paso.</p>
 *
 * <p>El Director se puede cambiar de builder en cualquier momento con
 * {@link #changeBuilder(IAppointmentBuilder)}, lo que permite reutilizar
 * las mismas recetas con distintas implementaciones del builder.</p>
 *
 * <p>Recetas disponibles:</p>
 * <ul>
 *   <li>{@link #buildManualAppointment} — cita agendada por el profesional
 *       en el flujo de 2 pasos, con todos los datos del paciente y
 *       observación clínica.</li>
 *   <li>{@link #buildAutonomousAppointment} — cita agendada automáticamente
 *       por el paciente desde la recomendación de primer slot disponible.</li>
 *   <li>{@link #buildSlotPreview} — objeto de previsualización del slot
 *       seleccionado en el Paso 1, sin datos de paciente ni descripción.</li>
 * </ul>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>
 *   AppointmentBuilder builder = new AppointmentBuilder();
 *   AppointmentDirector director = new AppointmentDirector(builder);
 *
 *   // Cita autónoma desde la recomendación al paciente
 *   Appointment cita = director.buildAutonomousAppointment(slot, patient.getCodPatient());
 *
 *   // Más adelante, cita manual desde el flujo profesional
 *   Appointment citaManual = director.buildManualAppointment(
 *       slot, patient.getCodPatient(), patient.getNamePatient(), "Observación clínica");
 * </pre>
 */
public class AppointmentDirector {

    private IAppointmentBuilder builder;

    // ── Constructor ──────────────────────────────────────────────────────────

    /**
     * Crea el Director con el builder concreto inicial.
     *
     * @param builder implementación concreta del builder a usar.
     */
    public AppointmentDirector(IAppointmentBuilder builder) {
        this.builder = builder;
    }

    // ── Gestión del builder ──────────────────────────────────────────────────

    /**
     * Sustituye el builder actual, permitiendo reutilizar el Director con
     * otra implementación concreta sin perder las recetas definidas.
     *
     * @param builder nueva implementación concreta del builder.
     */
    public void changeBuilder(IAppointmentBuilder builder) {
        this.builder = builder;
    }

    // ── Recetas predefinidas (make methods) ──────────────────────────────────

    /**
     * Receta 1 — Cita manual agendada por el profesional (flujo 2 pasos).
     *
     * <p>Incluye todos los datos del slot, la información auxiliar del
     * profesional para la vista, el paciente y la observación clínica
     * escrita en el Paso 2.</p>
     *
     * @param slot          cita-slot seleccionada en el Paso 1 (contiene
     *                      professionalId, date, time y campos de vista).
     * @param patientId     ID interno del paciente ya gestionado por la Facade.
     * @param patientName   nombre completo del paciente para la vista.
     * @param description   observación clínica ingresada en el Paso 2
     *                      (ya puede incluir el tag del Decorador).
     * @return {@link Appointment} listo para enviarse a la Facade.
     */
    public Appointment buildManualAppointment(Appointment slot,
                                              Long patientId,
                                              String patientName,
                                              String description) {
        return builder
                .reset()
                // Datos del slot (Paso 1)
                .setProfessionalId(slot.getProfessionalId())
                .setDate(slot.getDate())
                .setTime(slot.getTime())
                // Datos auxiliares del profesional para la vista
                .setProfessionalName(slot.getProfessionalName())
                .setSpecialityName(slot.getSpecialityName())
                .setTypeProfName(slot.getTypeProfName())
                // Datos del paciente (Paso 2)
                .setPatientId(patientId)
                .setPatientName(patientName)
                // Observación clínica (Paso 2, ya decorada si aplica)
                .setDescription(description)
                .build();
    }

    /**
     * Receta 2 — Cita autónoma agendada por el paciente.
     *
     * <p>Construye la cita mínima necesaria: slot horario + paciente +
     * descripción fija "[cita autónoma]". No incluye campos auxiliares
     * de vista porque el flujo del paciente no los necesita.</p>
     *
     * @param slot      primer slot disponible retornado por el microservicio.
     * @param patientId ID interno del paciente en sesión.
     * @return {@link Appointment} listo para enviarse a la Facade.
     */
    public Appointment buildAutonomousAppointment(Appointment slot, Long patientId) {
        return builder
                .reset()
                .setProfessionalId(slot.getProfessionalId())
                .setDate(slot.getDate())
                .setTime(slot.getTime())
                .setPatientId(patientId)
                .setDescription("[cita autónoma]")
                .build();
    }

    /**
     * Receta 3 — Previsualización del slot seleccionado (Paso 1 → Paso 2).
     *
     * <p>Construye un objeto {@link Appointment} con solo los datos del slot
     * y la información visual del profesional, sin paciente ni descripción.
     * Útil para transferir el contexto entre pantallas sin datos incompletos.</p>
     *
     * @param slot slot seleccionado en la tabla del Paso 1.
     * @return {@link Appointment} con datos del slot listo para el Paso 2.
     */
    public Appointment buildSlotPreview(Appointment slot) {
        return builder
                .reset()
                .setProfessionalId(slot.getProfessionalId())
                .setDate(slot.getDate())
                .setTime(slot.getTime())
                .setProfessionalName(slot.getProfessionalName())
                .setSpecialityName(slot.getSpecialityName())
                .setTypeProfName(slot.getTypeProfName())
                .build();
    }
}