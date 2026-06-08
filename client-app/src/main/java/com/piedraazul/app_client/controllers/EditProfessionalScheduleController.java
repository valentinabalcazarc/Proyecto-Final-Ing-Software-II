package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.dto.UpdateProfessionalDTO;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditProfessionalScheduleController {

    @FXML private Label     lblProfName;
    @FXML private TextField txtArrivalTime;
    @FXML private TextField txtDepartureTime;
    @FXML private TextField txtInterval;
    @FXML private Label     lblErrorSchedule;

    @FXML private CheckBox chkMonday;
    @FXML private CheckBox chkTuesday;
    @FXML private CheckBox chkWednesday;
    @FXML private CheckBox chkThursday;
    @FXML private CheckBox chkFriday;

    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private Professional professional;
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    // ----------------------------------------------------------------
    // Inicialización
    // ----------------------------------------------------------------

    public void initialize() {
    }

    // ----------------------------------------------------------------
    // Setter: llamado desde ProfessionalScheduleController
    // ----------------------------------------------------------------

    public void setProfessional(Professional prof) {
        this.professional = prof;
        lblProfName.setText(prof.getFullName() + " — " + prof.getSpecialityProf());

        if (prof.getArrivalTime() != null)
            txtArrivalTime.setText(prof.getArrivalTime().format(TIME_FMT));
        if (prof.getDepartureTime() != null)
            txtDepartureTime.setText(prof.getDepartureTime().format(TIME_FMT));
        if (prof.getAttentionInterval() != null)
            txtInterval.setText(String.valueOf(prof.getAttentionInterval()));

        // Cargar días no laborables ya existentes del profesional
        loadExistingUnavailableDays();
    }

    // ----------------------------------------------------------------
    // Carga inicial de días ya guardados en el backend
    // ----------------------------------------------------------------

    private void loadExistingUnavailableDays() {
        if (professional.getUnavailableDays() != null && !professional.getUnavailableDays().isBlank()) {
            List<String> days = Arrays.asList(professional.getUnavailableDays().split(","));
            chkMonday.setSelected(days.contains("MONDAY"));
            chkTuesday.setSelected(days.contains("TUESDAY"));
            chkWednesday.setSelected(days.contains("WEDNESDAY"));
            chkThursday.setSelected(days.contains("THURSDAY"));
            chkFriday.setSelected(days.contains("FRIDAY"));
        } else {
            chkMonday.setSelected(false);
            chkTuesday.setSelected(false);
            chkWednesday.setSelected(false);
            chkThursday.setSelected(false);
            chkFriday.setSelected(false);
        }
    }

    // ----------------------------------------------------------------
    // Guardar cambios de horario + días nuevos
    // ----------------------------------------------------------------

    @FXML
    private void handleSave() {
        lblErrorSchedule.setVisible(false);

        LocalTime arrival, departure;
        int interval;

        try {
            arrival   = LocalTime.parse(txtArrivalTime.getText().trim(), TIME_FMT);
            departure = LocalTime.parse(txtDepartureTime.getText().trim(), TIME_FMT);
        } catch (DateTimeParseException e) {
            lblErrorSchedule.setText("Formato HH:mm requerido (ej: 08:00)");
            lblErrorSchedule.setVisible(true);
            return;
        }

        if (arrival.isAfter(departure) || arrival.equals(departure)) {
            lblErrorSchedule.setText("La hora de llegada debe ser menor que la de salida");
            lblErrorSchedule.setVisible(true);
            return;
        }

        try {
            interval = Integer.parseInt(txtInterval.getText().trim());
            if (interval <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            lblErrorSchedule.setText("Intervalo debe ser un número positivo");
            lblErrorSchedule.setVisible(true);
            return;
        }

        // Actualizar horario
        UpdateProfessionalDTO dto = new UpdateProfessionalDTO();
        dto.setArrivalTime(arrival);
        dto.setDepartureTime(departure);
        dto.setAttentionInterval(interval);

        List<String> days = new ArrayList<>();
        if (chkMonday.isSelected()) days.add("MONDAY");
        if (chkTuesday.isSelected()) days.add("TUESDAY");
        if (chkWednesday.isSelected()) days.add("WEDNESDAY");
        if (chkThursday.isSelected()) days.add("THURSDAY");
        if (chkFriday.isSelected()) days.add("FRIDAY");
        dto.setUnavailableDays(String.join(",", days));

        boolean scheduleOk = ServiceManager.getInstance()
                .getProfessionalService()
                .updateSchedule(professional.getCodProf(), dto);

        if (!scheduleOk) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "No se pudo actualizar el horario del profesional.");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Éxito",
                "Horario actualizado correctamente.");

        closeDialog();
    }

    // ----------------------------------------------------------------
    // Cancelar
    // ----------------------------------------------------------------

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    // ----------------------------------------------------------------
    // Utilidades
    // ----------------------------------------------------------------

    private void closeDialog() {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



}