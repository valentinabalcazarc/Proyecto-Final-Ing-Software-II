package com.piedraazul.app_client.design_patterns.strategy;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.ServiceManager;
import java.util.List;

/**
 * Estrategia Concreta — Slots GENERADOS filtrados por profesional y/o fecha.
 *
 * <p>A diferencia de {@link SearchByProfAndDateStrategy} (que filtra citas
 * ya agendadas), esta estrategia trabaja exclusivamente sobre los slots
 * generados disponibles, usando el endpoint {@code /generated}.</p>
 *
 * <p>Usada en:</p>
 * <ul>
 *   <li>{@code ProfessionalCreateAppStep1Controller} — botón "Filtrar"</li>
 * </ul>
 *
 * <p>Parámetros usados de {@link SearchParams}:
 *   {@code professionalId} (opcional), {@code date} (opcional).</p>
 */
public class SearchGeneratedByProfAndDateStrategy implements IAppointmentSearchStrategy {

    @Override
    public List<Appointment> search(SearchParams params) {
        return ServiceManager.getInstance()
                .getAppointmentService()
                .getGeneratedAppointmentsFilteredTyped(
                        params.getProfessionalId(),
                        params.getDate());
    }
}