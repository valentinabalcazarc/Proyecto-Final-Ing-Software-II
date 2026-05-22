package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.repository;

import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.ProfessionalRefEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionalRefJpaRepository extends JpaRepository<ProfessionalRefEntity, Long> {
    List<ProfessionalRefEntity> findBySpecialityProf(SpecialityProfEnum specialityProf);
}
