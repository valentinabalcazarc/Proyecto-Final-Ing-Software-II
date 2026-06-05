package com.piedraazul.app_client.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.piedraazul.app_client.dto.UnavailableDayDTO;
import com.piedraazul.app_client.enums.RoleUserEnum;
import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.enums.StatusUserEnum;
import com.piedraazul.app_client.enums.TypeProfEnum;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RegisterProfessionalController {

    // --- Campos del formulario principal ---
    @FXML private TextField txtNumCedula;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtSecondName;
    @FXML private TextField txtFirstLastName;
    @FXML private TextField txtSecondLastName;
    @FXML private TextField txtPhoneNumber;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConPassword;
    @FXML private TextField txtSecurityAnswer;
    @FXML private TextField txtArrivalTime;
    @FXML private TextField txtDepartureTime;
    @FXML private TextField txtAttentionInterval;

    @FXML private ComboBox<String> cbxTipoProf;
    @FXML private ComboBox<String> cbxEspecialidad;
    @FXML private ComboBox<String> cbxSecurityQuestion;
    @FXML private ComboBox<String> cbxGenero;

    // --- Etiquetas de error ---
    @FXML private Label lb_errorID;
    @FXML private Label lb_errorFirstName;
    @FXML private Label lb_errorSecondName;
    @FXML private Label lb_errorFirstLastName;
    @FXML private Label lb_errorSecondLastName;
    @FXML private Label lb_errorPhone;
    @FXML private Label lb_errorPassword;
    @FXML private Label lb_errorSchedule;

    @FXML private Button btnEye;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Button btnRegresar;

    // --- Sección días no laborables ---
    @FXML private DatePicker dpUnavailableDate;
    @FXML private TextField txtUnavailableReason;
    @FXML private TableView<UnavailableDayRow> tableUnavailableDays;
    @FXML private TableColumn<UnavailableDayRow, String> colDate;
    @FXML private TableColumn<UnavailableDayRow, String> colReason;
    @FXML private TableColumn<UnavailableDayRow, Void> colDelete;

    private final ObservableList<UnavailableDayRow> unavailableDays = FXCollections.observableArrayList();

    private boolean mostrarPassword = false;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // ----------------------------------------------------------------
    @FXML
    public void initialize() {
        cbxTipoProf.setItems(FXCollections.observableArrayList("Doctor", "Terapeuta"));
        cbxTipoProf.getSelectionModel().selectFirst();

        cbxEspecialidad.setItems(FXCollections.observableArrayList(
                "Terapia neural", "Quiropraxia", "Fisioterapia", "Medicina general"));
        cbxEspecialidad.getSelectionModel().selectFirst();

        cbxSecurityQuestion.setItems(FXCollections.observableArrayList(
                "¿Cuál es el nombre de tu primera mascota?",
                "¿Cuál es el nombre de tu primer colegio?",
                "¿Cuál es el nombre del lugar donde naciste?"));
        cbxSecurityQuestion.getSelectionModel().selectFirst();

        cbxGenero.setItems(FXCollections.observableArrayList("Masculino", "Femenino", "Otro"));
        cbxGenero.getSelectionModel().selectFirst();

        // Configurar tabla días no laborables
        colDate.setCellValueFactory(data -> data.getValue().dateStrProperty());
        colReason.setCellValueFactory(data -> data.getValue().reasonProperty());

        colDelete.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("✕");
            {
                btn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                        "-fx-font-size: 11px; -fx-cursor: hand; -fx-padding: 2 8 2 8;");
                btn.setOnAction(e -> {
                    UnavailableDayRow row = getTableView().getItems().get(getIndex());
                    unavailableDays.remove(row);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tableUnavailableDays.setItems(unavailableDays);
        tableUnavailableDays.setPlaceholder(new Label("Aún no se han agregado días no laborables"));

        ocultarErrores();
    }

    // ---- Agregar día no laborable ----------------------------------
    @FXML
    public void handleAddUnavailableDay() {
        LocalDate date = dpUnavailableDate.getValue();
        if (date == null) {
            showAlert(Alert.AlertType.WARNING, "Fecha requerida", "Selecciona una fecha para el día no laborable.");
            return;
        }
        if (date.isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.WARNING, "Fecha inválida", "La fecha no puede ser anterior a hoy.");
            return;
        }
        boolean duplicado = unavailableDays.stream()
                .anyMatch(r -> r.getDate().equals(date));
        if (duplicado) {
            showAlert(Alert.AlertType.WARNING, "Fecha duplicada", "Esa fecha ya está en la lista.");
            return;
        }
        String reason = txtUnavailableReason.getText().trim();
        unavailableDays.add(new UnavailableDayRow(date, reason));
        dpUnavailableDate.setValue(null);
        txtUnavailableReason.clear();
    }

    // ---- Mostrar/ocultar contraseña --------------------------------
    @FXML
    public void handleTogglePassword() {
        mostrarPassword = !mostrarPassword;
        btnEye.setText(mostrarPassword ? "(O)" : "(-)");
    }

    // ---- Guardar ---------------------------------------------------
    @FXML
    public void handleSave() {
        ocultarErrores();
        if (!validarCampos()) return;

        try {
            // 1) Construir objeto Professional
            Professional p = new Professional();
            p.setCedUser((long) Integer.parseInt(txtNumCedula.getText().trim()));
            p.setPassUser(txtPassword.getText());
            p.setNameUser(txtFirstName.getText().trim());
            p.setSecondNameUser(txtSecondName.getText().trim());
            p.setLastNameUser(txtFirstLastName.getText().trim());
            p.setSecondLastNameUser(txtSecondLastName.getText().trim());
            p.setPhoneProf(Long.parseLong(txtPhoneNumber.getText().trim()));
            p.setStatusUser(StatusUserEnum.Active);
            p.setRoleUser(RoleUserEnum.Professional);
            p.setSecurityQuestion(cbxSecurityQuestion.getValue());
            p.setSecurityAnswer(txtSecurityAnswer.getText().trim());
            p.setGenProf(cbxGenero.getValue());
            p.setArrivalTime(LocalTime.parse(txtArrivalTime.getText().trim(), TIME_FORMATTER));
            p.setDepartureTime(LocalTime.parse(txtDepartureTime.getText().trim(), TIME_FORMATTER));
            p.setAttentionInterval(Integer.parseInt(txtAttentionInterval.getText().trim()));

            switch (cbxTipoProf.getValue()) {
                case "Doctor"    -> p.setTypeProf(TypeProfEnum.Doctor);
                case "Terapeuta" -> p.setTypeProf(TypeProfEnum.Therapist);
            }
            switch (cbxEspecialidad.getValue()) {
                case "Terapia neural"    -> p.setSpecialityProf(SpecialityProfEnum.Neural_Therapy);
                case "Quiropraxia"       -> p.setSpecialityProf(SpecialityProfEnum.Chiropractor);
                case "Fisioterapia"      -> p.setSpecialityProf(SpecialityProfEnum.Physiotherapy);
                case "Medicina general"  -> p.setSpecialityProf(SpecialityProfEnum.General);
            }

            // 2) Registrar en auth-service
            var userRegistrado = ServiceManager.getInstance().getUserService().regUser(p);
            if (userRegistrado == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo registrar el usuario. Verifique los datos.");
                return;
            }
            p.setCodUser(userRegistrado.getCodUser());

            // 3) Registrar en people-service
            boolean profOk = ServiceManager.getInstance().getProfessionalService().register(p);
            if (!profOk) {
                showAlert(Alert.AlertType.WARNING, "Atención",
                        "El usuario fue creado pero no se pudo registrar el perfil profesional. " +
                                "Contacte al administrador.");
                return;
            }

            // 4) Registrar días no laborables (si se agregaron)
            if (!unavailableDays.isEmpty()) {
                Professional profRegistrado = ServiceManager.getInstance()
                        .getProfessionalService().findByCod(p.getCodUser());

                if (profRegistrado == null || profRegistrado.getCodProf() == null) {
                    showAlert(Alert.AlertType.WARNING, "Atención",
                            "Profesional registrado, pero no se pudieron guardar los días no laborables. " +
                                    "Puede agregarlos después desde su perfil.");
                } else {
                    int fallidos = 0;
                    for (UnavailableDayRow row : unavailableDays) {
                        UnavailableDayDTO dto = new UnavailableDayDTO();
                        dto.setCodProf(profRegistrado.getCodProf());
                        dto.setDate(row.getDate());
                        dto.setReason(row.getReason().isEmpty() ? null : row.getReason());
                        boolean ok = ServiceManager.getInstance().getUnavailableDayService().create(dto);
                        if (!ok) fallidos++;
                    }
                    if (fallidos > 0) {
                        showAlert(Alert.AlertType.WARNING, "Atención",
                                "Profesional registrado, pero " + fallidos + " día(s) no laborable(s) no se pudieron guardar.");
                        limpiarFormulario();
                        return;
                    }
                }
            }

            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Profesional registrado correctamente.");
            limpiarFormulario();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error inesperado", e.getMessage());
        }
    }

    // ---- Cancelar --------------------------------------------------
    @FXML
    public void handleCancel() {
        NavigationService.getInstance().navigateTo("/fxml/LoginView.fxml", "Piedra Azul - Login", btnCancel);
    }

    // ---- Regresar al Admin -----------------------------------------
    @FXML
    public void handleRegresar() {
        NavigationService.getInstance().navigateTo("/fxml/AdminView.fxml", "Piedra Azul - Administrador", btnRegresar);
    }

    // ================================================================
    // Validaciones
    // ================================================================
    private boolean validarCampos() {
        boolean ok = true;

        if (txtNumCedula.getText().trim().isEmpty()) {
            lb_errorID.setText("Campo requerido"); lb_errorID.setVisible(true); ok = false;
        } else {
            try { Long.parseLong(txtNumCedula.getText().trim()); }
            catch (NumberFormatException e) { lb_errorID.setText("Solo números"); lb_errorID.setVisible(true); ok = false; }
        }

        if (txtFirstName.getText().trim().isEmpty()) {
            lb_errorFirstName.setText("Campo requerido"); lb_errorFirstName.setVisible(true); ok = false;
        } else if (!esNombreValido(txtFirstName.getText().trim())) {
            lb_errorFirstName.setText("Caracteres inválidos"); lb_errorFirstName.setVisible(true); ok = false;
        }

        if (!txtSecondName.getText().trim().isEmpty() && !esNombreValido(txtSecondName.getText().trim())) {
            lb_errorSecondName.setText("Caracteres inválidos"); lb_errorSecondName.setVisible(true); ok = false;
        }

        if (txtFirstLastName.getText().trim().isEmpty()) {
            lb_errorFirstLastName.setText("Campo requerido"); lb_errorFirstLastName.setVisible(true); ok = false;
        } else if (!esNombreValido(txtFirstLastName.getText().trim())) {
            lb_errorFirstLastName.setText("Caracteres inválidos"); lb_errorFirstLastName.setVisible(true); ok = false;
        }

        if (!txtSecondLastName.getText().trim().isEmpty() && !esNombreValido(txtSecondLastName.getText().trim())) {
            lb_errorSecondLastName.setText("Caracteres inválidos"); lb_errorSecondLastName.setVisible(true); ok = false;
        }

        if (txtPhoneNumber.getText().trim().isEmpty()) {
            lb_errorPhone.setText("Campo requerido"); lb_errorPhone.setVisible(true); ok = false;
        } else {
            try { Double.parseDouble(txtPhoneNumber.getText().trim()); }
            catch (NumberFormatException e) { lb_errorPhone.setText("Solo números"); lb_errorPhone.setVisible(true); ok = false; }
        }

        if (txtPassword.getText().isEmpty()) {
            lb_errorPassword.setText("Campo requerido"); lb_errorPassword.setVisible(true); ok = false;
        } else if (!validarPasswordSegura(txtPassword.getText())) {
            lb_errorPassword.setText("Contraseña débil (6 car., mayús., número, especial)"); lb_errorPassword.setVisible(true); ok = false;
        } else if (!txtPassword.getText().equals(txtConPassword.getText())) {
            lb_errorPassword.setText("Las contraseñas no coinciden"); lb_errorPassword.setVisible(true); ok = false;
        }

        try {
            LocalTime arrival   = LocalTime.parse(txtArrivalTime.getText().trim(), TIME_FORMATTER);
            LocalTime departure = LocalTime.parse(txtDepartureTime.getText().trim(), TIME_FORMATTER);
            if (arrival.isAfter(departure)) {
                lb_errorSchedule.setText("Llegada no puede ser mayor que salida");
                lb_errorSchedule.setVisible(true); ok = false;
            }
        } catch (DateTimeParseException e) {
            lb_errorSchedule.setText("Formato HH:mm requerido"); lb_errorSchedule.setVisible(true); ok = false;
        }

        try {
            int interval = Integer.parseInt(txtAttentionInterval.getText().trim());
            if (interval <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            lb_errorSchedule.setText("Intervalo debe ser un número positivo");
            lb_errorSchedule.setVisible(true); ok = false;
        }

        if (!ok) showAlert(Alert.AlertType.WARNING, "Campos incompletos",
                "Por favor corrige los errores marcados en rojo.");
        return ok;
    }

    private boolean esNombreValido(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    }

    private boolean validarPasswordSegura(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-]).{6,}$");
    }

    // ================================================================
    // Utilidades
    // ================================================================
    private void ocultarErrores() {
        lb_errorID.setVisible(false);
        lb_errorFirstName.setVisible(false);
        lb_errorSecondName.setVisible(false);
        lb_errorFirstLastName.setVisible(false);
        lb_errorSecondLastName.setVisible(false);
        lb_errorPhone.setVisible(false);
        lb_errorPassword.setVisible(false);
        lb_errorSchedule.setVisible(false);
    }

    private void limpiarFormulario() {
        txtNumCedula.clear(); txtFirstName.clear(); txtSecondName.clear();
        txtFirstLastName.clear(); txtSecondLastName.clear(); txtPhoneNumber.clear();
        txtPassword.clear(); txtConPassword.clear(); txtSecurityAnswer.clear();
        txtArrivalTime.clear(); txtDepartureTime.clear(); txtAttentionInterval.clear();
        cbxTipoProf.getSelectionModel().selectFirst();
        cbxEspecialidad.getSelectionModel().selectFirst();
        cbxSecurityQuestion.getSelectionModel().selectFirst();
        cbxGenero.getSelectionModel().selectFirst();
        unavailableDays.clear();
        dpUnavailableDate.setValue(null);
        txtUnavailableReason.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // ================================================================
    // Clase interna para filas de la tabla
    // ================================================================
    public static class UnavailableDayRow {
        private final LocalDate date;
        private final SimpleStringProperty dateStr;
        private final SimpleStringProperty reason;

        public UnavailableDayRow(LocalDate date, String reason) {
            this.date = date;
            this.dateStr = new SimpleStringProperty(date.toString());
            this.reason = new SimpleStringProperty(reason != null ? reason : "");
        }

        public LocalDate getDate() { return date; }
        public String getDateStr() { return dateStr.get(); }
        public SimpleStringProperty dateStrProperty() { return dateStr; }
        public String getReason() { return reason.get(); }
        public SimpleStringProperty reasonProperty() { return reason; }
    }
}