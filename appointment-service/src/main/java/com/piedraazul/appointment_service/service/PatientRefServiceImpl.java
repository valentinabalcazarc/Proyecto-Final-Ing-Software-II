package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.model.PatientRef;
import com.piedraazul.appointment_service.repository.PatientRefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientRefServiceImpl implements PatientRefService {

    private final PatientRefRepository patientRefRepository;

    @Override
    public PatientRef save(PatientRef patientRef) {
        return patientRefRepository.save(patientRef);
    }

    @Override
    public boolean existsById(Long codPatient) {
        return patientRefRepository.existsById(codPatient);
    }
}