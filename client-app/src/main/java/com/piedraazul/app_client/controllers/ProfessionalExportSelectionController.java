package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Controlador de la pantalla de selección de formato de exportación
 * para el rol Profesional.
 *
 * Utiliza el patrón Strategy implementado en el backend para generar
 * el archivo de exportación en el formato seleccionado (JSON, HTML, CSV).
 *
 * Al presionar un botón de formato:
 *   1. Envía los IDs de las citas visibles al endpoint POST /appointments/export
 *   2. Recibe los bytes del archivo generado por el backend
 *   3. Abre un FileChooser para que el usuario seleccione dónde guardar
 *   4. Escribe el archivo al disco
 */
public class ProfessionalExportSelectionController {

    @FXML
    private Button button_Json;

    @FXML
    private Button button_Html;

    @FXML
    private Button button_Csv;

    @FXML
    private Button button_Back;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

    }

    public void setAppointmentList(ObservableList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @FXML
    private void handleExportJson() {
        generateReport("json");
    }

    @FXML
    private void handleExportHtml() {
        generateReport("html");
    }

    @FXML
    private void handleExportCsv() {
        generateReport("csv");
    }

    /**
     * Genera el reporte de citas delegando al endpoint del backend
     * y descarga el archivo al disco del usuario.
     *
     * Envía solo los IDs de las citas actualmente visibles en pantalla
     * para exportar exactamente lo que el usuario ve.
     *
     * @param format el formato de exportación ("json", "html" o "csv")
     */
    private void generateReport(String format) {
        // Recolectar los IDs de las citas visibles
        List<Long> ids = appointmentList.stream()
                .map(Appointment::getId)
                .collect(Collectors.toList());

        // Llamar al backend para generar el archivo
        byte[] fileContent = ServiceManager.getInstance()
                .getAppointmentService()
                .exportFile(ids, format);

        if (fileContent == null) {
            showAlert(Alert.AlertType.ERROR, "Error de Exportación",
                    "No se pudo generar el archivo. Verifica la conexión con el servidor.");
            return;
        }

        // Abrir FileChooser para que el usuario seleccione dónde guardar
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo de exportación");
        fileChooser.setInitialFileName("citas_export." + format.toLowerCase());
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        format.toUpperCase() + " files", "*." + format.toLowerCase()));

        Stage stage = (Stage) button_Json.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(fileContent);
                showAlert(Alert.AlertType.INFORMATION, "Exportación Exitosa",
                        "El archivo se guardó correctamente en:\n" + file.getAbsolutePath());
            } catch (IOException e) {
                Logger.getLogger(ProfessionalExportSelectionController.class.getName())
                        .log(Level.SEVERE, "Error al guardar archivo de exportación", e);
                showAlert(Alert.AlertType.ERROR, "Error al Guardar",
                        "No se pudo guardar el archivo: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleBack() {
        NavigationService.getInstance().navigateTo(
                "/fxml/ProfessionalExport.fxml",
                "Exportar Citas",
                button_Back);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}