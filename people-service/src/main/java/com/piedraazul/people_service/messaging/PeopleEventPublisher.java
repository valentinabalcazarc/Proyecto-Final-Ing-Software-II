package com.piedraazul.people_service.messaging;

import com.piedraazul.people_service.dto.PatientEventDTO;
import com.piedraazul.people_service.dto.ProfessionalEventDTO;
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
        rabbitTemplate.convertAndSend(RabbitMQConfig.PATIENT_QUEUE, toDTO(patient));
    }

    public void publishPatientUpdated(Patient patient) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PATIENT_UPDATED_QUEUE, toDTO(patient));
    }

    public void publishProfessionalRegistered(Professional professional) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PROFESSIONAL_QUEUE, toDTO(professional));
    }

    public void publishProfessionalUpdated(Professional professional) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PROFESSIONAL_UPDATED_QUEUE, toDTO(professional));
    }

    private PatientEventDTO toDTO(Patient patient) {
        return new PatientEventDTO(
                patient.getCodPatient(),
                patient.getIdPatient(),
                patient.getNamePatient(),
                patient.getSecondNamePatient(),
                patient.getLastNamePatient(),
                patient.getSecondLastNamePatient(),
                patient.getPhonePatient(),
                patient.getGenderPatient()
        );
    }

    private ProfessionalEventDTO toDTO(Professional professional) {
        String name = "";
        String lastName = "";
        if (professional.getUserRef() != null) {
            name = professional.getUserRef().getNameUser() != null
                    ? professional.getUserRef().getNameUser() : "";
            lastName = professional.getUserRef().getLastNameUser() != null
                    ? professional.getUserRef().getLastNameUser() : "";
        }
        return new ProfessionalEventDTO(
                professional.getCodProf(),
                name,
                lastName,
                professional.getSpecialityProf().name(),
                professional.getTypeProf().name(),
                professional.getAttentionInterval() != null ? professional.getAttentionInterval() : 30
        );
    }
}