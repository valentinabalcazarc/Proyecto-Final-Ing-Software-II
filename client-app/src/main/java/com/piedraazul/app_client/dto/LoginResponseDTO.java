package com.piedraazul.app_client.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String role;
    private Long codUser;
    private String nameUser;
}