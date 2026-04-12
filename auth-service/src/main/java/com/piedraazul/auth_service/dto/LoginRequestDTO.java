package com.piedraazul.auth_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotNull
    private Long cedUser;
    @NotNull
    private String password;
}