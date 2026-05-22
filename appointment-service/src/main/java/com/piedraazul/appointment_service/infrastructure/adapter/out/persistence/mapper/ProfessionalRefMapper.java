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
                // unavailableDays NO se mapea aquí: es una colección lazy y se obtiene
                // explícitamente vía UnavailableDayRefRepositoryPort cuando se necesita.
                .unavailableDays(null)
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
        return entity;
    }
}
