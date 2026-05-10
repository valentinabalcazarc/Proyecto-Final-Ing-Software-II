package com.piedraazul.app_client.design_patterns.strategy;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.ServiceManager;

import java.util.List;

/**
 * Estrategia Concreta 4 — Slots generados por especialidad + profesional + fecha.
 *
 * <p>Aplica el filtrado más específico del flujo del paciente: restringe
 * los resultados por especialidad médica (obligatoria) y opcionalmente
 * por profesional concreto y/o fecha.</p>
 *
 * <p>Usada en:</p>
 * <ul>
 *   <li>{@code PatientSelectSpecificAppointmentController} — botón "Buscar"
 *       cuando el paciente quiere afinar la búsqueda dentro de una especialidad.</li>
 * </ul>
 *
 * <p>Parámetros usados de {@link SearchParams}:
 *   {@code speciality} (obligatorio), {@code professionalId} (opcional),
 *   {@code date} (opcional).</p>
 *
 * <p>Endpoint backend: {@code GET /generated?speciality=X[&codProf=Y][&date=Z]}</p>
 */
public class SearchBySpecialityFilteredStrategy implements IAppointmentSearchStrategy {

    @Override
    public List<Appointment> search(SearchParams params) {
        // getGeneratedAppointmentsBySpecialityFilteredTyped llama a:
        // GET /generated?spec={speciality}[&date={date}][&codProf={codProf}]
        // Si fecha es null, NO fuerza la fecha de hoy — muestra todos los slots futuros
        return ServiceManager.getInstance()
                .getAppointmentService()
                .getGeneratedAppointmentsBySpecialityFilteredTyped(
                        params.getProfessionalId(),
                        params.getDate(),
                        params.getSpeciality());
    }
}