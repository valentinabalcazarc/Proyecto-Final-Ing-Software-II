package com.piedraazul.appointment_service.repository;

import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.model.ProfessionalRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionalRefRepository extends JpaRepository<ProfessionalRef, Long> {
    List<ProfessionalRef> findBySpecialityProf(SpecialityProfEnum specialityProf);
}