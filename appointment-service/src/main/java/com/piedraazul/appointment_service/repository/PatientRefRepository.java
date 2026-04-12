package com.piedraazul.appointment_service.repository;

import com.piedraazul.appointment_service.model.PatientRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRefRepository extends JpaRepository<PatientRef, Long> {
}