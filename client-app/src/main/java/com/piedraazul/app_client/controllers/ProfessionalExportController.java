package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Professional;
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

public class ProfessionalExportController {

    @FXML private ComboBox<Professional> cbxProfessional;
    @FXML private DatePicker dpDate;
    @FXML private TableView<Appointment> tblAppointments;
    @FXML private TableColumn<Appointment, Integer> colId;
    @FXML private TableColumn<Appointment, LocalDate> colDate;
    @FXML private TableColumn<Appointment, String> colTime;
    @FXML private TableColumn<Appointment, Integer> colPatientId;
    @FXML private TableColumn<Appointment, String> colPatientName;
    @FXML private TableColumn<Appointment, String> colProfName;
    @FXML private TableColumn<Appointment, String> colSpeciality;
    @FXML private TableColumn<Appointment, String> colStatus;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        loadProfessionals();
        loadAllAppointments();
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        // For patient name and prof name, we'd ideally have them in the model or a wrapper
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName")); 
        colProfName.setCellValueFactory(new PropertyValueFactory<>("professionalName"));
        colSpeciality.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        tblAppointments.setItems(appointmentList);
    }

    private void loadProfessionals() {
        List<Professional> professionals = ServiceManager.getInstance().getProfessionalService().getAllProfessionals();
        cbxProfessional.setItems(FXCollections.observableArrayList(professionals));
    }

    private void loadAllAppointments() {
        // Fallback to searching with null filters to get all
        List<Appointment> appointments = ServiceManager.getInstance().getAppointmentService().searchAppointmentsTyped(null, null);
        appointmentList.setAll(appointments);
    }

    @FXML
    private void handleFind(ActionEvent event) {
        Professional selectedProf = cbxProfessional.getValue();
        LocalDate selectedDate = dpDate.getValue();
        Integer profId = (selectedProf != null) ? (int)selectedProf.getCodProf() : null;
        
        List<Appointment> filtered = ServiceManager.getInstance().getAppointmentService()
                .searchAppointmentsTyped(profId, selectedDate);
        appointmentList.setAll(filtered);
    }

    @FXML
    private void handleClear(ActionEvent event) {
        cbxProfessional.setValue(null);
        dpDate.setValue(null);
        loadAllAppointments();
    }

    @FXML
    private void handleReturn(ActionEvent event) {
        NavigationService.getInstance().navigateTo("/fxml/ProfessionalMainView.fxml", "Menú Principal - Profesional", (Button) event.getSource());
    }

    @FXML
    private void handleExport(ActionEvent event) {
        if (appointmentList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Sin datos", "No hay citas para exportar.");
            return;
        }

        // Show a ChoiceDialog for format selection
        List<String> choices = FXCollections.observableArrayList("PDF", "Excel", "JSON", "XML");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("PDF", choices);
        dialog.setTitle("Exportar Citas");
        dialog.setHeaderText("Seleccione el formato de exportación");
        dialog.setContentText("Formato:");

        dialog.showAndWait().ifPresent(format -> {
            showAlert(Alert.AlertType.INFORMATION, "Exportación", "Iniciando exportación en formato " + format + "...");
            // Here you would call the export logic (Pipe & Filter pattern if implemented)
        });
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
