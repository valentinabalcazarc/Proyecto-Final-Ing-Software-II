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

        System.out.println("Intentando conectar: " + user);

        try {
            int ced = Integer.parseInt(user);
            RoleUserEnum rol = (RoleUserEnum) ServiceManager.getInstance().getUserService().authUser(ced, pass);
            if(rol != null) {
                System.out.println("Login exitoso: " + rol);
            }
        } catch (NumberFormatException e) {
             showAlert("Error", "La cédula debe ser numérica");
        }

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}