package com.piedraazul.app_client.design_patterns.strategy;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.ServiceManager;

import java.util.List;
import java.util.stream.Collectors;

public class SearchByPatientAndProfStrategy implements IAppointmentSearchStrategy {

    @Override
    public List<Appointment> search(SearchParams params) {
        List<Appointment> citas = ServiceManager.getInstance()
                .getAppointmentService()
                .getAppointmentsByPatient(params.getPatientId());

        // Debug temporal
        System.out.println(">> Citas del paciente encontradas: " + citas.size());
        citas.forEach(a -> System.out.println(
                "   citaId=" + a.getId() +
                        " professionalId=" + a.getProfessionalId() +
                        " params.profId=" + params.getProfessionalId()));

        return citas.stream()
                .filter(a -> params.getProfessionalId() != null &&
                        params.getProfessionalId().equals(a.getProfessionalId()))
                .collect(Collectors.toList());
    }
}