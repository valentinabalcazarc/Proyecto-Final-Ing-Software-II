package com.piedraazul.appointment_service.messaging;

import com.piedraazul.appointment_service.dto.UnavailableDayEventDTO;
import com.piedraazul.appointment_service.model.ProfessionalRef;
import com.piedraazul.appointment_service.model.UnavailableDayRef;
import com.piedraazul.appointment_service.repository.ProfessionalRefRepository;
import com.piedraazul.appointment_service.repository.UnavailableDayRefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnvDayListener {

    private final UnavailableDayRefRepository unavailableDayRefRepository;
    private final ProfessionalRefRepository professionalRefRepository;

    @RabbitListener(queues = RabbitMQConfig.UNAVAILABLE_DAY_QUEUE)
    public void handleUnavailableDayCreated(UnavailableDayEventDTO dto) {
        try {
            if (unavailableDayRefRepository.existsById(dto.getId())) return;

            ProfessionalRef professionalRef = professionalRefRepository.findById(dto.getCodProf()).orElse(null);
            if (professionalRef == null) {
                System.err.println("ProfessionalRef no encontrado para codProf: " + dto.getCodProf());
                return;
            }

            UnavailableDayRef ref = new UnavailableDayRef();
            ref.setId(dto.getId());
            ref.setProfessionalRef(professionalRef);
            ref.setDate(dto.getDate());
            ref.setReason(dto.getReason());
            unavailableDayRefRepository.save(ref);
        } catch (Exception e) {
            System.err.println("Error registrando unavailable_day_ref: " + e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.UNAVAILABLE_DAY_DELETED_QUEUE)
    public void handleUnavailableDayDeleted(UnavailableDayEventDTO dto) {
        try {
            unavailableDayRefRepository.deleteById(dto.getId());
        } catch (Exception e) {
            System.err.println("Error eliminando unavailable_day_ref: " + e.getMessage());
        }
    }
}