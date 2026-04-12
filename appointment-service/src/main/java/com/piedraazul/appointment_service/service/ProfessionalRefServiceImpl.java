package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.model.ProfessionalRef;
import com.piedraazul.appointment_service.repository.ProfessionalRefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessionalRefServiceImpl implements ProfessionalRefService {

    private final ProfessionalRefRepository professionalRefRepository;

    @Override
    public ProfessionalRef save(ProfessionalRef professionalRef) {
        return professionalRefRepository.save(professionalRef);
    }

    @Override
    public boolean existsById(Long codProf) {
        return professionalRefRepository.existsById(codProf);
    }
}