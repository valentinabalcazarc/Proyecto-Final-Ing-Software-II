package com.piedraazul.appointment_service.repository;

import com.piedraazul.appointment_service.model.UnavailableDayRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnavailableDayRefRepository extends JpaRepository<UnavailableDayRef, Long> {
}
