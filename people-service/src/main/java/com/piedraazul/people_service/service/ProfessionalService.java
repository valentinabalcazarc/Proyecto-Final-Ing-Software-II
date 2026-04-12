package com.piedraazul.people_service.service;

import com.piedraazul.people_service.dto.ProfessionalDTO;
import com.piedraazul.people_service.dto.UpdateProfessionalDTO;
import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.model.Professional;
import java.util.List;
import java.util.Optional;

public interface ProfessionalService {
    Professional register(ProfessionalDTO dto);
    Optional<Professional> findByCodUser(Long codUser);
    List<Professional> findAll();
    List<Professional> findBySpeciality(SpecialityProfEnum speciality);
    Professional update(Long id, UpdateProfessionalDTO dto);
    void deactivate(Long id);
}