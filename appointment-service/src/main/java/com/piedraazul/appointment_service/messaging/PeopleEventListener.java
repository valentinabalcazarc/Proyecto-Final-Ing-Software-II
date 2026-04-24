package com.piedraazul.appointment_service.messaging;

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
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PeopleEventListener {

    private final PatientRefService patientRefService;
    private final ProfessionalRefService professionalRefService;
    private final PatientRefRepository patientRefRepository;
    private final ProfessionalRefRepository professionalRefRepository;

    @RabbitListener(queues = RabbitMQConfig.PATIENT_QUEUE)
    public void handlePatientRegistered(Map<String, Object> message) {
        try {
            PatientRef ref = new PatientRef();
            ref.setCodPatient(Long.valueOf(message.get("codPatient").toString()));
            ref.setIdPatient(Long.valueOf(message.get("idPatient").toString()));

            String name = message.get("namePatient") != null ? message.get("namePatient").toString() : "";
            String secondName = message.get("secondNamePatient") != null ? message.get("secondNamePatient").toString() : "";
            ref.setNamePatient((name + " " + secondName).trim());

            String lastName = message.get("lastNamePatient") != null ? message.get("lastNamePatient").toString() : "";
            String secondLastName = message.get("secondLastNamePatient") != null ? message.get("secondLastNamePatient").toString() : "";
            ref.setLastNamePatient((lastName + " " + secondLastName).trim());

            ref.setPhonePatient(message.get("phonePatient") != null ? Long.valueOf(message.get("phonePatient").toString()) : null);
            ref.setGenderPatient(message.get("genderPatient") != null ? message.get("genderPatient").toString() : "");

            if (!patientRefService.existsById(ref.getCodPatient())) {
                patientRefService.save(ref);
            }
        } catch (Exception e) {
            System.err.println("Error procesando paciente: " + e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PATIENT_UPDATED_QUEUE)
    public void handlePatientUpdated(Map<String, Object> message) {
        try {
            Long codPatient = Long.valueOf(message.get("codPatient").toString());
            PatientRef ref = patientRefRepository.findById(codPatient).orElse(null);
            if (ref == null) return;

            String name = message.get("namePatient") != null ? message.get("namePatient").toString() : "";
            String secondName = message.get("secondNamePatient") != null ? message.get("secondNamePatient").toString() : "";
            ref.setNamePatient((name + " " + secondName).trim());

            String lastName = message.get("lastNamePatient") != null ? message.get("lastNamePatient").toString() : "";
            String secondLastName = message.get("secondLastNamePatient") != null ? message.get("secondLastNamePatient").toString() : "";
            ref.setLastNamePatient((lastName + " " + secondLastName).trim());

            if (message.get("phonePatient") != null) ref.setPhonePatient(Long.valueOf(message.get("phonePatient").toString()));
            if (message.get("genderPatient") != null) ref.setGenderPatient(message.get("genderPatient").toString());

            patientRefService.save(ref);
        } catch (Exception e) {
            System.err.println("Error actualizando paciente: " + e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PROFESSIONAL_QUEUE)
    public void handleProfessionalRegistered(Map<String, Object> message) {
        try {
            ProfessionalRef ref = new ProfessionalRef();
            ref.setCodProf(Long.valueOf(message.get("codProf").toString()));

            Map<String, Object> userRef = (Map<String, Object>) message.get("userRef");
            if (userRef != null) {
                String name = userRef.get("nameUser") != null ? userRef.get("nameUser").toString() : "";
                ref.setNameProf(name);
                String lastName = userRef.get("lastNameUser") != null ? userRef.get("lastNameUser").toString() : "";
                ref.setLastNameProf(lastName);
            }

            ref.setSpecialityProf(SpecialityProfEnum.valueOf(message.get("specialityProf") != null ? message.get("specialityProf").toString() : ""));
            ref.setTypeProf(TypeProfEnum.valueOf(message.get("typeProf") != null ? message.get("typeProf").toString() : ""));
            ref.setAttentionInterval(message.get("attentionInterval") != null ? Integer.valueOf(message.get("attentionInterval").toString()) : 0);

            if (!professionalRefService.existsById(ref.getCodProf())) {
                professionalRefService.save(ref);
            }
        } catch (Exception e) {
            System.err.println("Error procesando profesional: " + e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PROFESSIONAL_UPDATED_QUEUE)
    public void handleProfessionalUpdated(Map<String, Object> message) {
        try {
            Long codProf = Long.valueOf(message.get("codProf").toString());
            ProfessionalRef ref = professionalRefRepository.findById(codProf).orElse(null);
            if (ref == null) return;

            Map<String, Object> userRef = (Map<String, Object>) message.get("userRef");
            if (userRef != null) {
                if (userRef.get("nameUser") != null) ref.setNameProf(userRef.get("nameUser").toString());
                if (userRef.get("lastNameUser") != null) ref.setLastNameProf(userRef.get("lastNameUser").toString());
            }

            if (message.get("specialityProf") != null) ref.setSpecialityProf(SpecialityProfEnum.valueOf(message.get("specialityProf").toString()));
            if (message.get("typeProf") != null) ref.setTypeProf(TypeProfEnum.valueOf(message.get("typeProf").toString()));
            if (message.get("attentionInterval") != null) ref.setAttentionInterval(Integer.valueOf(message.get("attentionInterval").toString()));

            professionalRefService.save(ref);
        } catch (Exception e) {
            System.err.println("Error actualizando profesional: " + e.getMessage());
        }
    }
}