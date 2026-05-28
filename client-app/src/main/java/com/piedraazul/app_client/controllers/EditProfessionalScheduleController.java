package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.dto.UnavailableDayDTO;
import com.piedraazul.app_client.dto.UpdateProfessionalDTO;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EditProfessionalScheduleController {

    @FXML private Label     lblProfName;
    @FXML private TextField txtArrivalTime;
    @FXML private TextField txtDepartureTime;
    @FXML private TextField txtInterval;
    @FXML private Label     lblErrorSchedule;

    @FXML private DatePicker dpUnavailableDate;
    @FXML private TextField  txtUnavailableReason;
    @FXML private TableView<UnavailableDayRow>         tableUnavailableDays;
    @FXML private TableColumn<UnavailableDayRow, String> colUnavDate;
    @FXML private TableColumn<UnavailableDayRow, String> colUnavReason;
    @FXML private TableColumn<UnavailableDayRow, Void>   colUnavDelete;

    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private Professional professional;
    private final ObservableList<UnavailableDayRow> unavailableDays = FXCollections.observableArrayList();
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    // ----------------------------------------------------------------
    // Inicialización
    // ----------------------------------------------------------------

    @FXML
    public void initialize() {
        colUnavDate.setCellValueFactory(c -> c.getValue().dateStrProperty());
        colUnavReason.setCellValueFactory(c -> c.getValue().reasonProperty());

        // Columna eliminar: llama al backend si el día ya existe, o solo quita de la lista si es nuevo
        colUnavDelete.setCellFactory(col -> new TableCell<>() {
            private final Button btnDel = new Button("🗑");

            {
                btnDel.setStyle(
                        "-fx-background-color: #D94F4F; -fx-text-fill: white; " +
                                "-fx-background-radius: 5; -fx-cursor: hand; -fx-font-size: 11px;"
                );
                btnDel.setOnAction(e -> {
                    UnavailableDayRow row = getTableView().getItems().get(getIndex());
                    handleDeleteRow(row);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnDel);
            }
        });

        tableUnavailableDays.setItems(unavailableDays);
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
        unavailableDays.clear();
        List<UnavailableDayDTO> existing = ServiceManager.getInstance()
                .getUnavailableDayService()
                .getByProfessional(professional.getCodProf());

        for (UnavailableDayDTO dto : existing) {
            // isNew = false → ya existe en BD, el delete llamará al backend
            unavailableDays.add(new UnavailableDayRow(dto.getId(), dto.getDate(), dto.getReason(), false));
        }
    }

    // ----------------------------------------------------------------
    // Eliminar fila: si ya existe en BD hace DELETE, si es nueva solo la quita
    // ----------------------------------------------------------------

    private void handleDeleteRow(UnavailableDayRow row) {
        if (row.isNew()) {
            // Día agregado en esta sesión, no está en BD todavía → solo quitar de la lista
            unavailableDays.remove(row);
        } else {
            // Día ya guardado → confirmar y llamar al backend
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Eliminar día no laborable");
            confirm.setHeaderText(null);
            confirm.setContentText("¿Eliminar el día " + row.getDateStr() + " de los días no laborables?");
            confirm.showAndWait().ifPresent(btn -> {
                if (btn == ButtonType.OK) {
                    boolean ok = ServiceManager.getInstance()
                            .getUnavailableDayService()
                            .delete(row.getId());
                    if (ok) {
                        unavailableDays.remove(row);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error",
                                "No se pudo eliminar el día no laborable. Intenta de nuevo.");
                    }
                }
            });
        }
    }

    // ----------------------------------------------------------------
    // Agregar nuevo día no laborable a la tabla (local, aún no en BD)
    // ----------------------------------------------------------------

    @FXML
    private void handleAddUnavailableDay() {
        LocalDate date = dpUnavailableDate.getValue();
        if (date == null) {
            showAlert(Alert.AlertType.WARNING, "Fecha requerida",
                    "Selecciona una fecha para agregar el día no laborable.");
            return;
        }
        if (date.isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.WARNING, "Fecha inválida",
                    "No puedes agregar fechas anteriores a hoy.");
            return;
        }
        boolean duplicada = unavailableDays.stream()
                .anyMatch(r -> r.getDate().equals(date));
        if (duplicada) {
            showAlert(Alert.AlertType.WARNING, "Fecha duplicada",
                    "Esa fecha ya está en la lista.");
            return;
        }

        String reason = txtUnavailableReason.getText().trim();
        // isNew = true → se enviará al backend al guardar
        unavailableDays.add(new UnavailableDayRow(null, date, reason.isEmpty() ? null : reason, true));
        dpUnavailableDate.setValue(null);
        txtUnavailableReason.clear();
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

        boolean scheduleOk = ServiceManager.getInstance()
                .getProfessionalService()
                .updateSchedule(professional.getCodProf(), dto);

        if (!scheduleOk) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "No se pudo actualizar el horario del profesional.");
            return;
        }

        // Guardar solo los días NUEVOS (isNew = true)
        int fallidos = 0;
        for (UnavailableDayRow row : unavailableDays) {
            if (!row.isNew()) continue;   // los existentes ya están en BD

            UnavailableDayDTO unavDto = new UnavailableDayDTO();
            unavDto.setCodProf(professional.getCodProf());
            unavDto.setDate(row.getDate());
            unavDto.setReason(row.getReason());

            boolean ok = ServiceManager.getInstance()
                    .getUnavailableDayService()
                    .create(unavDto);
            if (!ok) fallidos++;
        }

        if (fallidos > 0) {
            showAlert(Alert.AlertType.WARNING, "Atención",
                    "Horario actualizado, pero " + fallidos +
                            " día(s) no laborable(s) nuevos no se pudieron guardar.");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Éxito",
                    "Horario actualizado correctamente.");
        }

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


    public static class UnavailableDayRow {
        private final Long id;                         // null si es nuevo (aún no en BD)
        private final LocalDate date;
        private final SimpleStringProperty dateStr;
        private final SimpleStringProperty reason;
        private final boolean isNew;                   // true = recién agregado en esta sesión

        public UnavailableDayRow(Long id, LocalDate date, String reason, boolean isNew) {
            this.id     = id;
            this.date   = date;
            this.dateStr = new SimpleStringProperty(date.toString());
            this.reason  = new SimpleStringProperty(reason != null ? reason : "");
            this.isNew   = isNew;
        }

        public Long    getId()       { return id; }
        public boolean isNew()       { return isNew; }
        public LocalDate getDate()   { return date; }
        public String  getDateStr()  { return dateStr.get(); }
        public SimpleStringProperty dateStrProperty() { return dateStr; }
        public String  getReason()   { return reason.get(); }
        public SimpleStringProperty reasonProperty()  { return reason; }
    }
}