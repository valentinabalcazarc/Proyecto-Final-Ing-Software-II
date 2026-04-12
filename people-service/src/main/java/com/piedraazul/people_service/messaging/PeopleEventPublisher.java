package com.piedraazul.people_service.messaging;

import com.piedraazul.people_service.model.Patient;
import com.piedraazul.people_service.model.Professional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PeopleEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishPatientRegistered(Patient patient) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PATIENT_QUEUE, patient);
    }

    public void publishPatientUpdated(Patient patient) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PATIENT_UPDATED_QUEUE, patient);
    }

    public void publishProfessionalRegistered(Professional professional) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PROFESSIONAL_QUEUE, professional);
    }

    public void publishProfessionalUpdated(Professional professional) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PROFESSIONAL_UPDATED_QUEUE, professional);
    }
}