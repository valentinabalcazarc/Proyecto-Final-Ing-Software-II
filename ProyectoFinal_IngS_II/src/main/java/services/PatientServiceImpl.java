package services;

import java.util.List;
import models.Patient;
import repository.PatientRepository;


public class PatientServiceImpl implements PatientService{
    
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    
    @Override
    public boolean regPatient(Patient newPatient){
        

        if (patientRepository.findByCedPatient(newPatient.getIdPatient()) != null) {
            return false;
        }

        return patientRepository.save(newPatient);
    }
    
    @Override
    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }
    
    @Override
    public Patient findByCed(int cedPatient){
        return patientRepository.findByCedPatient(cedPatient);
    }
    
}
