package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.model.PatientRef;

public interface PatientRefService {
    PatientRef save(PatientRef patientRef);
    boolean existsById(Long codPatient);
}