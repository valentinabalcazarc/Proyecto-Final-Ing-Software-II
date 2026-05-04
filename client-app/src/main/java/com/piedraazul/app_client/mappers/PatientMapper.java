package com.piedraazul.app_client.mappers;

import com.piedraazul.app_client.dto.PatientDTO;
import com.piedraazul.app_client.models.Patient;

public class PatientMapper {
    public static PatientDTO toDTO(Patient model) {
        if (model == null) return null;
        PatientDTO dto = new PatientDTO();
        dto.setCodPatient(model.getCodPatient());
        dto.setIdPatient(model.getIdPatient());
        dto.setNamePatient(model.getNamePatient());
        dto.setSecondNamePatient(model.getSecondNamePatient());
        dto.setLastNamePatient(model.getLastNamePatient());
        dto.setSecondLastNamePatient(model.getSecondLastNamePatient());
        dto.setPhonePatient(model.getPhonePatient());
        dto.setDateBirthPatient(model.getDateBirthPatient());
        dto.setGenderPatient(model.getGenderPatient());
        return dto;
    }

    public static Patient toModel(PatientDTO dto) {
        if (dto == null) return null;
        Patient model = new Patient();
        model.setCodPatient(dto.getCodPatient());
        model.setIdPatient(dto.getIdPatient());
        model.setNamePatient(dto.getNamePatient());
        model.setSecondNamePatient(dto.getSecondNamePatient());
        model.setLastNamePatient(dto.getLastNamePatient());
        model.setSecondLastNamePatient(dto.getSecondLastNamePatient());
        model.setPhonePatient(dto.getPhonePatient());
        model.setDateBirthPatient(dto.getDateBirthPatient());
        model.setGenderPatient(dto.getGenderPatient());
        return model;
    }
}
