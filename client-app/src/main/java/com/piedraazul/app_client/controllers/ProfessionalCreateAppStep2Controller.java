package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.design_patterns.builder.AppointmentBuilder;
import com.piedraazul.app_client.design_patterns.builder.AppointmentDirector;
import com.piedraazul.app_client.design_patterns.decorator.descApp.AppointmentTag;
import com.piedraazul.app_client.design_patterns.decorator.descApp.DescriptionTagDecorator;
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
import java.util.List;

public class ProfessionalCreateAppStep2Controller {

    // ── Campos del paciente ───────────────────────────────────────
    @FXML private TextField txtCedula;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtSecondName;
    @FXML private TextField txtFirstLastName;
    @FXML private TextField txtSecondLastName;
    @FXML private ComboBox<String> cbxGender;
    @FXML private TextField txtPhone;
    @FXML private DatePicker dpBirthDate;
    @FXML private Label lblAge;
    @FXML private TextField txtAppSummary;
    @FXML private TextArea txtObservation;
    @FXML private Button btnSave;

    // ── Checkboxes de tipo de cita (patrón Decorador) ────────────
    @FXML private CheckBox chkUrgente;
    @FXML private CheckBox chkPrioritaria;
    @FXML private CheckBox chkCitaControl;

    private Appointment appointment;
    private boolean hasAppointments = false;
    private Patient patient;

    @FXML
    public void initialize() {
        cbxGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        configurarCalendario();
        configurarCheckboxes();
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

        dpBirthDate.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int years = Period.between(newVal, LocalDate.now()).getYears();
                lblAge.setText(years + " años");
            }
        });
    }

    private void configurarCheckboxes() {
        chkUrgente.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                chkPrioritaria.setSelected(false);
                chkCitaControl.setSelected(false);
            }
        });

        chkPrioritaria.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                chkUrgente.setSelected(false);
                chkCitaControl.setSelected(false);
            }
        });

        chkCitaControl.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                chkUrgente.setSelected(false);
                chkPrioritaria.setSelected(false);
            }
        });
    }

    private AppointmentTag resolverTag() {
        if (chkUrgente.isSelected())      return AppointmentTag.URGENTE;
        if (chkPrioritaria.isSelected())  return AppointmentTag.PRIORITARIA;
        if (chkCitaControl.isSelected())  return AppointmentTag.CITA_CONTROL;
        return AppointmentTag.NINGUNA;
    }

    // ── Handlers FXML ─────────────────────────────────────────────

    @FXML
    private void handleFindPatient(ActionEvent event) {
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty()) return;
        try {
            Long ced = Long.parseLong(cedula);
            patient = ServiceManager.getInstance().getPatientService().findByCed(ced);

            if (patient != null) {
                txtFirstName.setText(patient.getNamePatient());
                txtSecondName.setText(patient.getSecondNamePatient());
                txtFirstLastName.setText(patient.getLastNamePatient());
                txtSecondLastName.setText(patient.getSecondLastNamePatient());
                cbxGender.setValue(patient.getGenderPatient());
                txtPhone.setText(String.valueOf(patient.getPhonePatient()));
                dpBirthDate.setValue(patient.getDateBirthPatient());
                verificarCitasPrevias();
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Paciente no encontrado",
                        "El paciente no existe. Por favor, complete los datos manualmente.");
                btnSave.setDisable(false);
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ingrese un número de cédula válido.");
        }
    }

    @FXML
    private void handleReturn(ActionEvent event) {
        NavigationService.getInstance().navigateTo("/fxml/ProfessionalCreateAppStep1.fxml",
                "Agendar Cita - Paso 1", (Button) event.getSource());
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (!validateFields()) return;

        try {
            Patient pat = new Patient();
            pat.setIdPatient(Long.parseLong(txtCedula.getText().trim()));
            pat.setNamePatient(txtFirstName.getText().trim());
            pat.setLastNamePatient(txtFirstLastName.getText().trim());
            pat.setGenderPatient(cbxGender.getValue());
            pat.setPhonePatient(Long.parseLong(txtPhone.getText().trim()));
            pat.setDateBirthPatient(dpBirthDate.getValue());

            if (txtSecondName.getText() != null)
                pat.setSecondNamePatient(txtSecondName.getText().trim());
            if (txtSecondLastName.getText() != null)
                pat.setSecondLastNamePatient(txtSecondLastName.getText().trim());

            // Patrón Decorador: calcula el tag de tipo de cita
            DescriptionTagDecorator decorator =
                    new DescriptionTagDecorator(resolverTag());

            // Aplicar el decorador sobre la descripción antes de construir
            appointment.setDescription(txtObservation.getText().trim());
            decorator.apply(appointment);
            String descDecored = appointment.getDescription();

            // ── Patrón Builder + Director: construye la cita final ───────────────
            AppointmentDirector director = new AppointmentDirector(new AppointmentBuilder());
            appointment = director.buildManualAppointment(
                    appointment,
                    null,   // patientId lo resuelve la Facade internamente
                    txtFirstName.getText().trim() + " " + txtFirstLastName.getText().trim(),
                    descDecored);

            int result = AppointmentFacade.getInstance().scheduleAppointment(pat, appointment);

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
            showAlert(Alert.AlertType.WARNING, "Validación",
                    "Por favor, complete todos los campos obligatorios (*).");
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

    private void verificarCitasPrevias() {
        if (patient == null || patient.getCodPatient() == null) {
            btnSave.setDisable(true);
            showAlert(Alert.AlertType.WARNING,
                    "Primera cita",
                    "Este paciente no tiene citas previas. Su primera cita debe ser de Consulta General.");
            return;
        }

        try {
            List<Appointment> citas = ServiceManager.getInstance()
                    .getAppointmentService()
                    .getAppointmentsByPatient(patient.getCodPatient());

            boolean tieneCitaAgendada = citas != null && citas.stream()
                    .anyMatch(a -> "Scheduled".equalsIgnoreCase(a.getStatus()));

            boolean tieneCitasPrevias = citas != null && !citas.isEmpty();

            if (tieneCitaAgendada) {
                btnSave.setDisable(true);
                showAlert(Alert.AlertType.WARNING,
                        "Cita ya agendada",
                        "Este paciente ya tiene una cita en estado Agendada. " +
                                "No es posible agendar otra hasta que sea atendida o cancelada.");
                return;
            }

            if (!tieneCitasPrevias) {
                btnSave.setDisable(true);
                showAlert(Alert.AlertType.WARNING,
                        "Primera cita",
                        "Este paciente no tiene citas previas. Su primera cita debe ser de Consulta General.");
                return;
            }

            btnSave.setDisable(false);

        } catch (Exception e) {
            System.err.println(">> Error al verificar citas del paciente: " + e.getMessage());
            btnSave.setDisable(false);
        }
    }
}