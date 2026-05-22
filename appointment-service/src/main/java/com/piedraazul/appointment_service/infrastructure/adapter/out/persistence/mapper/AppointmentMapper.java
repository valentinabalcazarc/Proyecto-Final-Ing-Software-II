package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper;

import com.piedraazul.appointment_service.domain.model.Appointment;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {

    private final ProfessionalRefMapper professionalRefMapper;
    private final PatientRefMapper patientRefMapper;

    public Appointment toDomain(AppointmentEntity entity) {
        if (entity == null) return null;
        return Appointment.builder()
                .codApp(entity.getCodApp())
                .professionalRef(professionalRefMapper.toDomain(entity.getProfessionalRef()))
                .patientRef(patientRefMapper.toDomain(entity.getPatientRef()))
                .dateApp(entity.getDateApp())
                .timeApp(entity.getTimeApp())
                .descApp(entity.getDescApp())
                .statusApp(entity.getStatusApp())
                .build();
    }

    public AppointmentEntity toEntity(Appointment domain) {
        if (domain == null) return null;
        AppointmentEntity entity = new AppointmentEntity();
        entity.setCodApp(domain.getCodApp());
        entity.setProfessionalRef(professionalRefMapper.toEntity(domain.getProfessionalRef()));
        entity.setPatientRef(patientRefMapper.toEntity(domain.getPatientRef()));
        entity.setDateApp(domain.getDateApp());
        entity.setTimeApp(domain.getTimeApp());
        entity.setDescApp(domain.getDescApp());
        entity.setStatusApp(domain.getStatusApp());
        return entity;
    }
}
