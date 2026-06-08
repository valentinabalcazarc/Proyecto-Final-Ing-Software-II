package com.piedraazul.appointment_service.infrastructure.adapter.in.messaging;

import com.piedraazul.appointment_service.application.dto.ProfessionalEventDTO;
import com.piedraazul.appointment_service.application.dto.PatientEventDTO;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.TypeProfEnum;
import com.piedraazul.appointment_service.domain.model.PatientRef;
import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.domain.port.out.PatientRefRepositoryPort;
import com.piedraazul.appointment_service.domain.port.out.ProfessionalRefRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PeopleEventListener {

    private final PatientRefRepositoryPort patientRefRepository;
    private final ProfessionalRefRepositoryPort professionalRefRepository;

    @RabbitListener(queues = RabbitMQConfig.PATIENT_QUEUE)
    public void handlePatientRegistered(PatientEventDTO dto) {
        try {
            if (patientRefRepository.existsById(dto.getCodPatient())) return;

            PatientRef ref = PatientRef.builder()
                    .codPatient(dto.getCodPatient())
                    .idPatient(dto.getIdPatient())
                    .namePatient(buildFullName(dto.getNamePatient(), dto.getSecondNamePatient()))
                    .lastNamePatient(buildFullName(dto.getLastNamePatient(), dto.getSecondLastNamePatient()))
                    .phonePatient(dto.getPhonePatient())
                    .genderPatient(dto.getGenderPatient())
                    .build();
            patientRefRepository.save(ref);
        } catch (Exception e) {
            System.err.println("Error registrando patient_ref: " + e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PATIENT_UPDATED_QUEUE)
    public void handlePatientUpdated(PatientEventDTO dto) {
        try {
            PatientRef ref = patientRefRepository.findById(dto.getCodPatient()).orElse(null);
            if (ref == null) return;

            ref.setNamePatient(buildFullName(dto.getNamePatient(), dto.getSecondNamePatient()));
            ref.setLastNamePatient(buildFullName(dto.getLastNamePatient(), dto.getSecondLastNamePatient()));
            if (dto.getPhonePatient() != null) ref.setPhonePatient(dto.getPhonePatient());
            if (dto.getGenderPatient() != null) ref.setGenderPatient(dto.getGenderPatient());
            patientRefRepository.save(ref);
        } catch (Exception e) {
            System.err.println("Error actualizando patient_ref: " + e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PROFESSIONAL_QUEUE)
    public void handleProfessionalRegistered(ProfessionalEventDTO dto) {
        try {
            if (professionalRefRepository.existsById(dto.getCodProf())) return;

            ProfessionalRef ref = ProfessionalRef.builder()
                    .codProf(dto.getCodProf())
                    .nameProf(dto.getNameProf())
                    .lastNameProf(dto.getLastNameProf())
                    .specialityProf(SpecialityProfEnum.valueOf(dto.getSpecialityProf()))
                    .typeProf(TypeProfEnum.valueOf(dto.getTypeProf()))
                    .arrivalTime(dto.getArrivalTime())
                    .departureTime(dto.getDepartureTime())
                    .attentionInterval(dto.getAttentionInterval() != null ? dto.getAttentionInterval() : 30)
                    .unavailableDays(dto.getUnavailableDays())
                    .build();
            professionalRefRepository.save(ref);
        } catch (Exception e) {
            System.err.println("Error registrando professional_ref: " + e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PROFESSIONAL_UPDATED_QUEUE)
    public void handleProfessionalUpdated(ProfessionalEventDTO dto) {
        try {
            ProfessionalRef ref = professionalRefRepository.findById(dto.getCodProf()).orElse(null);
            if (ref == null) return;

            if (dto.getNameProf() != null) ref.setNameProf(dto.getNameProf());
            if (dto.getLastNameProf() != null) ref.setLastNameProf(dto.getLastNameProf());
            if (dto.getSpecialityProf() != null) ref.setSpecialityProf(SpecialityProfEnum.valueOf(dto.getSpecialityProf()));
            if (dto.getTypeProf() != null) ref.setTypeProf(TypeProfEnum.valueOf(dto.getTypeProf()));
            if (dto.getArrivalTime() != null) ref.setArrivalTime(dto.getArrivalTime());
            if (dto.getDepartureTime() != null) ref.setDepartureTime(dto.getDepartureTime());
            if (dto.getAttentionInterval() != null) ref.setAttentionInterval(dto.getAttentionInterval());
            if (dto.getUnavailableDays() != null) ref.setUnavailableDays(dto.getUnavailableDays());
            professionalRefRepository.save(ref);
        } catch (Exception e) {
            System.err.println("Error actualizando professional_ref: " + e.getMessage());
        }
    }

    private String buildFullName(String first, String second) {
        if (second == null || second.isBlank()) return first != null ? first : "";
        return (first + " " + second).trim();
    }
}
