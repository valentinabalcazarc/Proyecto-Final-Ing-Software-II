package com.piedraazul.app_client.controllers;
/*
//import interfacePlugin.IReportPlugin;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
//import models.AppointmentRep;
//import plugin.manager.AppointmentPluginManager;
//import services.ServiceManager;
*/

import com.piedraazul.app_client.services.FestivosService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.List;

public class ProfessionalExportSelectionController {

    @FXML
    private TextArea textArea_Export;

    @FXML
    private Button button_Json;

    @FXML
    private Button button_Html;

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
    private void handleExportJson(/*ActionEvent event*/) {
        generateReport("json");
    }

    @FXML
    private void handleExportHtml(/*ActionEvent event*/) {
        generateReport("html");
    }

    private void generateReport(String format) {
        /*try {
            // Inicialización del manager de plugins (basado en tu código original)
            AppointmentPluginManager.init("src/main/resources/");
            AppointmentPluginManager manager = AppointmentPluginManager.getInstance();
            IReportPlugin plugin = manager.getReportPlugin(format);

            if (plugin != null && appointmentReps != null) {
                String datosPrint = plugin.generateReport(appointmentReps);
                textArea_Export.setText(datosPrint);
            }
        } catch (Exception ex) {
            Logger.getLogger(ExportFormatController.class.getName()).log(Level.SEVERE, "Error al generar reporte " + format, ex);
        }*/
    }

    @FXML
    private void handleBack(/*ActionEvent event*/) {
        NavigationService.getInstance().navigateTo(
                "/fxml/ProfessionalExport.fxml",
                "Exportar Citas",
                button_Back);
    }

}