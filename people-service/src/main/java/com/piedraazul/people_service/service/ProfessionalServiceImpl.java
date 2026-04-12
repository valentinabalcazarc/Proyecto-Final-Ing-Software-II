package com.piedraazul.people_service.service;

import com.piedraazul.people_service.dto.ProfessionalDTO;
import com.piedraazul.people_service.dto.UpdateProfessionalDTO;
import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.enums.StatusProfEnum;
import com.piedraazul.people_service.messaging.PeopleEventPublisher;
import com.piedraazul.people_service.model.Professional;
import com.piedraazul.people_service.model.UserRef;
import com.piedraazul.people_service.repository.ProfessionalRepository;
import com.piedraazul.people_service.repository.UserRefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final UserRefRepository userRefRepository;
    private final PeopleEventPublisher peopleEventPublisher;

    @Override
    public Professional register(ProfessionalDTO dto) {
        UserRef userRef = userRefRepository.findById(dto.getCodUser())
                .orElseThrow(() -> new RuntimeException("No existe un usuario con ese código"));
        if (professionalRepository.existsByUserRef(userRef)) {
            throw new RuntimeException("Ya existe un profesional con ese usuario");
        }
        Professional prof = new Professional();
        prof.setUserRef(userRef);
        prof.setGenProf(dto.getGenProf());
        prof.setPhoneProf(dto.getPhoneProf());
        prof.setTypeProf(dto.getTypeProf());
        prof.setSpecialityProf(dto.getSpecialityProf());
        prof.setAttentionInterval(dto.getAttentionInterval());
        prof.setStatusProf(StatusProfEnum.Active);
        Professional saved = professionalRepository.save(prof);
        peopleEventPublisher.publishProfessionalRegistered(saved);
        return saved;
    }

    @Override
    public Optional<Professional> findByCodUser(Long codUser) {
        UserRef userRef = userRefRepository.findById(codUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + codUser));
        return professionalRepository.findByUserRef(userRef);
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
    public Professional update(Long id, UpdateProfessionalDTO dto) {
        Professional prof = professionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));
        if (dto.getGenProf() != null) prof.setGenProf(dto.getGenProf());
        if (dto.getPhoneProf() != null) prof.setPhoneProf(dto.getPhoneProf());
        if (dto.getTypeProf() != null) prof.setTypeProf(dto.getTypeProf());
        if (dto.getSpecialityProf() != null) prof.setSpecialityProf(dto.getSpecialityProf());
        if (dto.getAttentionInterval() != null) prof.setAttentionInterval(dto.getAttentionInterval());
        Professional updated = professionalRepository.save(prof);
        peopleEventPublisher.publishProfessionalUpdated(updated);
        return updated;
    }

    @Override
    public void deactivate(Long id) {
        Professional prof = professionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));
        prof.setStatusProf(StatusProfEnum.Inactive);
        professionalRepository.save(prof);
    }
}