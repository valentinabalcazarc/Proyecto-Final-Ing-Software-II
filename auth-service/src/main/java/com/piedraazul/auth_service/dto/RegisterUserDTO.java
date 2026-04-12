package com.piedraazul.auth_service.dto;

import com.piedraazul.auth_service.enums.RoleUserEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUserDTO {
    @NotNull
    private Long cedUser;
    @NotBlank
    private String passUser;
    @NotBlank
    private String nameUser;
    private String secondNameUser;
    @NotBlank
    private String lastNameUser;
    private String secondLastNameUser;
    @NotNull
    private RoleUserEnum roleUser;
    @NotBlank
    private String securityQuestion;
    @NotBlank
    private String securityAnswer;
}