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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.DateCell;
import javafx.scene.control.Tooltip;

public class ProfessionalCreateAppStep1Controller {

    @FXML private ComboBox<Professional> cbxProfessional;
    @FXML private DatePicker dpDate;
    @FXML private TableView<Appointment> tblAppointments;
    @FXML private TableColumn<Appointment, LocalDate> colDate;
    @FXML private TableColumn<Appointment, String> colTime;
    @FXML private TableColumn<Appointment, String> colProfName;
    @FXML private TableColumn<Appointment, String> colProfID;
    @FXML private TableColumn<Appointment, String> colSpeciality;
    @FXML private TableColumn<Appointment, String> colProfType;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private final FestivosService festivosService = new FestivosService();

    @FXML
    public void initialize() {
        setupTable();
        loadProfessionals();
        loadAllGeneratedAppointments();
        configurarCalendario();

        // Configuración del DatePicker (Equivalente a logicaCalendario)
        dpDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadAllGeneratedAppointments();
            }
        });

    }

    private void setupTable() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colProfID.setCellValueFactory(new PropertyValueFactory<>("professionalId"));
        colProfName.setCellValueFactory(new PropertyValueFactory<>("professionalName"));
        colProfType.setCellValueFactory(new PropertyValueFactory<>("typeProfName"));
        colSpeciality.setCellValueFactory(new PropertyValueFactory<>("specialityName"));

        tblAppointments.setItems(appointmentList);
    }

    private void loadProfessionals() {
        cbxProfessional.getItems().clear();
        List<Professional> lista = ServiceManager.getInstance().getProfessionalService().getAllProfessionals();
        cbxProfessional.getItems().addAll(lista);
    }

    private void loadAllGeneratedAppointments() {
        List<Appointment> lista = ServiceManager.getInstance()
                .getAppointmentService()
                .getGeneratedAppointmentsTyped();

        appointmentList.setAll(lista);
    }

    @FXML
    private void handleFilter(ActionEvent event) {
        Long codProf = null;
        if (cbxProfessional.getValue() != null) {
            codProf = cbxProfessional.getValue().getCodProf();
        }

        LocalDate fecha = dpDate.getValue();

        // Llamada filtrada al microservicio de citas
        List<Appointment> resultados = ServiceManager.getInstance()
                .getAppointmentService()
                .getGeneratedAppointmentsFilteredTyped(codProf, fecha);

        appointmentList.setAll(resultados);
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

    private void configurarCalendario() {
        dpDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date == null || empty) return;

                // 1. Bloquear fechas anteriores a hoy
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eeeeee;");
                }

                // 2. Bloquear fines de semana y festivos (usando tu servicio)
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
