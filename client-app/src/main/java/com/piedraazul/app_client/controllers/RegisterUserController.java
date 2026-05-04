package com.piedraazul.app_client.controllers;

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

public class RegisterUserController {

    // --- Campos del formulario ---
    @FXML private TextField     tF_userID;
    @FXML private TextField     tF_userFirstName;
    @FXML private TextField     tF_userSecondName;
    @FXML private TextField     tF_userFirstLastName;
    @FXML private TextField     tF_userSecondLastName;
    @FXML private PasswordField pF_password;
    @FXML private PasswordField pF_password2;
    @FXML private TextField     tF_SecurityQuestion;

    @FXML private ComboBox<String> cbx_SecurityQuestion;

    // --- Etiquetas de error ---
    @FXML private Label lb_errorID;
    @FXML private Label lb_errorFirstName;
    @FXML private Label lb_errorSecondName;
    @FXML private Label lb_errorFirstLastName;
    @FXML private Label lb_errorSecondLastName;
    @FXML private Label lb_errorPassword;

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

        ocultarErrores();
    }

    // ---- Mostrar/ocultar contraseña --------------------------------
    @FXML
    public void handleTogglePassword() {
        mostrarPassword = !mostrarPassword;
        jb_Eye.setText(mostrarPassword ? "(O)" : "(-)");
        // Para una implementación completa de show/hide, usa un TextField auxiliar.
    }

    // ---- Guardar ---------------------------------------------------
    @FXML
    public void handleSave() {
        ocultarErrores();
        if (!validarCampos()) return;

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

            boolean ok = ServiceManager.getInstance().getUserService().regUser(u);

            if (ok) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Registro exitoso.");
                navigateTo("/fxml/LoginView.fxml", "Piedra Azul - Login", btn_Save);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Error al registrar. Intenta de nuevo.");
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
    // Validaciones
    // ================================================================
    private boolean validarCampos() {
        boolean ok = true;

        if (tF_userID.getText().trim().isEmpty()) {
            lb_errorID.setText("Campo requerido"); lb_errorID.setVisible(true); ok = false;
        } else {
            try { Integer.parseInt(tF_userID.getText().trim()); }
            catch (NumberFormatException e) {
                lb_errorID.setText("Solo números"); lb_errorID.setVisible(true); ok = false;
            }
        }

        if (tF_userFirstName.getText().trim().isEmpty()) {
            lb_errorFirstName.setText("Campo requerido"); lb_errorFirstName.setVisible(true); ok = false;
        } else if (!esNombreValido(tF_userFirstName.getText().trim())) {
            lb_errorFirstName.setText("Caracteres inválidos"); lb_errorFirstName.setVisible(true); ok = false;
        }

        if (!tF_userSecondName.getText().trim().isEmpty() && !esNombreValido(tF_userSecondName.getText().trim())) {
            lb_errorSecondName.setText("Caracteres inválidos"); lb_errorSecondName.setVisible(true); ok = false;
        }

        if (tF_userFirstLastName.getText().trim().isEmpty()) {
            lb_errorFirstLastName.setText("Campo requerido"); lb_errorFirstLastName.setVisible(true); ok = false;
        } else if (!esNombreValido(tF_userFirstLastName.getText().trim())) {
            lb_errorFirstLastName.setText("Caracteres inválidos"); lb_errorFirstLastName.setVisible(true); ok = false;
        }

        if (!tF_userSecondLastName.getText().trim().isEmpty() && !esNombreValido(tF_userSecondLastName.getText().trim())) {
            lb_errorSecondLastName.setText("Caracteres inválidos"); lb_errorSecondLastName.setVisible(true); ok = false;
        }

        if (pF_password.getText().isEmpty()) {
            lb_errorPassword.setText("Campo requerido"); lb_errorPassword.setVisible(true); ok = false;
        } else if (!validarPasswordSegura(pF_password.getText())) {
            lb_errorPassword.setText("Débil: 6 car., mayús., número, especial");
            lb_errorPassword.setVisible(true); ok = false;
        } else if (!pF_password.getText().equals(pF_password2.getText())) {
            lb_errorPassword.setText("Las contraseñas no coinciden");
            lb_errorPassword.setVisible(true); ok = false;
        }

        if (!ok) {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos",
                    "Por favor corrige los errores marcados.");
        }
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
        lb_errorPassword.setVisible(false);
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
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
