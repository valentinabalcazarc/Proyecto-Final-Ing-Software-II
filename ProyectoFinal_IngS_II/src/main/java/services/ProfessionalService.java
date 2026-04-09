package services;

import enums.SpecialityProfEnum;
import java.util.List;
import models.Professional;

public interface ProfessionalService {
    List<Professional> getAllProfessionals();
    Professional findByCod(int codProf);
    List<Professional> getAllProfessionalsBySpeciality(SpecialityProfEnum speciality);
}
