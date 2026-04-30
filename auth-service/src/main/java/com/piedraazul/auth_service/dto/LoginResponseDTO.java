package com.piedraazul.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String role;
    private Long codUser;
    private Long cedUser;
    private String nameUser;
}