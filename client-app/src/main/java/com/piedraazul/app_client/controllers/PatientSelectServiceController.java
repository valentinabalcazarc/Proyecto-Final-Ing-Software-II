package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PatientSelectServiceController {

    private Patient patient;
    private boolean hasAppointments = false;

    @FXML private Button btnTerapiaNeutral;
    @FXML private Button btnQuiropractica;
    @FXML private Button btnFisioterapia;
    @FXML private VBox cardTerapiaNeutral;
    @FXML private VBox cardQuiropractica;
    @FXML private VBox cardFisioterapia;
    @FXML private Label lblRestriccion;

    public void setPatient(Patient patient) {
        this.patient = patient;
        verificarCitasPrevias();
    }

    /**
     * Verifica si el paciente tiene citas previas.
     * Si no tiene ninguna, solo puede agendar consulta general:
     * deshabilita las tarjetas de especialidad y muestra mensaje informativo.
     */
    private void verificarCitasPrevias() {
        if (patient == null || patient.getCodPatient() == null) {
            // Paciente nuevo (sin codPatient), no tiene citas
            hasAppointments = false;
        } else {
            try {
                List<Appointment> citas = ServiceManager.getInstance()
                        .getAppointmentService()
                        .getAppointmentsByPatient(patient.getCodPatient());
                hasAppointments = citas != null && !citas.isEmpty();
            } catch (Exception e) {
                System.err.println(">> Error al verificar citas del paciente: " + e.getMessage());
                hasAppointments = false;
            }
        }

        if (!hasAppointments) {
            // Deshabilitar botones de especialidad
            btnTerapiaNeutral.setDisable(true);
            btnQuiropractica.setDisable(true);
            btnFisioterapia.setDisable(true);

            // Atenuar visualmente las tarjetas de especialidad
            cardTerapiaNeutral.setOpacity(0.45);
            cardQuiropractica.setOpacity(0.45);
            cardFisioterapia.setOpacity(0.45);

            // Quitar cursor de mano en tarjetas deshabilitadas
            cardTerapiaNeutral.setStyle(cardTerapiaNeutral.getStyle() + " -fx-cursor: default;");
            cardQuiropractica.setStyle(cardQuiropractica.getStyle() + " -fx-cursor: default;");
            cardFisioterapia.setStyle(cardFisioterapia.getStyle() + " -fx-cursor: default;");

            // Mostrar mensaje de restricción
            if (lblRestriccion != null) {
                lblRestriccion.setVisible(true);
                lblRestriccion.setManaged(true);
            }
        } else {
            // Paciente con citas previas: todo habilitado
            if (lblRestriccion != null) {
                lblRestriccion.setVisible(false);
                lblRestriccion.setManaged(false);
            }
        }
    }

    private void navegarAAutoRecomendacion(SpecialityProfEnum seleccion, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PatientAutomaticRecommendationView.fxml"));
            Parent root = loader.load();

            PatientAutomaticRecommendationController controller = loader.getController();
            controller.setPatientAndSpeciality(patient, seleccion);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Piedra Azul - Recomendación Automática");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/fxml/stylesheet.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de recomendación.");
        }
    }

    @FXML
    private void handleConsultaGeneral(ActionEvent event) {
        navegarAAutoRecomendacion(SpecialityProfEnum.General, event);
    }

    @FXML
    private void handleTerapiaNeutral(ActionEvent event) {
        if (!hasAppointments) {
            mostrarAlerta("Acceso restringido",
                    "Debe tener al menos una cita de consulta general antes de agendar una cita de especialidad.");
            return;
        }
        navegarAAutoRecomendacion(SpecialityProfEnum.Neural_Therapy, event);
    }

    @FXML
    private void handleQuiropractica(ActionEvent event) {
        if (!hasAppointments) {
            mostrarAlerta("Acceso restringido",
                    "Debe tener al menos una cita de consulta general antes de agendar una cita de especialidad.");
            return;
        }
        navegarAAutoRecomendacion(SpecialityProfEnum.Chiropractor, event);
    }

    @FXML
    private void handleFisioterapia(ActionEvent event) {
        if (!hasAppointments) {
            mostrarAlerta("Acceso restringido",
                    "Debe tener al menos una cita de consulta general antes de agendar una cita de especialidad.");
            return;
        }
        navegarAAutoRecomendacion(SpecialityProfEnum.Physiotherapy, event);
    }

    @FXML
    private void handleRegresar(ActionEvent event) {
        NavigationService.getInstance().navigateTo(
                "/fxml/PatientMainView.fxml",
                "Piedra Azul - Menú Paciente",
                (Button) event.getSource()
        );
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
