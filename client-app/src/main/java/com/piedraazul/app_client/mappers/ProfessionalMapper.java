package com.piedraazul.app_client.mappers;

import com.piedraazul.app_client.dto.ProfessionalDTO;
import com.piedraazul.app_client.dto.ProfessionalResponseDTO;
import com.piedraazul.app_client.models.Professional;

public class ProfessionalMapper {

    public static ProfessionalDTO toRequestDTO(Professional model) {
        if (model == null) return null;
        ProfessionalDTO dto = new ProfessionalDTO();
        dto.setCodUser(model.getCodUser());
        dto.setGenProf(model.getGenProf());
        dto.setPhoneProf(model.getPhoneProf() != null ? String.valueOf(model.getPhoneProf()) : null);
        dto.setTypeProf(model.getTypeProf());
        dto.setSpecialityProf(model.getSpecialityProf());
        dto.setAttentionInterval(model.getAttentionInterval());
        return dto;
    }

    public static Professional toModel(ProfessionalResponseDTO responseDto) {
        if (responseDto == null) return null;
        Professional model = new Professional();
        model.setCodProf(responseDto.getCodProf());
        
        if (responseDto.getUserRef() != null) {
            model.setCodUser(responseDto.getUserRef().getCodUser());
            model.setCedUser(responseDto.getUserRef().getCedUser());
            model.setNameUser(responseDto.getUserRef().getNameUser());
            model.setLastNameUser(responseDto.getUserRef().getLastNameUser());
        }

        model.setGenProf(responseDto.getGenProf());
        model.setPhoneProf(responseDto.getPhoneProf());
        model.setTypeProf(responseDto.getTypeProf());
        model.setSpecialityProf(responseDto.getSpecialityProf());
        model.setAttentionInterval(responseDto.getAttentionInterval());
        model.setStatusProf(responseDto.getStatusProf());
        
        return model;
    }
}
