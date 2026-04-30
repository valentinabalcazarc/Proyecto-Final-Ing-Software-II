package com.piedraazul.app_client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.SessionManager;

public class PatientMainController {

    @FXML private Button btnAddAppointment;
    @FXML private Button btnViewAppointments;
    @FXML private Button btnCerrarSesion;

    @FXML
    public void handleAddAppointment() {
        NavigationService.getInstance().navigateTo(
                "/fxml/PatientInfoView.fxml", 
                "Piedra Azul - Información del Paciente", 
                btnAddAppointment
        );
    }

    @FXML
    public void handleViewAppointments() {
        NavigationService.getInstance().navigateTo(
                "/fxml/PatientViewAppointments.fxml", 
                "Piedra Azul - Mis Citas", 
                btnViewAppointments
        );
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
