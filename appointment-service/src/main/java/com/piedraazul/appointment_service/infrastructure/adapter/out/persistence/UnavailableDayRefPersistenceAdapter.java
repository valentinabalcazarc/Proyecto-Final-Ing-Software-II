package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence;

import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.domain.model.UnavailableDayRef;
import com.piedraazul.appointment_service.domain.port.out.UnavailableDayRefRepositoryPort;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper.ProfessionalRefMapper;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper.UnavailableDayRefMapper;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.repository.UnavailableDayRefJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UnavailableDayRefPersistenceAdapter implements UnavailableDayRefRepositoryPort {

    private final UnavailableDayRefJpaRepository unavailableDayRefJpaRepository;
    private final UnavailableDayRefMapper unavailableDayRefMapper;
    private final ProfessionalRefMapper professionalRefMapper;

    @Override
    public List<UnavailableDayRef> findByProfessionalRef(ProfessionalRef professionalRef) {
        return unavailableDayRefJpaRepository
                .findByProfessionalRefId(professionalRef.getCodProf())
                .stream()
                .map(unavailableDayRefMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public UnavailableDayRef save(UnavailableDayRef day) {
        return unavailableDayRefMapper.toDomain(
                unavailableDayRefJpaRepository.save(unavailableDayRefMapper.toEntity(day)));
    }

    @Override
    public void deleteAllByProfessionalRef(ProfessionalRef professionalRef) {
        unavailableDayRefJpaRepository.deleteAllByProfessionalRef(
                professionalRefMapper.toEntity(professionalRef));
    }

    @Override
    public boolean existsById(Long id) {
        return unavailableDayRefJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        unavailableDayRefJpaRepository.deleteById(id);
    }
}