package com.piedraazul.app_client.design_patterns.strategy;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.ServiceManager;

import java.util.List;

/**
 * Estrategia Concreta 2 — Slots generados filtrados por profesional y/o fecha.
 *
 * <p>Permite buscar slots disponibles combinando opcionalmente el ID del
 * profesional y una fecha específica. Si ambos parámetros son {@code null},
 * delega al servicio que decidirá el comportamiento por defecto.</p>
 *
 * <p>Usada en:</p>
 * <ul>
 *   <li>{@code ProfessionalCreateAppStep1Controller} — botón "Filtrar"</li>
 *   <li>{@code ProfessionalViewAppointmentsController} — botón "Buscar"</li>
 *   <li>{@code ProfessionalExportController} — botón "Buscar"</li>
 * </ul>
 *
 * <p>Parámetros usados de {@link SearchParams}:
 *   {@code professionalId} (opcional), {@code date} (opcional).</p>
 */
public class SearchByProfAndDateStrategy implements IAppointmentSearchStrategy {

    @Override
    public List<Appointment> search(SearchParams params) {
        return ServiceManager.getInstance()
                .getAppointmentService()
                .searchAppointmentsTyped(
                        params.getProfessionalId(),
                        params.getDate());
    }
}