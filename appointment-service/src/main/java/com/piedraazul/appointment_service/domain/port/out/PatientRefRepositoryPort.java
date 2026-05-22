package com.piedraazul.appointment_service.domain.port.out;

import com.piedraazul.appointment_service.domain.model.PatientRef;

import java.util.Optional;

public interface PatientRefRepositoryPort {
    Optional<PatientRef> findById(Long id);
    PatientRef save(PatientRef patientRef);
    boolean existsById(Long id);
}
