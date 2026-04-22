package com.piedraazul.app_client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ProfessionalViewAppointmentsController {

    @FXML
    private TableView<Appointment> tblAppointments;
    @FXML
    private TableColumn<Appointment, Integer> colId;
    @FXML
    private TableColumn<Appointment, LocalDate> colDate;
    @FXML
    private TableColumn<Appointment, LocalTime> colTime;
    @FXML
    private TableColumn<Appointment, Integer> colPatient;
    @FXML
    private TableColumn<Appointment, String> colStatus;
    @FXML
    private TableColumn<Appointment, String> colDescription;

    @FXML
    private ComboBox<Professional> cbxProfessional;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Button btnFind;
    @FXML
    private Button btnClearFilter;
    @FXML
    private Button btnRegresar;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        // TODO: Handle patient name and status mappings

        loadProfessionals();
        loadAllAppointments();
    }

    private void loadProfessionals() {
        try {
            List<Professional> list = ServiceManager.getInstance().getProfessionalService().getAllProfessionals();
            cbxProfessional.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllAppointments() {
        try {
            // Assuming getAppointmentsForTable logic
            List<Appointment> list = ServiceManager.getInstance().getAppointmentService().getAllAppointments();
            tblAppointments.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFind() {
        Professional selectedProf = cbxProfessional.getValue();
        LocalDate selectedDate = dpDate.getValue();

        Integer profId = (selectedProf != null) ? (int) selectedProf.getCodProf() : null;

        try {
            // Mapping to the service search logic
            List<Appointment> results = ServiceManager.getInstance().getAppointmentService()
                    .searchAppointmentsTyped(profId, selectedDate);
            tblAppointments.setItems(FXCollections.observableArrayList(results));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleClearFilter() {
        cbxProfessional.setValue(null);
        dpDate.setValue(null);
        loadAllAppointments();
    }

    @FXML
    public void handleRegresar() {
        NavigationService.getInstance().navigateTo(
                "/fxml/ProfessionalMainView.fxml",
                "Piedra Azul - Profesional",
                btnRegresar);
    }
}
