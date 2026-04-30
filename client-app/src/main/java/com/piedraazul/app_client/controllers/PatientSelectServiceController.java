package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.services.NavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientSelectServiceController {

    private Patient patient;

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    private void navegarAAutoRecomendacion(SpecialityProfEnum seleccion, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PatientAutomaticRecommendationView.fxml"));
            Parent root = loader.load();

            PatientAutomaticRecommendationController controller = loader.getController();
            controller.setPatientAndSpeciality(patient, seleccion);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Piedra Azul - Recomendación Automática");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de recomendación.");
        }
    }

    @FXML
    private void handleTerapiaNeutral(ActionEvent event) {
        navegarAAutoRecomendacion(SpecialityProfEnum.Neural_Therapy, event);
    }

    @FXML
    private void handleQuiropractica(ActionEvent event) {
        navegarAAutoRecomendacion(SpecialityProfEnum.Chiropractor, event);
    }

    @FXML
    private void handleFisioterapia(ActionEvent event) {
        navegarAAutoRecomendacion(SpecialityProfEnum.Physiotherapy, event);
    }

    @FXML
    private void handleRegresar(ActionEvent event) {
        NavigationService.getInstance().navigateTo(
                "/fxml/PatientInfoView.fxml", 
                "Piedra Azul - Información del Paciente", 
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
