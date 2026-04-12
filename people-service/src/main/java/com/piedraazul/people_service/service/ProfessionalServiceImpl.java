package com.piedraazul.people_service.service;

import com.piedraazul.people_service.dto.ProfessionalDTO;
import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.enums.StatusProfEnum;
import com.piedraazul.people_service.model.Professional;
import com.piedraazul.people_service.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    @Override
    public Professional register(ProfessionalDTO dto) {
        if (professionalRepository.existsByCodUser(dto.getCodUser())) {
            throw new RuntimeException("Ya existe un profesional con ese usuario");
        }
        Professional prof = new Professional();
        prof.setCodUser(dto.getCodUser());
        prof.setGenProf(dto.getGenProf());
        prof.setPhoneProf(dto.getPhoneProf());
        prof.setTypeProf(dto.getTypeProf());
        prof.setSpecialityProf(dto.getSpecialityProf());
        prof.setAttentionInterval(dto.getAttentionInterval());
        prof.setStatusProf(StatusProfEnum.Active);
        return professionalRepository.save(prof);
    }

    @Override
    public Optional<Professional> findByCodUser(Long codUser) {
        return professionalRepository.findByCodUser(codUser);
    }

    @Override
    public List<Professional> findAll() {
        return professionalRepository.findAll();
    }

    @Override
    public List<Professional> findBySpeciality(SpecialityProfEnum speciality) {
        return professionalRepository.findBySpecialityProf(speciality);
    }

    @Override
    public Professional update(Long id, ProfessionalDTO dto) {
        Professional prof = professionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));
        if (dto.getGenProf() != null) prof.setGenProf(dto.getGenProf());
        if (dto.getPhoneProf() != null) prof.setPhoneProf(dto.getPhoneProf());
        if (dto.getTypeProf() != null) prof.setTypeProf(dto.getTypeProf());
        if (dto.getSpecialityProf() != null) prof.setSpecialityProf(dto.getSpecialityProf());
        if (dto.getAttentionInterval() != null) prof.setAttentionInterval(dto.getAttentionInterval());
        return professionalRepository.save(prof);
    }

    @Override
    public void deactivate(Long id) {
        Professional prof = professionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));
        prof.setStatusProf(StatusProfEnum.Inactive);
        professionalRepository.save(prof);
    }
}