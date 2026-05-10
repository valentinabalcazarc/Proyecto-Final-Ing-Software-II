package com.piedraazul.app_client.design_patterns.strategy;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.ServiceManager;

import java.util.List;

/**
 * Estrategia Concreta 5 — Citas reales agendadas de un paciente.
 *
 * <p>A diferencia de las otras estrategias que trabajan con <em>slots
 * generados</em> (citas disponibles sin paciente asignado), esta estrategia
 * recupera las citas <em>realmente agendadas</em> asociadas a un paciente
 * concreto identificado por su ID interno.</p>
 *
 * <p>Usada en:</p>
 * <ul>
 *   <li>{@code ProfessionalManageAppointmentsController} — búsqueda por cédula
 *       para gestionar (cancelar / reagendar) las citas de un paciente.</li>
 * </ul>
 *
 * <p>Parámetros usados de {@link SearchParams}:
 *   {@code patientId} (obligatorio).</p>
 */
public class SearchByPatientStrategy implements IAppointmentSearchStrategy {

    @Override
    public List<Appointment> search(SearchParams params) {
        return ServiceManager.getInstance()
                .getAppointmentService()
                .getAppointmentsByPatient(params.getPatientId());
    }
}