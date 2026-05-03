package com.piedraazul.people_service.service;

import com.piedraazul.people_service.dto.UnavailableDayDTO;
import com.piedraazul.people_service.messaging.UnvDayPublisher;
import com.piedraazul.people_service.model.Professional;
import com.piedraazul.people_service.model.UnavailableDay;
import com.piedraazul.people_service.repository.ProfessionalRepository;
import com.piedraazul.people_service.repository.UnavailableDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnavailableDayServiceImpl implements UnavailableDayService {

    private final UnavailableDayRepository unavailableDayRepository;
    private final ProfessionalRepository professionalRepository;
    private final UnvDayPublisher unvDayPublisher;

    @Override
    public UnavailableDay create(UnavailableDayDTO dto) {
        Professional professional = professionalRepository.findById(dto.getCodProf())
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado con código: " + dto.getCodProf()));

        if (unavailableDayRepository.existsByProfessionalAndDate(professional, dto.getDate())) {
            throw new RuntimeException("Ya existe un día no laborable registrado para esa fecha");
        }

        UnavailableDay day = new UnavailableDay();
        day.setProfessional(professional);
        day.setDate(dto.getDate());
        day.setReason(dto.getReason());

        UnavailableDay saved = unavailableDayRepository.save(day);
        unvDayPublisher.publishUnavailableDayCreated(saved);
        return saved;
    }

    @Override
    public List<UnavailableDay> findByProfessional(Long codProf) {
        Professional professional = professionalRepository.findById(codProf)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado con código: " + codProf));
        return unavailableDayRepository.findByProfessional(professional);
    }

    @Override
    public boolean delete(Long id) {
        UnavailableDay day = unavailableDayRepository.findById(id).orElse(null);
        if (day == null) return false;
        unavailableDayRepository.delete(day);
        unvDayPublisher.publishUnavailableDayDeleted(day);
        return true;
    }
}