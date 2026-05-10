package com.piedraazul.app_client.design_patterns.strategy;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.ServiceManager;

import java.util.List;

/**
 * Estrategia Concreta 3 — Slots generados para una especialidad (sin filtro adicional).
 *
 * <p>Recupera todos los slots disponibles del microservicio filtrando
 * únicamente por la especialidad del profesional, sin restringir por
 * profesional específico ni por fecha.</p>
 *
 * <p>Usada en:</p>
 * <ul>
 *   <li>{@code PatientSelectSpecificAppointmentController} — carga inicial
 *       de la tabla al entrar a la pantalla con una especialidad preseleccionada.</li>
 * </ul>
 *
 * <p>Parámetros usados de {@link SearchParams}:
 *   {@code speciality} (obligatorio).</p>
 *
 * <p>Endpoint backend: {@code GET /generated/speciality/{speciality}}</p>
 */
public class SearchBySpecialityStrategy implements IAppointmentSearchStrategy {

    @Override
    public List<Appointment> search(SearchParams params) {
        // getGeneratedAppointmentsBySpecialityTyped llama a:
        // GET /generated/speciality/{speciality}  ← endpoint que SÍ existe en el backend
        return ServiceManager.getInstance()
                .getAppointmentService()
                .getGeneratedAppointmentsBySpecialityTyped(params.getSpeciality());
    }
}