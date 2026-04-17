package com.piedraazul.app_client.dto;

public class LoginResponseDTO {
    private String token;
    private String role;
    private Long codUser;
    private String nameUser;

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public Long getCodUser() {
        return codUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCodUser(Long codUser) {
        this.codUser = codUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}