package com.piedraazul.app_client.dto;

public class LoginRequestDTO {
    private Long cedUser;
    private String password;

    public LoginRequestDTO(Long cedUser, String password) {
        this.cedUser = cedUser;
        this.password = password;
    }

    // Getters y Setters
    public Long getCedUser() { return cedUser; }
    public String getPassword() { return password; }

    public void setCedUser(Long cedUser) {
        this.cedUser = cedUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}