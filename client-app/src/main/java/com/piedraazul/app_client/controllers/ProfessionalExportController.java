package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.FestivosService;
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

public class ProfessionalExportController {

    @FXML private ComboBox<Professional> cbxProfessional;
    @FXML private DatePicker dpDate;
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
    private final FestivosService festivosService = new FestivosService();

    @FXML
    public void initialize() {
        setupTable();
        loadProfessionals();
        loadAllAppointments();
        configurarCalendario();

        dpDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadAllAppointments();
            }
        });
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

    private void loadProfessionals() {
        List<Professional> professionals = ServiceManager.getInstance().getProfessionalService().getAllProfessionals();
        cbxProfessional.setItems(FXCollections.observableArrayList(professionals));
    }

    private void loadAllAppointments() {
        try {
            // Assuming getAppointmentsForTable logic
            List<Appointment> list = ServiceManager.getInstance().getAppointmentService().getAllAppointments();
            tblAppointments.setItems(FXCollections.observableArrayList(list));
            appointmentList.setAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFind(ActionEvent event) {
        Professional selectedProf = cbxProfessional.getValue();
        LocalDate selectedDate = dpDate.getValue();
        Long profId = (selectedProf != null) ? selectedProf.getCodProf() : null;
        
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

        List<String> choices = FXCollections.observableArrayList("JSON", "HTTML");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("JSON", choices);
        dialog.setTitle("Exportar Citas");
        dialog.setHeaderText("Seleccione el formato de exportación");
        dialog.setContentText("Formato:");

        dialog.showAndWait().ifPresent(format -> {
            showAlert(Alert.AlertType.INFORMATION, "Exportación", "Iniciando exportación en formato " + format + "...");
        });

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfessionalExportSelection.fxml"));
            Parent root = loader.load();

            ProfessionalExportSelectionController controller = loader.getController();
            controller.setAppointmentList(appointmentList);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Export Selection");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void configurarCalendario() {
        dpDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Bloquear fines de semana y festivos
                if (festivosService.esDiaInvalido(date)) {
                    setDisable(true);

                    if (festivosService.esFestivo(date)) {
                        setStyle("-fx-background-color: #ffcccc; -fx-text-fill: #cc0000;");
                        setTooltip(new Tooltip("Día Festivo"));
                    } else {
                        setStyle("-fx-background-color: #f0f0f0;");
                        setTooltip(new Tooltip("Fin de semana"));
                    }
                }
            }
        });
    }
}
