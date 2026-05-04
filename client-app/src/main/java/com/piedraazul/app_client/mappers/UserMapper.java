package com.piedraazul.app_client.mappers;

import com.piedraazul.app_client.dto.RegisterDTO;
import com.piedraazul.app_client.models.User;

public class UserMapper {
    public static RegisterDTO toRegisterDTO(User model) {
        if (model == null) return null;
        RegisterDTO dto = new RegisterDTO();
        dto.setCedUser(model.getCedUser());
        dto.setPassUser(model.getPassUser());
        dto.setNameUser(model.getNameUser());
        dto.setSecondNameUser(model.getSecondNameUser());
        dto.setLastNameUser(model.getLastNameUser());
        dto.setSecondLastNameUser(model.getSecondLastNameUser());
        dto.setRoleUser(model.getRoleUser());
        dto.setSecurityQuestion(model.getSecurityQuestion());
        dto.setSecurityAnswer(model.getSecurityAnswer());
        return dto;
    }

    public static User toModel(RegisterDTO dto) {
        if (dto == null) return null;
        User model = new User();
        model.setCedUser(dto.getCedUser());
        model.setPassUser(dto.getPassUser());
        model.setNameUser(dto.getNameUser());
        model.setSecondNameUser(dto.getSecondNameUser());
        model.setLastNameUser(dto.getLastNameUser());
        model.setSecondLastNameUser(dto.getSecondLastNameUser());
        model.setRoleUser(dto.getRoleUser());
        model.setSecurityQuestion(dto.getSecurityQuestion());
        model.setSecurityAnswer(dto.getSecurityAnswer());
        return model;
    }
}
