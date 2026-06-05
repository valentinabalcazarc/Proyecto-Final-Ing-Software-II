package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.models.Patient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import com.piedraazul.app_client.enums.RoleUserEnum;
import com.piedraazul.app_client.enums.StatusUserEnum;
import com.piedraazul.app_client.models.User;
import com.piedraazul.app_client.services.ServiceManager;

import java.time.LocalDate;
import java.time.Period;

public class RegisterUserController {

    // --- Campos del formulario ---
    @FXML private TextField     tF_userID;
    @FXML private TextField     tF_userFirstName;
    @FXML private TextField     tF_userSecondName;
    @FXML private TextField     tF_userFirstLastName;
    @FXML private TextField     tF_userSecondLastName;
    @FXML private PasswordField pF_password;
    @FXML private PasswordField pF_password2;
    @FXML private TextField     tF_showPassword;   // TextField visible cuando ojo activo (campo 1)
    @FXML private TextField     tF_showPassword2;  // TextField visible cuando ojo activo (campo 2)
    @FXML private TextField     tF_SecurityQuestion;
    @FXML private TextField txtCelular;
    @FXML private TextField txtEdad;
    @FXML private ComboBox<String> cbxGenero;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private ComboBox<String> cbx_SecurityQuestion;

    // --- Etiquetas de error ---
    @FXML private Label lb_errorID;
    @FXML private Label lb_errorFirstName;
    @FXML private Label lb_errorSecondName;
    @FXML private Label lb_errorSecondLastName;
    @FXML private Label lb_errorFirstLastName;
    @FXML private Label lb_errorPassword;
    @FXML private Label lblErrorCelular;
    @FXML private Label lblErrorFecha;

    @FXML private Button jb_Eye;
    @FXML private Button btn_Save;
    @FXML private Button btn_Cancel;
    @FXML private Button btnRegresar;

    private boolean mostrarPassword = false;

    // ----------------------------------------------------------------
    @FXML
    public void initialize() {
        cbx_SecurityQuestion.setItems(FXCollections.observableArrayList(
                "¿Cuál es el nombre de tu primera mascota?",
                "¿Cuál es el nombre de tu primer colegio?",
                "¿Cuál es el nombre del lugar donde naciste?"));
        cbx_SecurityQuestion.getSelectionModel().selectFirst();

        cbxGenero.getItems().addAll("Male", "Female", "Other");
        cbxGenero.getSelectionModel().selectFirst();

        txtEdad.setEditable(false);
        txtEdad.setDisable(true);
        txtEdad.setStyle("-fx-opacity: 1; -fx-text-fill: black; -fx-background-color: #cccccc;");

        dpFechaNacimiento.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblErrorFecha.setVisible(false);
                txtEdad.setText(String.valueOf(calcularEdad(newValue)));
            } else {
                lblErrorFecha.setVisible(true);
                lblErrorFecha.setText("Campo requerido");
                txtEdad.setText("");
            }
        });

        ocultarErrores();
        configurarCalendario();

        // ── Sincronizar PasswordField ↔ TextField visible ────────────
        // Los TextField visibles empiezan ocultos
        tF_showPassword.setVisible(false);
        tF_showPassword.setManaged(false);
        tF_showPassword2.setVisible(false);
        tF_showPassword2.setManaged(false);

        // Mantener texto sincronizado en ambas direcciones
        pF_password.textProperty().addListener((obs, o, n) -> {
            if (!tF_showPassword.isFocused()) tF_showPassword.setText(n);
        });
        tF_showPassword.textProperty().addListener((obs, o, n) -> {
            if (!pF_password.isFocused()) pF_password.setText(n);
        });
        pF_password2.textProperty().addListener((obs, o, n) -> {
            if (!tF_showPassword2.isFocused()) tF_showPassword2.setText(n);
        });
        tF_showPassword2.textProperty().addListener((obs, o, n) -> {
            if (!pF_password2.isFocused()) pF_password2.setText(n);
        });

        // ── Validación en tiempo real al perder el foco ──────────────

        tF_userID.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarID();
        });

        tF_userFirstName.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarPrimerNombre();
        });

        tF_userSecondName.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarSegundoNombre();
        });

        tF_userFirstLastName.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarPrimerApellido();
        });

        tF_userSecondLastName.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarSegundoApellido();
        });

        txtCelular.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarCelular();
        });

        pF_password.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarPassword();
        });

        pF_password2.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarPassword();
        });
    }

    // ── Métodos de validación individual ────────────────────────────

    private boolean validarID() {
        String val = tF_userID.getText().trim();
        if (val.isEmpty()) {
            lb_errorID.setText("Campo requerido"); lb_errorID.setVisible(true); return false;
        }
        try { Integer.parseInt(val); }
        catch (NumberFormatException e) {
            lb_errorID.setText("Solo números"); lb_errorID.setVisible(true); return false;
        }
        tF_userID.setText(val);
        lb_errorID.setVisible(false); return true;
    }

    private boolean validarPrimerNombre() {
        String val = tF_userFirstName.getText().trim();
        if (val.isEmpty()) {
            lb_errorFirstName.setText("Campo requerido"); lb_errorFirstName.setVisible(true); return false;
        }
        if (!esNombreValido(val)) {
            lb_errorFirstName.setText("Caracteres inválidos"); lb_errorFirstName.setVisible(true); return false;
        }
        tF_userFirstName.setText(normalizarNombre(val));
        lb_errorFirstName.setVisible(false); return true;
    }

    private boolean validarSegundoNombre() {
        String val = tF_userSecondName.getText().trim();
        if (!val.isEmpty() && !esNombreValido(val)) {
            lb_errorSecondName.setText("Caracteres inválidos"); lb_errorSecondName.setVisible(true); return false;
        }
        tF_userSecondName.setText(normalizarNombre(val));
        lb_errorSecondName.setVisible(false); return true;
    }

    private boolean validarPrimerApellido() {
        String val = tF_userFirstLastName.getText().trim();
        if (val.isEmpty()) {
            lb_errorFirstLastName.setText("Campo requerido"); lb_errorFirstLastName.setVisible(true); return false;
        }
        if (!esNombreValido(val)) {
            lb_errorFirstLastName.setText("Caracteres inválidos"); lb_errorFirstLastName.setVisible(true); return false;
        }
        tF_userFirstLastName.setText(normalizarNombre(val));
        lb_errorFirstLastName.setVisible(false); return true;
    }

    private boolean validarSegundoApellido() {
        String val = tF_userSecondLastName.getText().trim();
        if (!val.isEmpty() && !esNombreValido(val)) {
            lb_errorSecondLastName.setText("Caracteres inválidos"); lb_errorSecondLastName.setVisible(true); return false;
        }
        tF_userSecondLastName.setText(normalizarNombre(val));
        lb_errorSecondLastName.setVisible(false); return true;
    }

    private boolean validarCelular() {
        String val = txtCelular.getText().trim();
        if (val.isEmpty()) {
            lblErrorCelular.setText("Campo requerido"); lblErrorCelular.setVisible(true); return false;
        }
        try { Integer.parseInt(val); }
        catch (NumberFormatException e) {
            lblErrorCelular.setText("Solo números"); lblErrorCelular.setVisible(true); return false;
        }
        txtCelular.setText(val);
        lblErrorCelular.setVisible(false); return true;
    }

    private boolean validarPassword() {
        if (pF_password.getText().isEmpty()) {
            lb_errorPassword.setText("Campo requerido"); lb_errorPassword.setVisible(true); return false;
        }
        if (!validarPasswordSegura(pF_password.getText())) {
            lb_errorPassword.setText("Débil: 6 car., mayús., número, especial");
            lb_errorPassword.setVisible(true); return false;
        }
        if (!pF_password2.getText().isEmpty() && !pF_password.getText().equals(pF_password2.getText())) {
            lb_errorPassword.setText("Las contraseñas no coinciden");
            lb_errorPassword.setVisible(true); return false;
        }
        lb_errorPassword.setVisible(false); return true;
    }

    private boolean validarFecha() {
        if (dpFechaNacimiento.getValue() == null) {
            lblErrorFecha.setText("Campo requerido"); lblErrorFecha.setVisible(true); return false;
        }
        lblErrorFecha.setVisible(false); return true;
    }

    // ---- Mostrar/ocultar contraseña --------------------------------
    @FXML
    public void handleTogglePassword() {
        mostrarPassword = !mostrarPassword;

        // Campo 1
        pF_password.setVisible(!mostrarPassword);
        pF_password.setManaged(!mostrarPassword);
        tF_showPassword.setVisible(mostrarPassword);
        tF_showPassword.setManaged(mostrarPassword);

        // Campo 2
        pF_password2.setVisible(!mostrarPassword);
        pF_password2.setManaged(!mostrarPassword);
        tF_showPassword2.setVisible(mostrarPassword);
        tF_showPassword2.setManaged(mostrarPassword);

        jb_Eye.setText(mostrarPassword ? "🙈" : "👁");
    }

    // ---- Guardar ---------------------------------------------------
    @FXML
    public void handleSave() {
        ocultarErrores();
        if (!validarTodosCampos()) return;

        try {
            User u = new User();

            u.setCedUser(Long.parseLong(tF_userID.getText().trim()));
            u.setPassUser(pF_password.getText());
            u.setNameUser(tF_userFirstName.getText().trim());
            u.setSecondNameUser(tF_userSecondName.getText().trim());
            u.setLastNameUser(tF_userFirstLastName.getText().trim());
            u.setSecondLastNameUser(tF_userSecondLastName.getText().trim());
            u.setStatusUser(StatusUserEnum.Active);
            u.setRoleUser(RoleUserEnum.Patient);
            u.setSecurityQuestion(cbx_SecurityQuestion.getValue());
            u.setSecurityAnswer(tF_SecurityQuestion.getText().trim());

            Patient patient = new Patient();
            patient.setIdPatient(Long.parseLong(tF_userID.getText().trim()));
            patient.setNamePatient(tF_userFirstName.getText().trim());
            patient.setSecondNamePatient(tF_userSecondName.getText().trim());
            patient.setLastNamePatient(tF_userFirstLastName.getText().trim());
            patient.setSecondLastNamePatient(tF_userSecondLastName.getText().trim());
            patient.setPhonePatient(Long.parseLong(txtCelular.getText().trim()));
            patient.setDateBirthPatient(dpFechaNacimiento.getValue());
            patient.setGenderPatient(cbxGenero.getValue());

            var regUser = ServiceManager.getInstance().getUserService().regUser(u);

            if (regUser != null) {
                ServiceManager.getInstance().getUserService()
                        .authUser(u.getCedUser(), pF_password.getText());

                var regPat = ServiceManager.getInstance().getPatientService().regPatient(patient);

                if (regPat) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Registro exitoso.");
                    navigateTo("/fxml/LoginView.fxml", "Piedra Azul - Login", btn_Save);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Error al registrar paciente.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error inesperado", e.getMessage());
        }
    }

    // ---- Cancelar --------------------------------------------------
    @FXML
    public void handleCancel() {
        navigateTo("/fxml/LoginView.fxml", "Piedra Azul - Login", btn_Cancel);
    }

    // ---- Regresar --------------------------------------------------
    @FXML
    public void handleRegresar() {
        navigateTo("/fxml/LoginView.fxml", "Piedra Azul - Login", btnRegresar);
    }

    // ================================================================
    // Validación completa al presionar Guardar
    // ================================================================
    private boolean validarTodosCampos() {
        boolean ok = true;
        ok = validarID()            && ok;
        ok = validarPrimerNombre()  && ok;
        ok = validarSegundoNombre() && ok;
        ok = validarPrimerApellido()  && ok;
        ok = validarSegundoApellido() && ok;
        ok = validarCelular()       && ok;
        ok = validarPassword()      && ok;
        ok = validarFecha()         && ok;

        if (!ok) {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos",
                    "Por favor corrige los errores marcados.");
        }
        return ok;
    }

    // ================================================================
    // Helpers
    // ================================================================
    private int calcularEdad(LocalDate fechaNac) {
        if (fechaNac == null) return 0;
        return Period.between(fechaNac, LocalDate.now()).getYears();
    }

    private void configurarCalendario() {
        dpFechaNacimiento.setDayCellFactory(picker -> new DateCell() {
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
        lb_errorPassword.setVisible(false);
        lblErrorCelular.setVisible(false);
        lblErrorFecha.setVisible(false);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void navigateTo(String fxml, String title, Button sourceBtn) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) sourceBtn.getScene().getWindow();
            stage.setTitle(title);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/fxml/stylesheet.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}