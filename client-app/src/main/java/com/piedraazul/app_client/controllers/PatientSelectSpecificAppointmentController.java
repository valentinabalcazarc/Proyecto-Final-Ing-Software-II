package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.design_patterns.facade.AppointmentFacade;
import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.FestivosService;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PatientSelectSpecificAppointmentController {

    private Patient patient;
    private SpecialityProfEnum specialityProf;

    @FXML private ComboBox<Professional> cbxProfessional;
    @FXML private DatePicker dpFecha;
    @FXML private TableView<Appointment> tblAppointments;
    @FXML private TableColumn<Appointment, LocalDate> colDate;
    @FXML private TableColumn<Appointment, String> colTime;
    @FXML private TableColumn<Appointment, String> colProfName;
    @FXML private TableColumn<Appointment, String> colProfType;
    @FXML private TableColumn<Appointment, String> colSpeciality;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private final FestivosService festivosService = new FestivosService();

    @FXML
    public void initialize() {
        setupTable();
        configurarCalendario();
    }

    public void setPatientAndSpeciality(Patient patient, SpecialityProfEnum specialityProf) {
        this.patient = patient;
        this.specialityProf = specialityProf;
        loadProfessionals();
        loadAppointments();
    }

    private void setupTable() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        colTime.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            return new SimpleStringProperty(cellData.getValue().getTime().format(formatter));
        });
        
        colProfName.setCellValueFactory(new PropertyValueFactory<>("professionalName"));
        colProfType.setCellValueFactory(new PropertyValueFactory<>("typeProfName"));
        colSpeciality.setCellValueFactory(new PropertyValueFactory<>("specialityName"));

        tblAppointments.setItems(appointmentList);
    }

    private void loadProfessionals() {
        cbxProfessional.getItems().clear();
        List<Professional> lista = ServiceManager.getInstance().getProfessionalService().getAllProfessionalsBySpeciality(specialityProf);
        cbxProfessional.getItems().addAll(lista);
    }

    private void loadAppointments() {
        List<Appointment> lista = ServiceManager.getInstance()
                .getAppointmentService()
                .getGeneretedAppointmentsBySpeciality(specialityProf);
        appointmentList.setAll(lista);
    }

    private void configurarCalendario() {
        dpFecha.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date == null || empty) return;

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eeeeee;");
                }

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

    @FXML
    private void handleBuscar(ActionEvent event) {
        Long codProf = null;
        if (cbxProfessional.getValue() != null) {
            codProf = cbxProfessional.getValue().getCodProf();
        }

        LocalDate fecha = dpFecha.getValue();

        List<Appointment> resultados = ServiceManager.getInstance()
                .getAppointmentService()
                .getGeneretedAppointmentsBySpecialityFiltered(codProf, fecha, specialityProf);

        appointmentList.setAll(resultados);
    }

    @FXML
    private void handleBorrarFiltro(ActionEvent event) {
        cbxProfessional.setValue(null);
        dpFecha.setValue(null);
        loadAppointments();
    }

    @FXML
    private void handleConfirmar(ActionEvent event) {
        Appointment selected = tblAppointments.getSelectionModel().getSelectedItem();
        selected.setDescription("[cita autónoma]");
        if (selected != null) {
            int result = AppointmentFacade.getInstance().scheduleAppointment(this.patient, selected);

            if (result == 0) {
                mostrarAlerta("Éxito", "¡Cita guardada con éxito!", Alert.AlertType.INFORMATION);
                
                NavigationService.getInstance().navigateTo(
                        "/fxml/PatientMainView.fxml", 
                        "Piedra Azul - Menú Principal", 
                        (Button) event.getSource()
                );
            } else if (result == 1) {
                mostrarAlerta("Error", "Error crítico: No se pudo registrar al nuevo paciente.", Alert.AlertType.ERROR);
            } else {
                mostrarAlerta("Error", "Error: El horario ya no está disponible o hubo un fallo al guardar.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Atención", "Por favor, seleccione una fila de la tabla.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleRegresar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PatientAutomaticRecommendationView.fxml"));
            Parent root = loader.load();

            PatientAutomaticRecommendationController controller = loader.getController();
            controller.setPatientAndSpeciality(patient, specialityProf);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Piedra Azul - Recomendación Automática");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver a la vista anterior.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
