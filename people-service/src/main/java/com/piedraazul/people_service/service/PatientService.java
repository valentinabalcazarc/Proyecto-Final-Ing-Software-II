package com.piedraazul.people_service.service;

import com.piedraazul.people_service.dto.PatientDTO;
import com.piedraazul.people_service.model.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient register(PatientDTO dto);
    Optional<Patient> findByIdPatient(Long idPatient);
    List<Patient> findAll();
    Patient update(Long id, PatientDTO dto);
    void delete(Long id);
}