package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.design_patterns.strategy.SearchByProfAndDateStrategy;
import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.design_patterns.strategy.AppointmentSearchContext;
import com.piedraazul.app_client.design_patterns.strategy.SearchByPatientStrategy;
import com.piedraazul.app_client.design_patterns.strategy.SearchParams;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SchedulerManageAppointmentsController {

    @FXML private TextField txtCedula;
    @FXML private TableView<Appointment> tblAppointments;
    @FXML private TableColumn<Appointment, Integer> colId;
    @FXML private TableColumn<Appointment, LocalDate> colDate;
    @FXML private TableColumn<Appointment, LocalTime> colTime;
    @FXML private TableColumn<Appointment, Integer> colPatientName;
    @FXML private TableColumn<Appointment, String> colProfessional;
    @FXML private TableColumn<Appointment, String> colType;
    @FXML private TableColumn<Appointment, String> colSpeciality;
    @FXML private TableColumn<Appointment, String> colStatus;
    @FXML private TableColumn<Appointment, String> colDescription;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private final AppointmentSearchContext searchContext = new AppointmentSearchContext();

    @FXML
    public void initialize() {
        loadAllAppointments();
        setupTable();
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName")); // campo en Appointment
        colProfessional.setCellValueFactory(new PropertyValueFactory<>("professionalName"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeProfName"));
        colSpeciality.setCellValueFactory(new PropertyValueFactory<>("specialityName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblAppointments.setItems(appointmentList);
    }

    private void loadAllAppointments() {
        try {
            // ── Patrón Strategy: carga inicial — citas AGENDADAS reales, sin filtro ──
            // Pasa ambos parámetros null → SearchByProfAndDateStrategy llama a
            // searchAppointmentsTyped(null, null) → endpoint GET /appointments (todas)
            searchContext.setStrategy(new SearchByProfAndDateStrategy());
            List<Appointment> list = searchContext.executeSearch(
                    new SearchParams.Builder().build());
            appointmentList.setAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFind(ActionEvent event) {
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty()) {
            loadAllAppointments();
            return;
        }

        try {
            Long ced = Long.parseLong(cedula);
            Patient patient = ServiceManager.getInstance().getPatientService().findByCed(ced);

            if (patient != null) {
                // ── Patrón Strategy: citas reales del paciente ───────────
                searchContext.setStrategy(new SearchByPatientStrategy());
                SearchParams params = new SearchParams.Builder()
                        .patientId(patient.getCodPatient())
                        .build();
                List<Appointment> appointments = searchContext.executeSearch(params);
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
    public void handleCancel() {
        Appointment selected = tblAppointments.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (selected.getStatus().equals("Cancelled") || selected.getStatus().equals("Completed")) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se puede cancelar una cita completeda o cancelada.");
        }else{
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea cancelar la cita?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.YES) {
                boolean success = ServiceManager.getInstance().getAppointmentService().deleteAppointment(selected.getId());
                if (success) {
                    new Alert(Alert.AlertType.INFORMATION, "Cita cancelada.").showAndWait();
                    loadAllAppointments();
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
                        getClass().getResource("/fxml/SchedulerAutomaticRecommendationView.fxml"));
                Parent root = loader.load();

                SchedulerAutomaticRecommendationController controller = loader.getController();
                controller.setPatientAndSpeciality(patient, specialityProf,selected);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setTitle("Piedra Azul - Citas Más Cercana");
                stage.setScene(new Scene(root));
                stage.getScene().getStylesheets().add(getClass().getResource("/fxml/stylesheet.css").toExternalForm());
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de citas es.");
            }
        }
    }

    @FXML
    private void handleReturn(ActionEvent event) {
        NavigationService.getInstance().navigateTo("/fxml/SchedulerMainView.fxml", "Menú Principal - Agendador",
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