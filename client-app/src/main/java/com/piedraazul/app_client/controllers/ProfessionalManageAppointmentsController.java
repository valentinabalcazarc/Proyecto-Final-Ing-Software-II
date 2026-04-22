package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class ProfessionalManageAppointmentsController {

    @FXML
    private TextField txtCedula;
    @FXML
    private TableView<Appointment> tblAppointments;
    @FXML
    private TableColumn<Appointment, Integer> colId;
    @FXML
    private TableColumn<Appointment, LocalDate> colDate;
    @FXML
    private TableColumn<Appointment, String> colTime;
    @FXML
    private TableColumn<Appointment, String> colStatus;
    @FXML
    private TableColumn<Appointment, String> colDescription;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        tblAppointments.setItems(appointmentList);
    }

    @FXML
    private void handleFind(ActionEvent event) {
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty())
            return;

        try {
            Long ced = Long.parseLong(cedula);
            Patient patient = ServiceManager.getInstance().getPatientService().findByCed(ced);

            if (patient != null) {
                List<Appointment> appointments = ServiceManager.getInstance().getAppointmentService()
                        .getAppointmentsByPatient(patient.getCodPatient());
                appointmentList.setAll(appointments);
            } else {
                showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontró el paciente con esa cédula.");
                appointmentList.clear();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Cédula inválida.");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Appointment selected = tblAppointments.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selección requerida", "Por favor, seleccione una cita.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de que desea cancelar esta cita?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            boolean success = ServiceManager.getInstance().getAppointmentService().deleteAppointment(selected.getId());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Cita cancelada correctamente.");
                handleFind(null); // Refresh table
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cancelar la cita.");
            }
        }
    }

    @FXML
    private void handleReschedule(ActionEvent event) {
        Appointment selected = tblAppointments.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selección requerida", "Por favor, seleccione una cita.");
            return;
        }

        // Re-use Create Step 1 logic but passing the context?
        // For simplicity, let's just go to Step 1 and the professional can pick a new
        // one.
        // Or we could have a specific "Reschedule" flow.
        // Original Swing 'btn_RescheduleAppointment' was empty, so I'll just go to Step
        // 1.
        NavigationService.getInstance().navigateTo("/fxml/ProfessionalCreateAppStep1.fxml",
                "Reagendar Cita - Selección de Nueva Fecha", (Button) event.getSource());
    }

    @FXML
    private void handleReturn(ActionEvent event) {
        NavigationService.getInstance().navigateTo("/fxml/ProfessionalMainView.fxml", "Menú Principal - Profesional",
                (Button) event.getSource());
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
