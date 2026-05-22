package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper;

import com.piedraazul.appointment_service.domain.model.UnavailableDayRef;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.UnavailableDayRefEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UnavailableDayRefMapper {

    private final ProfessionalRefMapper professionalRefMapper;

    public UnavailableDayRef toDomain(UnavailableDayRefEntity entity) {
        if (entity == null) return null;
        return UnavailableDayRef.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .reason(entity.getReason())
                .professionalRef(professionalRefMapper.toDomain(entity.getProfessionalRef()))
                .build();
    }

    public UnavailableDayRefEntity toEntity(UnavailableDayRef domain) {
        if (domain == null) return null;
        UnavailableDayRefEntity entity = new UnavailableDayRefEntity();
        entity.setId(domain.getId());
        entity.setDate(domain.getDate());
        entity.setReason(domain.getReason());
        entity.setProfessionalRef(professionalRefMapper.toEntity(domain.getProfessionalRef()));
        return entity;
    }
}
