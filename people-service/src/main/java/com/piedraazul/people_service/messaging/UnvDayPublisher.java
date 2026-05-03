package com.piedraazul.people_service.messaging;

import com.piedraazul.people_service.dto.UnavailableDayEventDTO;
import com.piedraazul.people_service.model.UnavailableDay;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnvDayPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishUnavailableDayCreated(UnavailableDay unavailableDay) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.UNAVAILABLE_DAY_QUEUE, toDTO(unavailableDay));
    }

    public void publishUnavailableDayDeleted(UnavailableDay unavailableDay) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.UNAVAILABLE_DAY_DELETED_QUEUE, toDTO(unavailableDay));
    }

    private UnavailableDayEventDTO toDTO(UnavailableDay day) {
        return new UnavailableDayEventDTO(
                day.getId(),
                day.getProfessional().getCodProf(),
                day.getDate(),
                day.getReason()
        );
    }
}