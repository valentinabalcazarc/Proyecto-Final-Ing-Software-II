package com.piedraazul.appointment_service.domain.port.out;

import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRefRepositoryPort {
    Optional<ProfessionalRef> findById(Long id);
    ProfessionalRef save(ProfessionalRef professionalRef);
    boolean existsById(Long id);
    List<ProfessionalRef> findAll();
    List<ProfessionalRef> findBySpecialityProf(SpecialityProfEnum speciality);
}
