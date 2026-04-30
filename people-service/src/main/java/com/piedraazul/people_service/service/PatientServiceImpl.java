package com.piedraazul.people_service.service;

import com.piedraazul.people_service.dto.PatientDTO;
import com.piedraazul.people_service.dto.UpdatePatientDTO;
import com.piedraazul.people_service.messaging.PeopleEventPublisher;
import com.piedraazul.people_service.model.Patient;
import com.piedraazul.people_service.model.Professional;
import com.piedraazul.people_service.model.UserRef;
import com.piedraazul.people_service.repository.PatientRepository;
import com.piedraazul.people_service.repository.UserRefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRefRepository userRefRepository;
    private final PeopleEventPublisher peopleEventPublisher;

    @Override
    public Patient register(PatientDTO dto) {
        if (patientRepository.existsByIdPatient(dto.getIdPatient())) {
            throw new RuntimeException("Ya existe un paciente con esa identificación");
        }

        Patient patient = new Patient();
        patient.setIdPatient(dto.getIdPatient());
        patient.setNamePatient(dto.getNamePatient());
        patient.setSecondNamePatient(dto.getSecondNamePatient());
        patient.setLastNamePatient(dto.getLastNamePatient());
        patient.setSecondLastNamePatient(dto.getSecondLastNamePatient());
        patient.setPhonePatient(dto.getPhonePatient());
        patient.setDateBirthPatient(dto.getDateBirthPatient());
        patient.setGenderPatient(dto.getGenderPatient());

        Patient saved = patientRepository.save(patient);
        peopleEventPublisher.publishPatientRegistered(saved);
        return saved;
    }

    @Override
    public Optional<Patient> findByIdPatient(Long idPatient) {
        return patientRepository.findByIdPatient(idPatient);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient update(Long id, UpdatePatientDTO dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        if (dto.getNamePatient() != null) patient.setNamePatient(dto.getNamePatient());
        if (dto.getSecondNamePatient() != null) patient.setSecondNamePatient(dto.getSecondNamePatient());
        if (dto.getLastNamePatient() != null) patient.setLastNamePatient(dto.getLastNamePatient());
        if (dto.getSecondLastNamePatient() != null) patient.setSecondLastNamePatient(dto.getSecondLastNamePatient());
        if (dto.getPhonePatient() != null) patient.setPhonePatient(dto.getPhonePatient());
        if (dto.getDateBirthPatient() != null) patient.setDateBirthPatient(dto.getDateBirthPatient());
        if (dto.getGenderPatient() != null) patient.setGenderPatient(dto.getGenderPatient());
        Patient updated = patientRepository.save(patient);
        peopleEventPublisher.publishPatientUpdated(updated);
        return updated;
    }

    @Override
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }
}