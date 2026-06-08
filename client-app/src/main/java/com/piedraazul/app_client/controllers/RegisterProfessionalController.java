package com.piedraazul.app_client.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TextField     txtShowPassword;    // TextField visible cuando ojo activo (campo 1)
    @FXML private TextField     txtShowConPassword; // TextField visible cuando ojo activo (campo 2)
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
    @FXML private CheckBox chkMonday;
    @FXML private CheckBox chkTuesday;
    @FXML private CheckBox chkWednesday;
    @FXML private CheckBox chkThursday;
    @FXML private CheckBox chkFriday;

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



        ocultarErrores();

        // ── Sincronizar PasswordField ↔ TextField visible ────────────
        txtShowPassword.setVisible(false);
        txtShowPassword.setManaged(false);
        txtShowConPassword.setVisible(false);
        txtShowConPassword.setManaged(false);

        txtPassword.textProperty().addListener((obs, o, n) -> {
            if (!txtShowPassword.isFocused()) txtShowPassword.setText(n);
        });
        txtShowPassword.textProperty().addListener((obs, o, n) -> {
            if (!txtPassword.isFocused()) txtPassword.setText(n);
        });
        txtConPassword.textProperty().addListener((obs, o, n) -> {
            if (!txtShowConPassword.isFocused()) txtShowConPassword.setText(n);
        });
        txtShowConPassword.textProperty().addListener((obs, o, n) -> {
            if (!txtConPassword.isFocused()) txtConPassword.setText(n);
        });

        // ── Validación en tiempo real al perder el foco ──────────────

        txtNumCedula.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarCedula();
        });

        txtFirstName.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarPrimerNombre();
        });

        txtSecondName.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarSegundoNombre();
        });

        txtFirstLastName.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarPrimerApellido();
        });

        txtSecondLastName.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarSegundoApellido();
        });

        txtPhoneNumber.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarTelefono();
        });

        txtPassword.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarPassword();
        });

        txtConPassword.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarPassword();
        });

        txtArrivalTime.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarHorario();
        });

        txtDepartureTime.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarHorario();
        });

        txtAttentionInterval.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarIntervalo();
        });
    }

    // ── Métodos de validación individual ────────────────────────────

    private boolean validarCedula() {
        String val = txtNumCedula.getText().trim();
        if (val.isEmpty()) {
            lb_errorID.setText("Campo requerido"); lb_errorID.setVisible(true); return false;
        }
        try { Long.parseLong(val); }
        catch (NumberFormatException e) {
            lb_errorID.setText("Solo números"); lb_errorID.setVisible(true); return false;
        }
        txtNumCedula.setText(val);
        lb_errorID.setVisible(false); return true;
    }

    private boolean validarPrimerNombre() {
        String val = txtFirstName.getText().trim();
        if (val.isEmpty()) {
            lb_errorFirstName.setText("Campo requerido"); lb_errorFirstName.setVisible(true); return false;
        }
        if (!esNombreValido(val)) {
            lb_errorFirstName.setText("Caracteres inválidos"); lb_errorFirstName.setVisible(true); return false;
        }
        txtFirstName.setText(normalizarNombre(val));
        lb_errorFirstName.setVisible(false); return true;
    }

    private boolean validarSegundoNombre() {
        String val = txtSecondName.getText().trim();
        if (val.isEmpty()) { lb_errorSecondName.setVisible(false); return true; }
        if (!esNombreValido(val)) {
            lb_errorSecondName.setText("Caracteres inválidos"); lb_errorSecondName.setVisible(true); return false;
        }
        txtSecondName.setText(normalizarNombre(val));
        lb_errorSecondName.setVisible(false); return true;
    }

    private boolean validarPrimerApellido() {
        String val = txtFirstLastName.getText().trim();
        if (val.isEmpty()) {
            lb_errorFirstLastName.setText("Campo requerido"); lb_errorFirstLastName.setVisible(true); return false;
        }
        if (!esNombreValido(val)) {
            lb_errorFirstLastName.setText("Caracteres inválidos"); lb_errorFirstLastName.setVisible(true); return false;
        }
        txtFirstLastName.setText(normalizarNombre(val));
        lb_errorFirstLastName.setVisible(false); return true;
    }

    private boolean validarSegundoApellido() {
        String val = txtSecondLastName.getText().trim();
        if (val.isEmpty()) { lb_errorSecondLastName.setVisible(false); return true; }
        if (!esNombreValido(val)) {
            lb_errorSecondLastName.setText("Caracteres inválidos"); lb_errorSecondLastName.setVisible(true); return false;
        }
        txtSecondLastName.setText(normalizarNombre(val));
        lb_errorSecondLastName.setVisible(false); return true;
    }

    private boolean validarTelefono() {
        String val = txtPhoneNumber.getText().trim();
        if (val.isEmpty()) {
            lb_errorPhone.setText("Campo requerido"); lb_errorPhone.setVisible(true); return false;
        }
        try { Double.parseDouble(val); }
        catch (NumberFormatException e) {
            lb_errorPhone.setText("Solo números"); lb_errorPhone.setVisible(true); return false;
        }
        txtPhoneNumber.setText(val);
        lb_errorPhone.setVisible(false); return true;
    }

    private boolean validarPassword() {
        if (txtPassword.getText().isEmpty()) {
            lb_errorPassword.setText("Campo requerido"); lb_errorPassword.setVisible(true); return false;
        }
        if (!validarPasswordSegura(txtPassword.getText())) {
            lb_errorPassword.setText("Contraseña débil (6 car., mayús., número, especial)");
            lb_errorPassword.setVisible(true); return false;
        }
        if (!txtConPassword.getText().isEmpty() && !txtPassword.getText().equals(txtConPassword.getText())) {
            lb_errorPassword.setText("Las contraseñas no coinciden");
            lb_errorPassword.setVisible(true); return false;
        }
        lb_errorPassword.setVisible(false); return true;
    }

    private boolean validarHorario() {
        try {
            LocalTime arrival   = LocalTime.parse(txtArrivalTime.getText().trim(), TIME_FORMATTER);
            LocalTime departure = LocalTime.parse(txtDepartureTime.getText().trim(), TIME_FORMATTER);
            if (arrival.isAfter(departure)) {
                lb_errorSchedule.setText("Llegada no puede ser mayor que salida");
                lb_errorSchedule.setVisible(true); return false;
            }
        } catch (DateTimeParseException e) {
            lb_errorSchedule.setText("Formato HH:mm requerido"); lb_errorSchedule.setVisible(true); return false;
        }
        lb_errorSchedule.setVisible(false); return true;
    }

    private boolean validarIntervalo() {
        try {
            int interval = Integer.parseInt(txtAttentionInterval.getText().trim());
            if (interval <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            lb_errorSchedule.setText("Intervalo debe ser un número positivo");
            lb_errorSchedule.setVisible(true); return false;
        }
        lb_errorSchedule.setVisible(false); return true;
    }



    // ---- Mostrar/ocultar contraseña --------------------------------
    @FXML
    public void handleTogglePassword() {
        mostrarPassword = !mostrarPassword;

        // Campo 1
        txtPassword.setVisible(!mostrarPassword);
        txtPassword.setManaged(!mostrarPassword);
        txtShowPassword.setVisible(mostrarPassword);
        txtShowPassword.setManaged(mostrarPassword);

        // Campo 2
        txtConPassword.setVisible(!mostrarPassword);
        txtConPassword.setManaged(!mostrarPassword);
        txtShowConPassword.setVisible(mostrarPassword);
        txtShowConPassword.setManaged(mostrarPassword);

        btnEye.setText(mostrarPassword ? "🙈" : "👁");
    }

    @FXML
    public void handleSave() {
        ocultarErrores();
        if (!validarTodosCampos()) return;

        try {
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
                case "Terapia neural"   -> p.setSpecialityProf(SpecialityProfEnum.Neural_Therapy);
                case "Quiropraxia"      -> p.setSpecialityProf(SpecialityProfEnum.Chiropractor);
                case "Fisioterapia"     -> p.setSpecialityProf(SpecialityProfEnum.Physiotherapy);
                case "Medicina general" -> p.setSpecialityProf(SpecialityProfEnum.General);
            }

            java.util.List<String> days = new java.util.ArrayList<>();
            if (chkMonday.isSelected()) days.add("MONDAY");
            if (chkTuesday.isSelected()) days.add("TUESDAY");
            if (chkWednesday.isSelected()) days.add("WEDNESDAY");
            if (chkThursday.isSelected()) days.add("THURSDAY");
            if (chkFriday.isSelected()) days.add("FRIDAY");
            p.setUnavailableDays(String.join(",", days));

            var userRegistrado = ServiceManager.getInstance().getUserService().regUser(p);
            if (userRegistrado == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo registrar el usuario. Verifique los datos.");
                return;
            }
            p.setCodUser(userRegistrado.getCodUser());

            boolean profOk = ServiceManager.getInstance().getProfessionalService().register(p);
            if (!profOk) {
                showAlert(Alert.AlertType.WARNING, "Atención",
                        "El usuario fue creado pero no se pudo registrar el perfil profesional. " +
                                "Contacte al administrador.");
                return;
            }

            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Profesional registrado correctamente.");
            limpiarFormulario();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error inesperado", e.getMessage());
        }
    }

    @FXML
    public void handleCancel() {
        NavigationService.getInstance().navigateTo("/fxml/LoginView.fxml", "Piedra Azul - Login", btnCancel);
    }

    @FXML
    public void handleRegresar() {
        NavigationService.getInstance().navigateTo("/fxml/AdminView.fxml", "Piedra Azul - Administrador", btnRegresar);
    }

    private boolean validarTodosCampos() {
        boolean ok = true;
        ok = validarCedula()          && ok;
        ok = validarPrimerNombre()    && ok;
        ok = validarSegundoNombre()   && ok;
        ok = validarPrimerApellido()  && ok;
        ok = validarSegundoApellido() && ok;
        ok = validarTelefono()        && ok;
        ok = validarPassword()        && ok;
        ok = validarHorario()         && ok;
        ok = validarIntervalo()       && ok;

        if (!ok) showAlert(Alert.AlertType.WARNING, "Campos incompletos",
                "Por favor corrige los errores marcados en rojo.");
        return ok;
    }

    private boolean esNombreValido(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    }

    private String normalizarNombre(String texto) {
        if (texto == null || texto.isEmpty()) return texto;
        String sinTildes = java.text.Normalizer
                .normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        String lower = sinTildes.toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    private boolean validarPasswordSegura(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-]).{6,}$");
    }

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
        chkMonday.setSelected(false);
        chkTuesday.setSelected(false);
        chkWednesday.setSelected(false);
        chkThursday.setSelected(false);
        chkFriday.setSelected(false);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}