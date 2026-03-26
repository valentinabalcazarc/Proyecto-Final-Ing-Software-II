package repository;

import enums.SpecialityProfEnum;
import models.Professional;
import java.util.List;

public interface ProfessionalRepository {

    void save(Professional professional);
    Professional findById(int codProf);
    List<Professional> findAll();
    void update(Professional professional);
    void delete(int codProf);
    List<Professional> findAllBySpeciality(SpecialityProfEnum speciality);
}
