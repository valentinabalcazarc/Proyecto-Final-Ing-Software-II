package com.piedraazul.app_client.dto;
import com.piedraazul.app_client.enums.RoleUserEnum;

public class RegisterDTO {
    private Long cedUser;
    private String passUser;
    private String nameUser;
    private String secondNameUser;
    private String lastNameUser;
    private String secondLastNameUser;
    private RoleUserEnum roleUser;
    private String securityQuestion;
    private String securityAnswer;
}
