package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Appointment;
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

import java.io.IOException;
import java.util.List;

public class PatientMainController {

    @FXML private Button btnAddAppointment;
    @FXML private Button btnViewAppointments;
    @FXML private Button btnCerrarSesion;

    @FXML
    public void handleAddAppointment(ActionEvent event) {
        Long cedUser = SessionManager.getCurrentUserCodUser();
        Patient patient = ServiceManager.getInstance().getPatientService().findByCed(cedUser);

        // Validar que el paciente no tenga una cita en estado "Scheduled" (Agendada)
        if (patient != null && pacienteTieneCitaAgendada(patient.getCodPatient())) {
            mostrarAlertaAdvertencia(
                    "No es posible agendar una nueva cita",
                    "Ya tiene una cita agendada",
                    "Usted ya cuenta con una cita en estado \"Agendada\". "
                    + "No es posible agendar otra cita hasta que la cita actual sea completada o cancelada.\n\n"
                    + "Puede consultar sus citas en la sección \"Ver Mis Citas\"."
            );
            return;
        }

        navegarSelccionarServicio(patient, event);
    }

    /**
     * Verifica si el paciente tiene al menos una cita con estado "Scheduled" (Agendada).
     */
    private boolean pacienteTieneCitaAgendada(Long codPatient) {
        try {
            List<Appointment> citas = ServiceManager.getInstance()
                    .getAppointmentService()
                    .getAppointmentsByPatient(codPatient);
            return citas.stream().anyMatch(a -> "Scheduled".equalsIgnoreCase(a.getStatus()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    private void mostrarAlertaAdvertencia(String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
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
