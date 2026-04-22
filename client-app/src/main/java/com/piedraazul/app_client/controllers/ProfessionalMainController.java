package com.piedraazul.app_client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.SessionManager;

public class ProfessionalMainController {

    @FXML private Button btnAddAppointment;
    @FXML private Button btnManageAppointments;
    @FXML private Button btnViewAppointments;
    @FXML private Button btnExportAppointments;
    @FXML private Button btnCerrarSesion;

    @FXML
    public void handleAddAppointment() {
        NavigationService.getInstance().navigateTo(
                "/fxml/ProfessionalCreateAppStep1.fxml", 
                "Agendar Cita - Paso 1", 
                btnAddAppointment
        );
    }

    @FXML
    public void handleManageAppointments() {
        NavigationService.getInstance().navigateTo(
                "/fxml/ProfessionalManageAppointments.fxml", 
                "Gestionar Citas", 
                btnManageAppointments
        );
    }

    @FXML
    public void handleViewAppointments() {
        NavigationService.getInstance().navigateTo(
                "/fxml/ProfessionalViewAppointments.fxml", 
                "Piedra Azul - Visualización de Citas", 
                btnViewAppointments
        );
    }

    @FXML
    public void handleExportAppointments() {
        NavigationService.getInstance().navigateTo(
                "/fxml/ProfessionalExport.fxml", 
                "Exportar Citas", 
                btnExportAppointments
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
