package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.repository;

import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.ProfessionalRefEntity;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.UnavailableDayRefEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnavailableDayRefJpaRepository extends JpaRepository<UnavailableDayRefEntity, Long> {

    List<UnavailableDayRefEntity> findByProfessionalRef(ProfessionalRefEntity professionalRef);

    @Query("SELECT u FROM UnavailableDayRefEntity u WHERE u.professionalRef.codProf = :codProf")
    List<UnavailableDayRefEntity> findByProfessionalRefId(@Param("codProf") Long codProf);

    void deleteAllByProfessionalRef(ProfessionalRefEntity professionalRef);

    @Query("SELECT u FROM UnavailableDayRefEntity u WHERE u.professionalRef IS NULL")
    List<UnavailableDayRefEntity> findGlobalUnavailableDays();
}