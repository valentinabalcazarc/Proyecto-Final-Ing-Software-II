package com.piedraazul.appointment_service.infrastructure.adapter.in.messaging;

import com.piedraazul.appointment_service.application.dto.UnavailableDayEventDTO;
import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.domain.model.UnavailableDayRef;
import com.piedraazul.appointment_service.domain.port.out.ProfessionalRefRepositoryPort;
import com.piedraazul.appointment_service.domain.port.out.UnavailableDayRefRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnvDayListener {

    private final UnavailableDayRefRepositoryPort unavailableDayRefRepository;
    private final ProfessionalRefRepositoryPort professionalRefRepository;

    @RabbitListener(queues = RabbitMQConfig.UNAVAILABLE_DAY_QUEUE)
    public void handleUnavailableDayCreated(UnavailableDayEventDTO dto) {
        try {
            if (unavailableDayRefRepository.existsById(dto.getId())) return;

            ProfessionalRef professionalRef = professionalRefRepository.findById(dto.getCodProf()).orElse(null);
            if (professionalRef == null) {
                System.err.println("ProfessionalRef no encontrado para codProf: " + dto.getCodProf());
                return;
            }

            UnavailableDayRef ref = UnavailableDayRef.builder()
                    .id(dto.getId())
                    .professionalRef(professionalRef)
                    .date(dto.getDate())
                    .reason(dto.getReason())
                    .build();
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
