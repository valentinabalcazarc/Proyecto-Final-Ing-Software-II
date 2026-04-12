package com.piedraazul.people_service.repository;

import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    Optional<Professional> findByCodUser(Long codUser);
    List<Professional> findBySpecialityProf(SpecialityProfEnum speciality);
    boolean existsByCodUser(Long codUser);
}