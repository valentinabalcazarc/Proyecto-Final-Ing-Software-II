package services;

import java.util.List;
import models.Patient;

public interface PatientService {
    boolean regPatient(Patient newPatient);
    List<Patient> getAllPatients();
    Patient findByCed(int cedPatient);
}
