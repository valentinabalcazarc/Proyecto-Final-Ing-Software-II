package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.repository;

import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.PatientRefEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRefJpaRepository extends JpaRepository<PatientRefEntity, Long> {
}
