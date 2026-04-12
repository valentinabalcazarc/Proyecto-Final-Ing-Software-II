package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.model.ProfessionalRef;

public interface ProfessionalRefService {
    ProfessionalRef save(ProfessionalRef professionalRef);
    boolean existsById(Long codProf);
}