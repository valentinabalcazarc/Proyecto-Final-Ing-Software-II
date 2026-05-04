package com.piedraazul.people_service.repository;

import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.model.Professional;
import com.piedraazul.people_service.model.UnavailableDay;
import com.piedraazul.people_service.model.UserRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    Optional<Professional> findByUserRef(UserRef userRef);
    List<Professional> findBySpecialityProf(SpecialityProfEnum speciality);
    boolean existsByUserRef(UserRef userRef);

    @Query("SELECT u FROM UnavailableDay u WHERE u.professional.userRef = :userRef")
    List<UnavailableDay> findUnavailableDaysByUserRef(@Param("userRef") UserRef userRef);
}