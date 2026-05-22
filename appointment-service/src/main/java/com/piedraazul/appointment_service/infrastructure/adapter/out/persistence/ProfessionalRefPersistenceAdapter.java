package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence;

import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.domain.port.out.ProfessionalRefRepositoryPort;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper.ProfessionalRefMapper;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.repository.ProfessionalRefJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfessionalRefPersistenceAdapter implements ProfessionalRefRepositoryPort {

    private final ProfessionalRefJpaRepository professionalRefJpaRepository;
    private final ProfessionalRefMapper professionalRefMapper;

    @Override
    public Optional<ProfessionalRef> findById(Long id) {
        return professionalRefJpaRepository.findById(id).map(professionalRefMapper::toDomain);
    }

    @Override
    public List<ProfessionalRef> findAll() {
        return professionalRefJpaRepository.findAll().stream()
                .map(professionalRefMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<ProfessionalRef> findBySpecialityProf(SpecialityProfEnum speciality) {
        return professionalRefJpaRepository.findBySpecialityProf(speciality).stream()
                .map(professionalRefMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public ProfessionalRef save(ProfessionalRef professionalRef) {
        return professionalRefMapper.toDomain(professionalRefJpaRepository.save(professionalRefMapper.toEntity(professionalRef)));
    }

    @Override
    public boolean existsById(Long id) {
        return professionalRefJpaRepository.existsById(id);
    }
}
