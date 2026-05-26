package com.piedraazul.app_client.services;

import com.piedraazul.app_client.dto.UpdateProfessionalDTO;
import com.piedraazul.app_client.enums.SpecialityProfEnum;
import java.util.List;
import com.piedraazul.app_client.models.Professional;

public interface ProfessionalService {
    List<Professional> getAllProfessionals();

    Professional findByCod(int codProf);

    List<Professional> getAllProfessionalsBySpeciality(SpecialityProfEnum speciality);

    boolean register(Professional professional);

    boolean updateSchedule(Long codProf, UpdateProfessionalDTO dto);
}