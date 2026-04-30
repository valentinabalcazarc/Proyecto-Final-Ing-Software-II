package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.services.FestivosService;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public class PatientInfoController {

    @FXML private TextField txtCedula;
    @FXML private TextField txtPrimerNombre;
    @FXML private TextField txtSegundoNombre;
    @FXML private TextField txtPrimerApellido;
    @FXML private TextField txtSegundoApellido;
    @FXML private TextField txtCelular;
    @FXML private TextField txtEdad;
    @FXML private ComboBox<String> cbxGenero;
    @FXML private DatePicker dpFechaNacimiento;

    @FXML private Label lblErrorCedula;
    @FXML private Label lblErrorPrimerNombre;
    @FXML private Label lblErrorSegundoNombre;
    @FXML private Label lblErrorPrimerApellido;
    @FXML private Label lblErrorSegundoApellido;
    @FXML private Label lblErrorCelular;
    @FXML private Label lblErrorFecha;

    private final FestivosService festivosService = new FestivosService();

    @FXML
    public void initialize() {
        cbxGenero.getItems().addAll("Male", "Female", "Other");
        cbxGenero.getSelectionModel().selectFirst();
        
        txtEdad.setEditable(false);
        txtEdad.setDisable(true);
        txtEdad.setStyle("-fx-opacity: 1; -fx-text-fill: black; -fx-background-color: #cccccc;");

        ocultarErrores();
        configurarValidaciones();
        configurarCalendario();
    }

    private void ocultarErrores() {
        lblErrorCedula.setVisible(false);
        lblErrorPrimerNombre.setVisible(false);
        lblErrorSegundoNombre.setVisible(false);
        lblErrorPrimerApellido.setVisible(false);
        lblErrorSegundoApellido.setVisible(false);
        lblErrorCelular.setVisible(false);
        lblErrorFecha.setVisible(false);
    }

    private void configurarValidaciones() {
        configurarValidacionCampo(txtCedula, lblErrorCedula, "NUMERICO", true);
        configurarValidacionCampo(txtPrimerNombre, lblErrorPrimerNombre, "TEXTO", true);
        configurarValidacionCampo(txtPrimerApellido, lblErrorPrimerApellido, "TEXTO", true);
        configurarValidacionCampo(txtCelular, lblErrorCelular, "NUMERICO", true);
        
        configurarValidacionCampo(txtSegundoNombre, lblErrorSegundoNombre, "TEXTO", false);
        configurarValidacionCampo(txtSegundoApellido, lblErrorSegundoApellido, "TEXTO", false);

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
    }

    private void configurarValidacionCampo(TextField field, Label label, String tipo, boolean esObligatorio) {
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                String texto = field.getText().trim();
                boolean hayError = false;
                String mensaje = "";

                if (texto.isEmpty()) {
                    if (esObligatorio) {
                        hayError = true;
                        mensaje = "Campo requerido";
                    }
                } else {
                    if (tipo.equals("NUMERICO") && !texto.matches("\\d+")) {
                        hayError = true;
                        mensaje = "Solo números";
                    } else if (tipo.equals("TEXTO") && !texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                        hayError = true;
                        mensaje = "Solo letras";
                    }
                }
                label.setText(mensaje);
                label.setVisible(hayError);
            }
        });
    }

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

    @FXML
    private void handleBuscar(ActionEvent event) {
        try {
            Long cedPatient = Long.parseLong(txtCedula.getText().trim());
            Patient patient = ServiceManager.getInstance().getPatientService().findByCed(cedPatient);

            if (patient != null) {
                txtPrimerNombre.setText(patient.getNamePatient() != null ? patient.getNamePatient() : "");
                txtSegundoNombre.setText(patient.getSecondNamePatient() != null ? patient.getSecondNamePatient() : "");
                txtPrimerApellido.setText(patient.getLastNamePatient() != null ? patient.getLastNamePatient() : "");
                txtSegundoApellido.setText(patient.getSecondLastNamePatient() != null ? patient.getSecondLastNamePatient() : "");
                
                if (patient.getGenderPatient() != null && !patient.getGenderPatient().isEmpty()) {
                    cbxGenero.setValue(patient.getGenderPatient());
                }

                txtCelular.setText(patient.getPhonePatient() != 0 ? String.valueOf(patient.getPhonePatient()) : "");

                if (patient.getDateBirthPatient() != null) {
                    dpFechaNacimiento.setValue(patient.getDateBirthPatient());
                }
                
                ocultarErrores();

            } else {
                mostrarAlerta("Información", "El paciente no fue encontrado. Llene los datos manualmente.", Alert.AlertType.INFORMATION);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingrese un número de cédula válido.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleContinuar(ActionEvent event) {
        // Trigger focus lost to validate all fields
        txtCedula.getParent().requestFocus();

        if (lblErrorCedula.isVisible() || lblErrorPrimerNombre.isVisible() || 
            lblErrorPrimerApellido.isVisible() || lblErrorCelular.isVisible() ||
            lblErrorSegundoNombre.isVisible() || lblErrorSegundoApellido.isVisible() ||
            lblErrorFecha.isVisible() || txtCedula.getText().trim().isEmpty() ||
            txtPrimerNombre.getText().trim().isEmpty() || txtPrimerApellido.getText().trim().isEmpty() ||
            txtCelular.getText().trim().isEmpty() || dpFechaNacimiento.getValue() == null) {

            mostrarAlerta("Validación", "Por favor, corrija los errores o complete los campos requeridos.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Patient patient = new Patient();
            patient.setIdPatient(Long.parseLong(txtCedula.getText().trim()));
            patient.setNamePatient(txtPrimerNombre.getText().trim());
            patient.setSecondNamePatient(txtSegundoNombre.getText().trim());
            patient.setLastNamePatient(txtPrimerApellido.getText().trim());
            patient.setSecondLastNamePatient(txtSegundoApellido.getText().trim());
            patient.setPhonePatient(Long.parseLong(txtCelular.getText().trim()));
            patient.setDateBirthPatient(dpFechaNacimiento.getValue());
            patient.setGenderPatient(cbxGenero.getValue());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PatientSelectServiceView.fxml"));
            Parent root = loader.load();

            PatientSelectServiceController controller = loader.getController();
            controller.setPatient(patient);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Piedra Azul - Seleccionar Servicio");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error inesperado al cargar la siguiente vista.", Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, asegúrese de que la cédula y el celular sean números válidos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleRegresar(ActionEvent event) {
        NavigationService.getInstance().navigateTo("/fxml/PatientMainView.fxml", "Piedra Azul - Menú Principal", (Button) event.getSource());
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
