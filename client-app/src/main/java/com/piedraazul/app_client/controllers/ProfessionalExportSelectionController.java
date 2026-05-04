package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.design_patterns.observer.ExportEventListener;
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
 */
public class ProfessionalExportSelectionController {

    @FXML
    private Button button_Json;

    @FXML
    private Button button_Html;

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

    /**
     * Genera el reporte de citas en el formato indicado y notifica
     * a los suscriptores (Observers) del evento "export".
     *
     * Si la ventana de resultados no existe o fue cerrada, la crea,
     * obtiene el controlador (Observer concreto) y lo suscribe al
     * EventManager. Luego notifica a todos los suscriptores.
     *
     * @param format el formato de exportación ("JSON" o "HTML")
     */
    private void generateReport(String format) {
        // Generar el contenido exportado según el formato
        String exportedContent = generateExportContent(format);

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
            exportResultStage.setScene(new Scene(root));
            exportResultStage.show();

            // Al cerrar la ventana, desuscribir el Observer del EventManager
            exportResultStage.setOnCloseRequest(event -> {
                events.unsubscribe("export", exportResultController);
                exportResultController = null;
                exportResultStage = null;
            });

        } catch (IOException e) {
            Logger.getLogger(ProfessionalExportSelectionController.class.getName())
                    .log(Level.SEVERE, "Error al abrir ventana de resultados de exportación", e);
        }
    }

    /**
     * Genera el contenido de exportación de las citas según el formato solicitado.
     */
    private String generateExportContent(String format) {
        StringBuilder sb = new StringBuilder();

        if ("JSON".equalsIgnoreCase(format)) {
            sb.append("[\n");
            for (int i = 0; i < appointmentList.size(); i++) {
                Appointment app = appointmentList.get(i);
                sb.append("  {\n");
                sb.append("    \"id\": ").append(app.getId()).append(",\n");
                sb.append("    \"paciente\": \"").append(nullSafe(app.getPatientName())).append("\",\n");
                sb.append("    \"profesional\": \"").append(nullSafe(app.getProfessionalName())).append("\",\n");
                sb.append("    \"especialidad\": \"").append(nullSafe(app.getSpecialityName())).append("\",\n");
                sb.append("    \"tipo\": \"").append(nullSafe(app.getTypeProfName())).append("\",\n");
                sb.append("    \"fecha\": \"").append(app.getDate()).append("\",\n");
                sb.append("    \"hora\": \"").append(app.getTime()).append("\",\n");
                sb.append("    \"estado\": \"").append(nullSafe(app.getStatus())).append("\",\n");
                sb.append("    \"descripcion\": \"").append(nullSafe(app.getDescription())).append("\"\n");
                sb.append("  }");
                if (i < appointmentList.size() - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            }
            sb.append("]");
        } else if ("HTML".equalsIgnoreCase(format)) {
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html lang=\"es\">\n");
            sb.append("<head>\n");
            sb.append("  <meta charset=\"UTF-8\">\n");
            sb.append("  <title>Citas Exportadas</title>\n");
            sb.append("  <style>\n");
            sb.append("    body { font-family: Arial, sans-serif; margin: 20px; }\n");
            sb.append("    h1 { color: #5947FF; }\n");
            sb.append("    table { border-collapse: collapse; width: 100%; }\n");
            sb.append("    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
            sb.append("    th { background-color: #5947FF; color: white; }\n");
            sb.append("    tr:nth-child(even) { background-color: #f2f2f2; }\n");
            sb.append("  </style>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            sb.append("  <h1>Reporte de Citas Médicas</h1>\n");
            sb.append("  <table>\n");
            sb.append("    <tr>\n");
            sb.append("      <th>ID</th><th>Paciente</th><th>Profesional</th>");
            sb.append("<th>Especialidad</th><th>Tipo</th><th>Fecha</th>");
            sb.append("<th>Hora</th><th>Estado</th><th>Descripción</th>\n");
            sb.append("    </tr>\n");
            for (Appointment app : appointmentList) {
                sb.append("    <tr>\n");
                sb.append("      <td>").append(app.getId()).append("</td>");
                sb.append("<td>").append(nullSafe(app.getPatientName())).append("</td>");
                sb.append("<td>").append(nullSafe(app.getProfessionalName())).append("</td>");
                sb.append("<td>").append(nullSafe(app.getSpecialityName())).append("</td>");
                sb.append("<td>").append(nullSafe(app.getTypeProfName())).append("</td>");
                sb.append("<td>").append(app.getDate()).append("</td>");
                sb.append("<td>").append(app.getTime()).append("</td>");
                sb.append("<td>").append(nullSafe(app.getStatus())).append("</td>");
                sb.append("<td>").append(nullSafe(app.getDescription())).append("</td>\n");
                sb.append("    </tr>\n");
            }
            sb.append("  </table>\n");
            sb.append("</body>\n");
            sb.append("</html>");
        }

        return sb.toString();
    }

    /**
     * Retorna una cadena vacía si el valor es null, o el valor mismo si no lo es.
     */
    private String nullSafe(String value) {
        return value != null ? value : "";
    }

    @FXML
    private void handleBack() {
        // Cerrar la ventana de resultados si está abierta
        if (exportResultStage != null && exportResultStage.isShowing()) {
            exportResultStage.close();
        }

        NavigationService.getInstance().navigateTo(
                "/fxml/ProfessionalExport.fxml",
                "Exportar Citas",
                button_Back);
    }
}