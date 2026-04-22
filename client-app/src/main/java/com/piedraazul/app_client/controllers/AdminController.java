package com.piedraazul.app_client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class AdminController {

    @FXML private Button btnRegProfessional;
    @FXML private Button btnProfessionalSchedule;
    @FXML private Button btnCerrarSesion;

    @FXML
    public void handleRegProfessional() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/RegisterProfessionalView.fxml"));
            Stage stage = (Stage) btnRegProfessional.getScene().getWindow();
            stage.setTitle("Piedra Azul - Registrar Profesional");
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleProfessionalSchedule() {
        // TODO: implementar vista de gestión de horario
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("En construcción");
        alert.setContentText("La gestión de horarios aún no está disponible.");
        alert.showAndWait();
    }

    @FXML
    public void handleCerrarSesion() {
        try {
            // Limpiar sesión activa
            com.piedraazul.app_client.services.SessionManager.clearSession();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setTitle("Piedra Azul - Login");
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
