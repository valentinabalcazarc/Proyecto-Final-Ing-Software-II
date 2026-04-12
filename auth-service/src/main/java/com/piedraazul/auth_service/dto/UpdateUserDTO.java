package com.piedraazul.auth_service.dto;

import com.piedraazul.auth_service.enums.StatusUserEnum;
import lombok.Data;

@Data
public class UpdateUserDTO {
    private String passUser;
    private String nameUser;
    private String secondNameUser;
    private String lastNameUser;
    private String secondLastNameUser;
    private String securityQuestion;
    private String securityAnswer;
    private StatusUserEnum statusUser;
}