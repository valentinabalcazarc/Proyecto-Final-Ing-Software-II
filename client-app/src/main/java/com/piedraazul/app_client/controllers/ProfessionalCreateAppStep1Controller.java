package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.models.Appointment;
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
import java.util.List;

public class ProfessionalCreateAppStep1Controller {

    @FXML private ComboBox<Professional> cbxProfessional;
    @FXML private DatePicker dpDate;
    @FXML private TableView<Appointment> tblAppointments;
    @FXML private TableColumn<Appointment, LocalDate> colDate;
    @FXML private TableColumn<Appointment, String> colTime;
    @FXML private TableColumn<Appointment, String> colProfName;
    @FXML private TableColumn<Appointment, String> colSpeciality;
    @FXML private Button btnContinue;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        loadProfessionals();
        loadAllGeneratedAppointments();
    }

    private void setupTable() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        // For professional name and speciality, we might need a custom cell factory or mapping if Appointment model doesn't have them
        // Assuming Appointment has professionalId, we might need to fetch names.
        // In the original Swing, it seems they had Object[] from getGeneretedAppointments.
        
        colProfName.setCellValueFactory(cellData -> {
            // Placeholder: usually you'd get the name from a professional map
            return new javafx.beans.property.SimpleStringProperty("Prof ID: " + cellData.getValue().getProfessionalId());
        });
        
        tblAppointments.setItems(appointmentList);
    }

    private void loadProfessionals() {
        List<Professional> professionals = ServiceManager.getInstance().getProfessionalService().getAllProfessionals();
        cbxProfessional.setItems(FXCollections.observableArrayList(professionals));
    }

    private void loadAllGeneratedAppointments() {
        // Using the new typed method (which I'll implement in Step 2 of this task)
        List<Appointment> appointments = ServiceManager.getInstance().getAppointmentService().getGeneratedAppointmentsTyped();
        appointmentList.setAll(appointments);
    }

    @FXML
    private void handleFind(ActionEvent event) {
        Professional selectedProf = cbxProfessional.getValue();
        LocalDate selectedDate = dpDate.getValue();
        Integer profId = (selectedProf != null) ? (int)selectedProf.getCodProf() : null;
        
        List<Appointment> filtered = ServiceManager.getInstance().getAppointmentService()
                .getGeneratedAppointmentsFilteredTyped(profId, selectedDate);
        appointmentList.setAll(filtered);
    }

    @FXML
    private void handleClearFilter(ActionEvent event) {
        cbxProfessional.setValue(null);
        dpDate.setValue(null);
        loadAllGeneratedAppointments();
    }

    @FXML
    private void handleRegresar(ActionEvent event) {
        NavigationService.getInstance().navigateTo("/fxml/ProfessionalMainView.fxml", "Menú Principal - Profesional", (Button) event.getSource());
    }

    @FXML
    private void handleContinue(ActionEvent event) {
        Appointment selected = tblAppointments.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selección requerida");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione una cita de la tabla.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfessionalCreateAppStep2.fxml"));
            Parent root = loader.load();
            
            ProfessionalCreateAppStep2Controller controller = loader.getController();
            controller.setAppointment(selected);
            
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Agendar Cita - Paso 2");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
