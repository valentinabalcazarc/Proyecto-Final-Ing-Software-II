package com.piedraazul.app_client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.SessionManager;

public class AdminController {

    @FXML private Button btnRegProfessional;
    @FXML private Button btnProfessionalSchedule;
    @FXML private Button btnCerrarSesion;

    @FXML
    public void handleRegProfessional() {
        NavigationService.getInstance().navigateTo(
                "/fxml/RegisterProfessionalView.fxml", 
                "Piedra Azul - Registrar Profesional", 
                btnRegProfessional
        );
    }

    @FXML
    public void handleProfessionalSchedule() {
        // TODO: implementar vista de gestión de horario
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("En construcción");
        alert.setHeaderText(null);
        alert.setContentText("La gestión de horarios aún no está disponible.");
        alert.showAndWait();
    }

    @FXML
    public void handleCerrarSesion() {
        SessionManager.clearSession();
        NavigationService.getInstance().navigateTo(
                "/fxml/LoginView.fxml", 
                "Piedra Azul - Login", 
                btnCerrarSesion
        );
    }
}
