package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import com.piedraazul.app_client.services.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PatientViewAppointmentsController {

    @FXML private TableView<Appointment> tblAppointments;
    @FXML private TableColumn<Appointment, LocalDate> colDate;
    @FXML private TableColumn<Appointment, LocalTime> colTime;
    @FXML private TableColumn<Appointment, String> colProfessional;
    @FXML private TableColumn<Appointment, String> colStatus;

    @FXML private Button btnRegresar;
    @FXML private Button btnCancelAppointment;
    @FXML private Button btnReschedule;
    private Long codPatSesion;

    @FXML
    public void initialize() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colProfessional.setCellValueFactory(new PropertyValueFactory<>("professionalName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        loadAppointments();
    }

    private void loadAppointments() {
        try {
           // codPatSesion = SessionManager.getCurrentUserCodUser();
            Long cedUser = SessionManager.getCurrentUser().getCedUser();
            Patient patient = ServiceManager.getInstance()
                    .getPatientService()
                    .findByCed(cedUser);
            codPatSesion = patient.getIdPatient();
            System.out.println("CurrentUserCedUser " + codPatSesion);
            if (codPatSesion == null) return;

            // Buscar codPatient desde el patient-service
            patient = ServiceManager.getInstance().getPatientService().findByCed(codPatSesion);
            if (patient != null) {
                List<Appointment> list = ServiceManager.getInstance().getAppointmentService()
                        .getAppointmentsByPatient(patient.getCodPatient());
                tblAppointments.setItems(FXCollections.observableArrayList(list));

            }else{
                System.out.println(">> No se encontró paciente para codUser: " + codPatSesion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegresar() {
        NavigationService.getInstance().navigateTo(
                "/fxml/PatientMainView.fxml",
                "Piedra Azul - Paciente", 
                btnRegresar
        );
    }

    @FXML
    public void handleCancelAppointment() {
        Appointment selected = tblAppointments.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (selected.getStatus().equals("Cancelled") || selected.getStatus().equals("Completed")) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se puede cancelar una cita completeda o cancelada.");
        }else{
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea cancelar su cita?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.YES) {
                boolean success = ServiceManager.getInstance().getAppointmentService().deleteAppointment(selected.getId());
                if (success) {
                    new Alert(Alert.AlertType.INFORMATION, "Cita cancelada.").showAndWait();
                    loadAppointments();
                } else {
                    new Alert(Alert.AlertType.ERROR, "No se pudo cancelar.").showAndWait();
                }
            }
        }
    }

    @FXML
    public void handleReschedule(ActionEvent event) {
        Appointment selected = tblAppointments.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selección requerida", "Por favor, seleccione una cita.");
            return;
        }

        if (selected.getStatus().equals("Cancelled") || selected.getStatus().equals("Completed")) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se puede reagendar una cita completeda o cancelada.");
        }else{
            System.out.println("PatientId: " + selected.getPatientId());
            Patient patient = ServiceManager.getInstance().getPatientService().findByCod(selected.getPatientId());
            Professional prof = ServiceManager.getInstance().getProfessionalService().findByCodProf(selected.getProfessionalId());
            SpecialityProfEnum specialityProf = prof.getSpecialityProf();

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/PatientRescheduleAutomaticRecommendationView.fxml"));
                Parent root = loader.load();

                PatientRescheduleAutomaticRecommendationController controller = loader.getController();
                controller.setPatientAndSpeciality(patient, specialityProf,selected);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setTitle("Piedra Azul - Citas Específicas");
                stage.setScene(new Scene(root));
                stage.getScene().getStylesheets().add(getClass().getResource("/fxml/stylesheet.css").toExternalForm());
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de citas es.");
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
