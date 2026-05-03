package com.piedraazul.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEventDTO {
    private Long codUser;
    private Long cedUser;
    private String nameUser;
    private String secondNameUser;
    private String lastNameUser;
    private String secondLastNameUser;
    private String roleUser;
}
