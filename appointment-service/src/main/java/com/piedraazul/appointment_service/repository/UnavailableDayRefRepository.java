package com.piedraazul.appointment_service.repository;

import com.piedraazul.appointment_service.model.ProfessionalRef;
import com.piedraazul.appointment_service.model.UnavailableDayRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnavailableDayRefRepository extends JpaRepository<UnavailableDayRef, Long> {
    List<UnavailableDayRef> findByProfessionalRef(ProfessionalRef professionalRef);
}
