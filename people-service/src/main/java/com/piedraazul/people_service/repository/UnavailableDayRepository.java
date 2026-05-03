package com.piedraazul.people_service.repository;

import com.piedraazul.people_service.model.Professional;
import com.piedraazul.people_service.model.UnavailableDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnavailableDayRepository extends JpaRepository<UnavailableDay, Long> {
    List<UnavailableDay> findByProfessional(Professional professional);
    boolean existsByProfessionalAndDate(Professional professional, java.time.LocalDate date);
}