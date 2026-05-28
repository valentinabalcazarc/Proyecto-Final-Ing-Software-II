package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.design_patterns.decorator.export.AppointmentFormatter;
import com.piedraazul.app_client.design_patterns.decorator.export.BaseAppointmentFormatter;
import com.piedraazul.app_client.design_patterns.decorator.export.StatusHighlightDecorator;
import com.piedraazul.app_client.design_patterns.decorator.export.TimestampDecorator;
import com.piedraazul.app_client.design_patterns.observer.ExportEventManager;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.services.NavigationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador de la pantalla de selección de formato de exportación.
 * Actúa como Notificador concreto (Subject) del patrón Observer.
 *
 * Contiene una referencia ExportEventManager que gestiona los suscriptores
 * y los notifica cuando el usuario selecciona un formato de exportación.
 *
 * Utiliza el patrón Decorador para enriquecer el contenido exportado:
 *   BaseAppointmentFormatter  → contenido base (JSON o HTML)
 *   + TimestampDecorator      → agrega fecha/hora de exportación
 *   + StatusHighlightDecorator → resalta citas según su estado
 */
public class SchedulerExportSelectionController {

    @FXML
    private Button button_Json;

    @FXML
    private Button button_Html;

    @FXML
    private Button button_Csv;

    @FXML
    private Button button_Back;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    /**
     * EventManager del patrón Observer.
     * Gestiona la suscripción y notificación del evento "export".
     */
    public ExportEventManager events = new ExportEventManager("export");

    /**
     * Referencia a la ventana (Stage) de resultados de exportación.
     * Se mantiene para verificar si ya está abierta y evitar duplicados.
     */
    private Stage exportResultStage;

    /**
     * Referencia al controlador de resultados (Observer concreto).
     */
    private ExportResultController exportResultController;

    @FXML
    public void initialize() {

    }

    public void setAppointmentList(ObservableList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @FXML
    private void handleExportJson() {
        generateReport("JSON");
    }

    @FXML
    private void handleExportHtml() {
        generateReport("HTML");
    }

    @FXML
    private void handleExportCsv() {
        generateReport("CSV");
    }

    /**
     * Genera el reporte de citas en el formato indicado usando el
     * patrón Decorador, y notifica a los suscriptores (Observers).
     *
     * La cadena de decoradores es:
     *   BaseAppointmentFormatter
     *     → TimestampDecorator       (agrega fecha/hora de exportación)
     *     → StatusHighlightDecorator (resalta citas según su estado)
     *
     * Si la ventana de resultados no existe o fue cerrada, la crea y
     * suscribe el Observer antes de notificar.
     *
     * @param format el formato de exportación ("JSON", "HTML" o "CSV")
     */
    private void generateReport(String format) {
        // ── Patrón Decorador ──────────────────────────────────────────
        // Se construye la cadena de decoradores de adentro hacia afuera:
        // primero el componente base, luego se envuelve con cada decorador.
        AppointmentFormatter formatter = new BaseAppointmentFormatter();
        formatter = new TimestampDecorator(formatter);
        formatter = new StatusHighlightDecorator(formatter);

        String exportedContent = formatter.format(appointmentList, format);
        // ─────────────────────────────────────────────────────────────

        // Si la ventana de resultado no existe o fue cerrada, crearla y suscribir el Observer
        if (exportResultStage == null || !exportResultStage.isShowing()) {
            openExportResultWindow();
        }

        // Notificar a todos los suscriptores (Observer) con el evento "export"
        events.notifyListeners("export", format, exportedContent);
    }

    /**
     * Abre la ventana de resultados de exportación (ExportResult.fxml)
     * en un nuevo Stage. Obtiene el controlador y lo suscribe como
     * Observer al ExportEventManager.
     */
    private void openExportResultWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExportResult.fxml"));
            Parent root = loader.load();

            // Obtener el controlador que implementa ExportEventListener (Observer concreto)
            exportResultController = loader.getController();

            // Suscribir el Observer al evento "export" del EventManager
            events.subscribe("export", exportResultController);

            // Crear y mostrar la ventana de resultados
            exportResultStage = new Stage();
            exportResultStage.setTitle("Citas Exportadas");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/fxml/stylesheet.css").toExternalForm());
            exportResultStage.setScene(scene);
            exportResultStage.show();

            // Al cerrar la ventana, desuscribir el Observer del EventManager
            exportResultStage.setOnCloseRequest(event -> {
                events.unsubscribe("export", exportResultController);
                exportResultController = null;
                exportResultStage = null;
            });

        } catch (IOException e) {
            Logger.getLogger(SchedulerExportSelectionController.class.getName())
                    .log(Level.SEVERE, "Error al abrir ventana de resultados de exportación", e);
        }
    }

    @FXML
    private void handleBack() {
        // Cerrar la ventana de resultados si está abierta
        if (exportResultStage != null && exportResultStage.isShowing()) {
            exportResultStage.close();
        }

        NavigationService.getInstance().navigateTo(
                "/fxml/SchedulerExport.fxml",
                "Exportar Citas",
                button_Back);
    }
}