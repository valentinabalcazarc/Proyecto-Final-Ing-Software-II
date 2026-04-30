package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.models.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import com.piedraazul.app_client.services.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
            Long cedUser = SessionManager.getCurrentUserCodUser();
            System.out.println("CurrentUserCedUser " + cedUser);
            if (cedUser == null) return;

            // Buscar codPatient desde el patient-service
            Patient patient = ServiceManager.getInstance().getPatientService().findByCed(cedUser);
            if (patient != null) {
                List<Appointment> list = ServiceManager.getInstance().getAppointmentService()
                        .getAppointmentsByPatient(patient.getCodPatient());
                tblAppointments.setItems(FXCollections.observableArrayList(list));

            }else{
                System.out.println(">> No se encontró paciente para codUser: " + cedUser);
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

    @FXML
    public void handleReschedule() {
        Appointment selected = tblAppointments.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Reagendando cita ID: " + selected.getId());
            // TODO: Implement rescheduling logic
        }
    }
}
