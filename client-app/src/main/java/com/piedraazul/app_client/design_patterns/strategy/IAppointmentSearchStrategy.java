package com.piedraazul.app_client.design_patterns.strategy;

import com.piedraazul.app_client.models.Appointment;

import java.util.List;

/**
 * Interfaz Strategy para búsqueda/filtrado de citas.
 *
 * <p>Define el contrato común que deben cumplir todos los algoritmos
 * de búsqueda de citas. Cada implementación concreta encapsula
 * una estrategia de filtrado distinta sin que el contexto
 * ({@link AppointmentSearchContext}) necesite conocer los detalles.</p>
 *
 * <p>Estrategias disponibles:</p>
 * <ul>
 *   <li>{@link SearchAllGeneratedStrategy}       — todos los slots generados (sin filtro)</li>
 *   <li>{@link SearchByProfAndDateStrategy}       — slots generados por profesional y/o fecha</li>
 *   <li>{@link SearchBySpecialityStrategy}        — slots generados por especialidad</li>
 *   <li>{@link SearchBySpecialityFilteredStrategy}— slots generados por especialidad + profesional + fecha</li>
 *   <li>{@link SearchByPatientStrategy}           — citas reales agendadas de un paciente</li>
 * </ul>
 */
public interface IAppointmentSearchStrategy {

    List<Appointment> search(SearchParams params);
}