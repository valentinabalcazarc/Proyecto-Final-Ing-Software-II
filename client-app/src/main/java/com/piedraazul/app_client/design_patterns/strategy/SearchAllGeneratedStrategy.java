package com.piedraazul.app_client.design_patterns.strategy;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.ServiceManager;

import java.util.List;

/**
 * Estrategia Concreta 1 — Todos los slots generados sin filtro.
 *
 * <p>Recupera la lista completa de slots de citas generados por el
 * microservicio, sin aplicar ningún criterio de filtrado.</p>
 *
 * <p>Usada en:</p>
 * <ul>
 *   <li>{@code ProfessionalCreateAppStep1Controller} — carga inicial de la tabla</li>
 * </ul>
 *
 * <p>Parámetros usados de {@link SearchParams}: <strong>ninguno</strong>.</p>
 */
public class SearchAllGeneratedStrategy implements IAppointmentSearchStrategy {

    @Override
    public List<Appointment> search(SearchParams params) {
        return ServiceManager.getInstance()
                .getAppointmentService()
                .getGeneratedAppointmentsTyped();
    }
}