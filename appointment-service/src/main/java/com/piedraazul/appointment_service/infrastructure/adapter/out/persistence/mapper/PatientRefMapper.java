package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper;

import com.piedraazul.appointment_service.domain.model.PatientRef;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.PatientRefEntity;
import org.springframework.stereotype.Component;

@Component
public class PatientRefMapper {

    public PatientRef toDomain(PatientRefEntity entity) {
        if (entity == null) return null;
        return PatientRef.builder()
                .codPatient(entity.getCodPatient())
                .idPatient(entity.getIdPatient())
                .namePatient(entity.getNamePatient())
                .lastNamePatient(entity.getLastNamePatient())
                .phonePatient(entity.getPhonePatient())
                .genderPatient(entity.getGenderPatient())
                .build();
    }

    public PatientRefEntity toEntity(PatientRef domain) {
        if (domain == null) return null;
        PatientRefEntity entity = new PatientRefEntity();
        entity.setCodPatient(domain.getCodPatient());
        entity.setIdPatient(domain.getIdPatient());
        entity.setNamePatient(domain.getNamePatient());
        entity.setLastNamePatient(domain.getLastNamePatient());
        entity.setPhonePatient(domain.getPhonePatient());
        entity.setGenderPatient(domain.getGenderPatient());
        return entity;
    }
}
