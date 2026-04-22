package com.piedraazul.app_client.dto;

import com.piedraazul.app_client.enums.StatusUserEnum;

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
