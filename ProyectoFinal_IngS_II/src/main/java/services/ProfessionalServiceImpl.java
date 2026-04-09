package services;

import enums.SpecialityProfEnum;
import java.util.List;
import models.Professional;
import repository.ProfessionalRepository;

public class ProfessionalServiceImpl implements ProfessionalService{
    
    private final ProfessionalRepository professionalRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    
    @Override
    public List<Professional> getAllProfessionals() {
        return professionalRepository.findAll();
    }
    
    @Override
    public Professional findByCod(int codProf){
        return professionalRepository.findById(codProf);
    }
    
    @Override
    public List<Professional> getAllProfessionalsBySpeciality(SpecialityProfEnum speciality){
        return professionalRepository.findAllBySpeciality(speciality);
    }
}
