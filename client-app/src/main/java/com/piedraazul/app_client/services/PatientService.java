package com.piedraazul.app_client.services;

import com.piedraazul.app_client.models.Patient;
import java.util.List;

public interface PatientService {
    boolean regPatient(Patient newPatient);
    List<Patient> getAllPatients();
    Patient findByCed(Long idPatient);
}