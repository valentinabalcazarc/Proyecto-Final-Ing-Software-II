package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.SessionManager;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class PatientMainController {

    @FXML private Button btnAddAppointment;
    @FXML private Button btnViewAppointments;
    @FXML private Button btnCerrarSesion;

    @FXML
    public void handleAddAppointment(ActionEvent event) {
        Long cedUser = SessionManager.getCurrentUserCodUser();
        Patient patient = ServiceManager.getInstance().getPatientService().findByCed(cedUser);
        navegarSelccionarServicio(patient, event);
    }

    private void navegarSelccionarServicio(Patient patient, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PatientSelectServiceView.fxml"));
            Parent root = loader.load();

            PatientSelectServiceController controller = loader.getController();
            controller.setPatient(patient);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Piedra Azul - Seleccionar Servicio");
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("/fxml/stylesheet.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de recomendación.");
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
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
