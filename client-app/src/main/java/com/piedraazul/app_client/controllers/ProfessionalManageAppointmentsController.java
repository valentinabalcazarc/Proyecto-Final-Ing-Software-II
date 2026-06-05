package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.design_patterns.strategy.*;
import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import com.piedraazul.app_client.services.SessionManager;
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

public class ProfessionalManageAppointmentsController {

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

    @FXML private ComboBox<String> cbxNewStatus;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private final AppointmentSearchContext searchContext = new AppointmentSearchContext();
    private Long codProfSesion;
    Professional prof;

    @FXML
    public void initialize() {
        Long codUser = SessionManager.getCurrentUser().getCodUser();
        try {
            prof = ServiceManager.getInstance()
                    .getProfessionalService()
                    .findByCodUser(codUser);
            codProfSesion = prof.getCodProf();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupTable();
        setupStatusComboBox();
        loadAllAppointments();
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colProfessional.setCellValueFactory(new PropertyValueFactory<>("professionalName"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeProfName"));
        colSpeciality.setCellValueFactory(new PropertyValueFactory<>("specialityName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblAppointments.setItems(appointmentList);
    }

    private void setupStatusComboBox() {
        cbxNewStatus.setItems(FXCollections.observableArrayList(
                "ATENDIDA", "CANCELADA"
        ));
        cbxNewStatus.setPromptText("Seleccione estado");
    }

    private void loadAllAppointments() {
        try {
            searchContext.setStrategy(new SearchByProfAndDateStrategy());
            List<Appointment> list = searchContext.executeSearch(
                    new SearchParams.Builder()
                            .professionalId(codProfSesion)
                            .build());
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
                // ── Filtra por paciente Y profesional de sesión ──
                searchContext.setStrategy(new SearchByPatientAndProfStrategy());
                SearchParams params = new SearchParams.Builder()
                        .patientId(patient.getCodPatient())
                        .professionalId(codProfSesion)
                        .build();
                List<Appointment> appointments = searchContext.executeSearch(params);
                appointmentList.setAll(appointments);
            } else {
                showAlert(Alert.AlertType.WARNING, "No encontrado",
                        "No se encontró el paciente con esa cédula.");
                appointmentList.clear();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Cédula inválida.");
        }
    }

    @FXML
    private void handleChangeStatus(ActionEvent event) {
        Appointment selected = tblAppointments.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selección requerida", "Por favor, seleccione una cita.");
            return;
        }

        String newStatus = cbxNewStatus.getValue();
        if (newStatus == null || newStatus.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Estado requerido", "Por favor, seleccione el nuevo estado para la cita.");
            return;
        }

        if (newStatus.equalsIgnoreCase(selected.getStatus())) {
            showAlert(Alert.AlertType.INFORMATION, "Sin cambios", "La cita ya se encuentra en estado \"" + newStatus + "\".");
            return;
        }

        if (selected.getStatus().equals("Cancelled") || selected.getStatus().equals("Completed")) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se puede cambiar el estado de una cita cancelada o completeda.");
        }else{
            // ── Validación: solo marcar ATENDIDA si la cita ya pasó ──
            if (newStatus.equals("ATENDIDA")) {
                LocalDate hoy = LocalDate.now();
                LocalTime ahora = LocalTime.now();
                boolean citaYaPaso = selected.getDate().isBefore(hoy) ||
                        (selected.getDate().isEqual(hoy) && selected.getTime().isBefore(ahora));

                if (!citaYaPaso) {
                    showAlert(Alert.AlertType.WARNING,
                            "Acción no permitida",
                            "Solo puede marcar una cita como ATENDIDA después de que su fecha y hora hayan pasado.");
                    return;
                }
            }

            String mensaje = newStatus.equals("ATENDIDA")
                    ? "¿Está seguro de marcar esta cita como ATENDIDA?"
                    : "¿Está seguro de marcar esta cita como CANCELADA?";

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, mensaje,
                    ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirmar cambio de estado");
            confirm.setHeaderText("Cambiar estado de cita #" + selected.getId());
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.YES) {
                boolean success = ServiceManager.getInstance().getAppointmentService()
                        .updateAppointmentStatus(selected.getId(), newStatus);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito",
                            "La cita fue actualizada a estado \"" + newStatus + "\" correctamente.");
                    handleFind(null);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "No se pudo actualizar el estado de la cita. Verifique la conexión con el servidor.");
                }
            }
        }


    }

    @FXML
    private void handleClearFilter(ActionEvent event) {
        txtCedula.setText("");
        loadAllAppointments();
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

        System.out.println("PatientId: " + selected.getPatientId());
        Patient patient = ServiceManager.getInstance().getPatientService().findByCod(selected.getPatientId());
        SpecialityProfEnum specialityProf = prof.getSpecialityProf();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/ProfessionalAutomaticRecommendationView.fxml"));
            Parent root = loader.load();

            ProfessionalAutomaticRecommendationController controller = loader.getController();
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