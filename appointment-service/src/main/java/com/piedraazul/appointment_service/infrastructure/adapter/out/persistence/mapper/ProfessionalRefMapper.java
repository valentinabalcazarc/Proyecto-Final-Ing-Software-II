package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper;

import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.ProfessionalRefEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfessionalRefMapper {

    public ProfessionalRef toDomain(ProfessionalRefEntity entity) {
        if (entity == null)
            return null;

        return ProfessionalRef.builder()
                .codProf(entity.getCodProf())
                .nameProf(entity.getNameProf())
                .lastNameProf(entity.getLastNameProf())
                .specialityProf(entity.getSpecialityProf())
                .typeProf(entity.getTypeProf())
                .arrivalTime(entity.getArrivalTime())
                .departureTime(entity.getDepartureTime())
                .attentionInterval(entity.getAttentionInterval())
                .unavailableDays(entity.getUnavailableDays())
                .build();
    }

    public ProfessionalRefEntity toEntity(ProfessionalRef domain) {
        if (domain == null)
            return null;

        ProfessionalRefEntity entity = new ProfessionalRefEntity();
        entity.setCodProf(domain.getCodProf());
        entity.setNameProf(domain.getNameProf());
        entity.setLastNameProf(domain.getLastNameProf());
        entity.setSpecialityProf(domain.getSpecialityProf());
        entity.setTypeProf(domain.getTypeProf());
        entity.setArrivalTime(domain.getArrivalTime());
        entity.setDepartureTime(domain.getDepartureTime());
        entity.setAttentionInterval(domain.getAttentionInterval());
        entity.setUnavailableDays(domain.getUnavailableDays());
        return entity;
    }
}
