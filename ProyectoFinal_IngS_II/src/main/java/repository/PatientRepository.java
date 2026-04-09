package repository;

import java.util.List;
import models.Patient;


public interface PatientRepository {
    boolean save(Patient patient);

    Patient findById(int codPatient);

    Patient findByCedPatient(int cedPatient);

    List<Patient> findAll();
}
