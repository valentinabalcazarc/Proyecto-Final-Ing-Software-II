package com.piedraazul.app_client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import com.piedraazul.app_client.enums.RoleUserEnum;
import com.piedraazul.app_client.services.ServiceManager;

public class LoginController {

    @FXML private TextField txtUser_login;
    @FXML private PasswordField txtPass_Login;

    @FXML
    public void handleLogin() {
        String user = txtUser_login.getText();
        String pass = txtPass_Login.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            showAlert("Campos vacíos", "Por favor ingrese usuario y contraseña");
            return;
        }

        try {
            int ced = Integer.parseInt(user);
            RoleUserEnum rol = (RoleUserEnum) ServiceManager.getInstance().getUserService().authUser(ced, pass);
            
            if(rol != null) {
                System.out.println("Login exitoso: " + rol);
                redirectToMainView(rol);
            } else {
                showAlert("Error de autenticación", "Usuario o contraseña incorrectos");
            }
        } catch (NumberFormatException e) {
             showAlert("Error", "La cédula debe ser numérica");
        } catch (Exception e) {
            showAlert("Error", "Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void redirectToMainView(RoleUserEnum role) {
        String fxmlPath = "";
        String title = "Piedra Azul - ";

        switch (role) {
            case Admin:
                fxmlPath = "/fxml/AdminView.fxml";
                title += "Administración";
                break;
            case Patient:
                fxmlPath = "/fxml/MenuPrincipalPaciente.fxml";
                title += "Paciente";
                break;
            case Professional:
                fxmlPath = "/fxml/ProfessionalMainView.fxml";
                title += "Profesional";
                break;
        }

        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxmlPath));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) txtUser_login.getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new javafx.scene.Scene(root));
            stage.centerOnScreen();
        } catch (java.io.IOException e) {
            showAlert("Error de navegación", "No se pudo cargar la vista: " + fxmlPath);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}