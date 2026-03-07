package services;

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
}
