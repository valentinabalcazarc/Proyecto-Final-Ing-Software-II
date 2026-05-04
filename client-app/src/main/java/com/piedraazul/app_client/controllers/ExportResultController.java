package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.design_patterns.observer.ExportEventListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Controlador de la ventana de resultados de exportación.
 * Actúa como Suscriptor/Observer concreto del patrón Observer.
 *
 * Implementa ExportEventListener para recibir notificaciones
 * cuando el usuario selecciona un formato de exportación desde la
 * ventana de selección ProfessionalExportSelectionController.
 *
 * Al recibir la notificación vía {@code update()}, actualiza
 * dinámicamente el contenido del TextArea y la etiqueta de formato.
 */
public class ExportResultController implements ExportEventListener {

    @FXML
    private TextArea textArea_ExportResult;

    @FXML
    private Label label_Format;

    @FXML
    public void initialize() {
        textArea_ExportResult.setText("Esperando selección de formato de exportación...");
    }

    /**
     * Método del patrón Observer invocado por el ExportEventManager.
     * cuando ocurre un evento de exportación.
     *
     * Actualiza la interfaz gráfica con el formato y contenido exportado.
     */
    @Override
    public void update(String format, String exportedContent) {
        // Platform.runLater asegura que la actualización del UI
        // se ejecute en el hilo de JavaFX
        Platform.runLater(() -> {
            label_Format.setText("Formato: " + format.toUpperCase());
            textArea_ExportResult.setText(exportedContent);
        });
    }
}
