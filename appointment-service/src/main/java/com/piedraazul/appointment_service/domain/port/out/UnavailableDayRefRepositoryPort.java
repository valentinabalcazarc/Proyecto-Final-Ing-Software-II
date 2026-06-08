package com.piedraazul.appointment_service.domain.port.out;

import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.domain.model.UnavailableDayRef;

import java.util.List;

public interface UnavailableDayRefRepositoryPort {
    List<UnavailableDayRef> findByProfessionalRef(ProfessionalRef professionalRef);
    UnavailableDayRef save(UnavailableDayRef day);
    void deleteAllByProfessionalRef(ProfessionalRef professionalRef);
    boolean existsById(Long id);
    void deleteById(Long id);
}
