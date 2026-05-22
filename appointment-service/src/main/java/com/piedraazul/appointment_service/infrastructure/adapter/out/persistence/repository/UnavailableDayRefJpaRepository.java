package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.repository;

import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.ProfessionalRefEntity;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.UnavailableDayRefEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnavailableDayRefJpaRepository extends JpaRepository<UnavailableDayRefEntity, Long> {
    List<UnavailableDayRefEntity> findByProfessionalRef(ProfessionalRefEntity professionalRef);
    void deleteAllByProfessionalRef(ProfessionalRefEntity professionalRef);
}
