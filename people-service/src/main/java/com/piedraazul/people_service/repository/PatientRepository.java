package com.piedraazul.people_service.repository;

import com.piedraazul.people_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByIdPatient(Long idPatient);
    boolean existsByIdPatient(Long idPatient);
}