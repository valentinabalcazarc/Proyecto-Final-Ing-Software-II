package com.piedraazul.app_client.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import com.piedraazul.app_client.enums.RoleUserEnum;
import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.enums.StatusUserEnum;
import com.piedraazul.app_client.enums.TypeProfEnum;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.ServiceManager;

public class RegisterProfessionalController {

    // --- Campos del formulario ---
    @FXML private TextField     txtNumCedula;
    @FXML private TextField     txtFirstName;
    @FXML private TextField     txtSecondName;
    @FXML private TextField     txtFirstLastName;
    @FXML private TextField     txtSecondLastName;
    @FXML private TextField     txtPhoneNumber;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConPassword;
    @FXML private TextField     txtSecurityAnswer;

    @FXML private ComboBox<String> cbxTipoProf;
    @FXML private ComboBox<String> cbxEspecialidad;
    @FXML private ComboBox<String> cbxSecurityQuestion;

    // --- Etiquetas de error ---
    @FXML private Label lb_errorID;
    @FXML private Label lb_errorFirstName;
    @FXML private Label lb_errorSecondName;
    @FXML private Label lb_errorFirstLastName;
    @FXML private Label lb_errorSecondLastName;
    @FXML private Label lb_errorPhone;
    @FXML private Label lb_errorPassword;

    @FXML private Button btnEye;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Button btnRegresar;

    private boolean mostrarPassword = false;

    // ----------------------------------------------------------------
    @FXML
    public void initialize() {
        cbxTipoProf.setItems(FXCollections.observableArrayList("Doctor", "Terapeuta"));
        cbxTipoProf.getSelectionModel().selectFirst();

        cbxEspecialidad.setItems(FXCollections.observableArrayList(
                "Terapia neural", "Quiropraxia", "Fisioterapia"));
        cbxEspecialidad.getSelectionModel().selectFirst();

        cbxSecurityQuestion.setItems(FXCollections.observableArrayList(
                "¿Cuál es el nombre de tu primera mascota?",
                "¿Cuál es el nombre de tu primer colegio?",
                "¿Cuál es el nombre del lugar donde naciste?"));
        cbxSecurityQuestion.getSelectionModel().selectFirst();

        ocultarErrores();
    }

    // ---- Mostrar/ocultar contraseña --------------------------------
    @FXML
    public void handleTogglePassword() {
        mostrarPassword = !mostrarPassword;
        // JavaFX no permite cambiar echoChar en PasswordField directamente;
        // la solución estándar es intercambiar con un TextField.
        // Aquí se muestra el estado en el botón como indicador visual.
        btnEye.setText(mostrarPassword ? "(O)" : "(-)");
        // Para implementar show/hide real, reemplaza PasswordField por TextField
        // en el FXML y controla la propiedad con un campo TextField auxiliar.
        // Ver patrón: https://edencoding.com/javafx-show-password/
    }

    // ---- Guardar ---------------------------------------------------
    @FXML
    public void handleSave() {
        ocultarErrores();
        if (!validarCampos()) return;

        try {
            Professional p = new Professional();

            p.setCedUser((long) Integer.parseInt(txtNumCedula.getText().trim()));
            p.setPassUser(txtPassword.getText());
            p.setNameUser(txtFirstName.getText().trim());
            p.setSecondNameUser(txtSecondName.getText().trim());
            p.setLastNameUser(txtFirstLastName.getText().trim());
            p.setSecondLastNameUser(txtSecondLastName.getText().trim());
            p.setPhoneProf(Double.parseDouble(txtPhoneNumber.getText().trim()));
            p.setStatusUser(StatusUserEnum.Active);
            p.setRoleUser(RoleUserEnum.Professional);
            p.setSecurityQuestion(cbxSecurityQuestion.getValue());
            p.setSecurityAnswer(txtSecurityAnswer.getText().trim());

            switch (cbxTipoProf.getValue()) {
                case "Doctor"    -> p.setTypeProf(TypeProfEnum.Doctor);
                case "Terapeuta" -> p.setTypeProf(TypeProfEnum.Therapist);
            }
            switch (cbxEspecialidad.getValue()) {
                case "Terapia neural" -> p.setSpecialityProf(SpecialityProfEnum.Neural_Therapy);
                case "Quiropraxia"    -> p.setSpecialityProf(SpecialityProfEnum.Chiropractor);
                case "Fisioterapia"   -> p.setSpecialityProf(SpecialityProfEnum.Physiotherapy);
            }

            boolean ok = ServiceManager.getInstance().getUserService().regUser(p);

            if (ok) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Registro exitoso.");
                limpiarFormulario();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Error al registrar. Verifique los datos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error inesperado", e.getMessage());
        }
    }

    // ---- Cancelar --------------------------------------------------
    @FXML
    public void handleCancel() {
        navigateTo("/fxml/LoginView.fxml", "Piedra Azul - Login", btnCancel);
    }

    // ---- Regresar al Admin -----------------------------------------
    @FXML
    public void handleRegresar() {
        navigateTo("/fxml/AdminView.fxml", "Piedra Azul - Administrador", btnRegresar);
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
            catch (NumberFormatException e) {
                lb_errorID.setText("Solo números"); lb_errorID.setVisible(true); ok = false;
            }
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
            catch (NumberFormatException e) {
                lb_errorPhone.setText("Solo números"); lb_errorPhone.setVisible(true); ok = false;
            }
        }

        if (txtPassword.getText().isEmpty()) {
            lb_errorPassword.setText("Campo requerido"); lb_errorPassword.setVisible(true); ok = false;
        } else if (!validarPasswordSegura(txtPassword.getText())) {
            lb_errorPassword.setText("Contraseña débil (6 car., mayús., número, especial)");
            lb_errorPassword.setVisible(true); ok = false;
        } else if (!txtPassword.getText().equals(txtConPassword.getText())) {
            lb_errorPassword.setText("Las contraseñas no coinciden");
            lb_errorPassword.setVisible(true); ok = false;
        }

        if (!ok) {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos",
                    "Por favor corrige los errores marcados en rojo.");
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
        lb_errorPhone.setVisible(false);
        lb_errorPassword.setVisible(false);
    }

    private void limpiarFormulario() {
        txtNumCedula.clear(); txtFirstName.clear(); txtSecondName.clear();
        txtFirstLastName.clear(); txtSecondLastName.clear();
        txtPhoneNumber.clear(); txtPassword.clear(); txtConPassword.clear();
        txtSecurityAnswer.clear();
        cbxTipoProf.getSelectionModel().selectFirst();
        cbxEspecialidad.getSelectionModel().selectFirst();
        cbxSecurityQuestion.getSelectionModel().selectFirst();
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
