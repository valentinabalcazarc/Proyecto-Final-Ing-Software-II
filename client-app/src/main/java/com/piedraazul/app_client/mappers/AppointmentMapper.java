package com.piedraazul.app_client.mappers;

import com.piedraazul.app_client.dto.AppointmentDTO;
import com.piedraazul.app_client.dto.AppointmentResponseDTO;
import com.piedraazul.app_client.models.Appointment;

public class AppointmentMapper {

    public static AppointmentDTO toRequestDTO(Appointment model, Long patientId) {
        if (model == null) return null;
        AppointmentDTO dto = new AppointmentDTO();
        dto.setCodProf(model.getProfessionalId());
        dto.setCodPatient(patientId != null ? patientId : model.getPatientId());
        dto.setDateApp(model.getDate());
        dto.setTimeApp(model.getTime());
        dto.setDescApp(model.getDescription());
        return dto;
    }

    public static Appointment toModel(AppointmentResponseDTO dto) {
        if (dto == null) return null;
        Appointment model = new Appointment();
        model.setId(dto.getCodApp());
        model.setDate(dto.getDateApp());
        model.setTime(dto.getTimeApp());
        model.setStatus(dto.getStatusApp());
        model.setDescription(dto.getDescApp());

        // Map Patient
        if (dto.getPatientRef() != null) {
            model.setPatientId(dto.getPatientRef().getCodPatient());
            model.setPatientName(dto.getPatientRef().getNamePatient() + " " + dto.getPatientRef().getLastNamePatient());
        } else if (dto.getCodPatient() != null) {
            model.setPatientId(dto.getCodPatient());
        }

        // Map Professional
        if (dto.getProfessionalName() != null) {
            // Slots generados (campos planos)
            model.setProfessionalName(dto.getProfessionalName());
            model.setProfessionalId(dto.getCodProf());
            model.setSpecialityName(dto.getSpecialityProf());
            model.setTypeProfName(dto.getTypeProf());
        } else if (dto.getProfessionalRef() != null) {
            // Citas reales (objeto anidado)
            model.setProfessionalId(dto.getProfessionalRef().getCodProf());
            model.setProfessionalName(dto.getProfessionalRef().getNameProf() + " " + dto.getProfessionalRef().getLastNameProf());
            model.setSpecialityName(dto.getProfessionalRef().getSpecialityProf());
            model.setTypeProfName(dto.getProfessionalRef().getTypeProf());
        }

        return model;
    }
}
