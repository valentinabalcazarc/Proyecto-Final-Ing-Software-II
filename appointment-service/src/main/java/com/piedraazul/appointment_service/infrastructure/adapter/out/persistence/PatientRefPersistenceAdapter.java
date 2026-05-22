package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence;

import com.piedraazul.appointment_service.domain.model.PatientRef;
import com.piedraazul.appointment_service.domain.port.out.PatientRefRepositoryPort;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper.PatientRefMapper;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.repository.PatientRefJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PatientRefPersistenceAdapter implements PatientRefRepositoryPort {

    private final PatientRefJpaRepository patientRefJpaRepository;
    private final PatientRefMapper patientRefMapper;

    @Override
    public Optional<PatientRef> findById(Long id) {
        return patientRefJpaRepository.findById(id).map(patientRefMapper::toDomain);
    }

    @Override
    public PatientRef save(PatientRef patientRef) {
        return patientRefMapper.toDomain(patientRefJpaRepository.save(patientRefMapper.toEntity(patientRef)));
    }

    @Override
    public boolean existsById(Long id) {
        return patientRefJpaRepository.existsById(id);
    }
}
