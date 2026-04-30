package com.piedraazul.app_client.services;

import com.piedraazul.app_client.dto.LoginResponseDTO;

public class SessionManager {
    private static String token;
    private static LoginResponseDTO currentUser;

    public static void setSession(LoginResponseDTO response) {
        token = response.getToken();
        currentUser = response;
    }

    public static String getToken() {
        return token;
    }

    public static LoginResponseDTO getCurrentUser() {
        return currentUser;
    }

    public static Long getCurrentUserCodUser() {
        return (currentUser != null) ? currentUser.getCodUser() : null;
    }

    public static void setCurrentUserCodUser(Long codUser) {
        currentUser.setCodUser(codUser);
    }

    public static void clearSession() {
        token = null;
        currentUser = null;
    }
}