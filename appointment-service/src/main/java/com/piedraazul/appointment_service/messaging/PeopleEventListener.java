package com.piedraazul.appointment_service.messaging;

import com.piedraazul.appointment_service.dto.ProfessionalEventDTO;
import com.piedraazul.appointment_service.dto.PatientEventDTO;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.TypeProfEnum;
import com.piedraazul.appointment_service.model.PatientRef;
import com.piedraazul.appointment_service.model.ProfessionalRef;
import com.piedraazul.appointment_service.repository.PatientRefRepository;
import com.piedraazul.appointment_service.repository.ProfessionalRefRepository;
import com.piedraazul.appointment_service.service.PatientRefService;
import com.piedraazul.appointment_service.service.ProfessionalRefService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PeopleEventListener {

    private final PatientRefService patientRefService;
    private final ProfessionalRefService professionalRefService;
    private final PatientRefRepository patientRefRepository;
    private final ProfessionalRefRepository professionalRefRepository;

    @RabbitListener(queues = RabbitMQConfig.PATIENT_QUEUE)
    public void handlePatientRegistered(PatientEventDTO dto) {
        try {
            if (patientRefService.existsById(dto.getCodPatient())) return;

            PatientRef ref = new PatientRef();
            ref.setCodPatient(dto.getCodPatient());
            ref.setIdPatient(dto.getIdPatient());
            ref.setNamePatient(buildFullName(dto.getNamePatient(), dto.getSecondNamePatient()));
            ref.setLastNamePatient(buildFullName(dto.getLastNamePatient(), dto.getSecondLastNamePatient()));
            ref.setPhonePatient(dto.getPhonePatient());
            ref.setGenderPatient(dto.getGenderPatient());
            patientRefService.save(ref);
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
            patientRefService.save(ref);
        } catch (Exception e) {
            System.err.println("Error actualizando patient_ref: " + e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PROFESSIONAL_QUEUE)
    public void handleProfessionalRegistered(ProfessionalEventDTO dto) {
        try {
            if (professionalRefService.existsById(dto.getCodProf())) return;

            ProfessionalRef ref = new ProfessionalRef();
            ref.setCodProf(dto.getCodProf());
            ref.setNameProf(dto.getNameProf());
            ref.setLastNameProf(dto.getLastNameProf());
            ref.setSpecialityProf(SpecialityProfEnum.valueOf(dto.getSpecialityProf()));
            ref.setTypeProf(TypeProfEnum.valueOf(dto.getTypeProf()));
            ref.setAttentionInterval(dto.getAttentionInterval() != null ? dto.getAttentionInterval() : 30);
            professionalRefService.save(ref);
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
            if (dto.getAttentionInterval() != null) ref.setAttentionInterval(dto.getAttentionInterval());
            professionalRefService.save(ref);
        } catch (Exception e) {
            System.err.println("Error actualizando professional_ref: " + e.getMessage());
        }
    }

    private String buildFullName(String first, String second) {
        if (second == null || second.isBlank()) return first != null ? first : "";
        return (first + " " + second).trim();
    }
}
