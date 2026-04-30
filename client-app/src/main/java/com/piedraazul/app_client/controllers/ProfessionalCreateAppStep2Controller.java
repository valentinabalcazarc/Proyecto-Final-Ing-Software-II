package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.design_patterns.facade.AppointmentFacade;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.Period;

public class ProfessionalCreateAppStep2Controller {

    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtSecondName;
    @FXML
    private TextField txtFirstLastName;
    @FXML
    private TextField txtSecondLastName;
    @FXML
    private ComboBox<String> cbxGender;
    @FXML
    private TextField txtPhone;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private Label lblAge;
    @FXML
    private TextField txtAppSummary;
    @FXML
    private TextArea txtObservation;

    private Appointment appointment;

    @FXML
    public void initialize() {
        cbxGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        configurarCalendario();
    }

    public void setAppointment(Appointment app) {
        this.appointment = app;
        if (app != null) {
            txtAppSummary.setText(app.getDate().toString() + " | " + app.getTime().toString());
        }
    }

    private void configurarCalendario() {
        dpBirthDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date == null || empty) return;

                if (date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eeeeee;");
                }
            }
        });
    }

    @FXML
    private void handleFindPatient(ActionEvent event) {
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty())
            return;

        try {
            Long ced = Long.parseLong(cedula);
            Patient patient = ServiceManager.getInstance().getPatientService().findByCed(ced);

            if (patient != null) {
                txtFirstName.setText(patient.getNamePatient());
                txtSecondName.setText(patient.getSecondNamePatient());
                txtFirstLastName.setText(patient.getLastNamePatient());
                txtSecondLastName.setText(patient.getSecondLastNamePatient());
                cbxGender.setValue(patient.getGenderPatient());
                txtPhone.setText(String.valueOf(patient.getPhonePatient()));
                dpBirthDate.setValue(patient.getDateBirthPatient());
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Paciente no encontrado",
                        "El paciente no existe. Por favor, complete los datos manualmente.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ingrese un número de cédula válido.");
        }
    }

    @FXML
    private void handleReturn(ActionEvent event) {
        NavigationService.getInstance().navigateTo("/fxml/ProfessionalCreateAppStep1.fxml", "Agendar Cita - Paso 1",
                (Button) event.getSource());
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (!validateFields())
            return;

        try {
            Patient patient = new Patient();
            patient.setIdPatient(Long.parseLong(txtCedula.getText().trim()));
            patient.setNamePatient(txtFirstName.getText().trim());
            patient.setLastNamePatient(txtFirstLastName.getText().trim());
            patient.setGenderPatient(cbxGender.getValue());
            patient.setPhonePatient(Long.parseLong(txtPhone.getText().trim()));
            patient.setDateBirthPatient(dpBirthDate.getValue());

            if (txtSecondName.getText() != null) {
                patient.setSecondNamePatient(txtSecondName.getText().trim());
            }
            if (txtSecondLastName.getText() != null) {
                patient.setSecondLastNamePatient(txtSecondLastName.getText().trim());
            }

            appointment.setDescription(txtObservation.getText().trim());

            int result = AppointmentFacade.getInstance().scheduleAppointment(patient, appointment);

            if (result == 0) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "¡Cita guardada con éxito!");
                NavigationService.getInstance().navigateTo("/fxml/ProfessionalMainView.fxml",
                        "Menú Principal - Profesional", (Button) event.getSource());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo guardar la cita. Intente de nuevo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Crítico", "Ocurrió un error al procesar la cita.");
        }
    }

    private boolean validateFields() {
        if (txtCedula.getText().trim().isEmpty() || txtFirstName.getText().trim().isEmpty() ||
                txtFirstLastName.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty() ||
                dpBirthDate.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validación", "Por favor, complete todos los campos obligatorios (*).");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
