package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.design_patterns.builder.AppointmentBuilder;
import com.piedraazul.app_client.design_patterns.builder.AppointmentDirector;
import com.piedraazul.app_client.design_patterns.facade.AppointmentFacade;
import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ProfessionalAutomaticRecommendationController {

    private Patient patient;
    private SpecialityProfEnum specialityProf;
    private Appointment appointment;
    private Professional professional;

    @FXML
    private Label lblFecha;
    @FXML
    private Label lblHora;
    @FXML
    private Label lblProfesional;
    @FXML
    private Button btnConfirmar;

    public void setPatientAndSpeciality(Patient patient, SpecialityProfEnum specialityProf) {
        this.patient = patient;
        this.specialityProf = specialityProf;
        cargarRecomendacion();
    }

    private void cargarRecomendacion() {
        if (specialityProf != null) {
            this.appointment = ServiceManager.getInstance().getAppointmentService()
                    .getFirstAvailableBySpeciality(specialityProf);

            if (this.appointment == null
                    || this.appointment.getProfessionalId() == null
                    || this.appointment.getProfessionalName() == null) {
                lblFecha.setText("No hay citas disponibles próximamente.");
                lblHora.setText("");
                lblProfesional.setText("");
                btnConfirmar.setDisable(true);
                return;
            }

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", new Locale("es", "ES"));
            String fechaFormateada = appointment.getDate().format(formatoFecha);
            fechaFormateada = fechaFormateada.substring(0, 1).toUpperCase() + fechaFormateada.substring(1);

            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
            String horaFormateada = appointment.getTime().format(formatoHora);

            lblFecha.setText("📅 " + fechaFormateada);
            lblHora.setText("🕒 " + horaFormateada);
            lblProfesional.setText("👤 " + appointment.getProfessionalName());
            btnConfirmar.setDisable(false);
        }
    }

    @FXML
    private void handleConfirmar(ActionEvent event) {
        if (this.appointment == null || this.appointment.getProfessionalId() == null || this.patient == null) {
            mostrarAlerta("Error", "Faltan datos para agendar la cita.", Alert.AlertType.ERROR);
            return;
        }

        // ── Patrón Builder + Director: cita autónoma con receta predefinida ──
        AppointmentDirector director = new AppointmentDirector(new AppointmentBuilder());
        Appointment slotRef = new AppointmentBuilder()
                .setProfessionalId(this.appointment.getProfessionalId())
                .setDate(this.appointment.getDate())
                .setTime(this.appointment.getTime())
                .build();
        Appointment appToSchedule = director.buildAutonomousAppointment(
                slotRef, this.patient.getCodPatient());

        int result = AppointmentFacade.getInstance().scheduleAppointment(this.patient, appToSchedule);

        if (result == 0) {
            mostrarAlerta("Éxito", "¡Cita guardada con éxito!", Alert.AlertType.INFORMATION);

            NavigationService.getInstance().navigateTo(
                    "/fxml/ProfessionalMainView.fxml",
                    "Piedra Azul - Menú Principal",
                    (Button) event.getSource());
        } else if (result == 1) {
            mostrarAlerta("Error", "Error al registrar al nuevo paciente.", Alert.AlertType.ERROR);
        } else {
            mostrarAlerta("Error", "Error al registrar la cita.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleOtrosHorarios(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/ProfessionalSelectSpecificAppointmentView.fxml"));
            Parent root = loader.load();

            ProfessionalSelectSpecificAppointmentController controller = loader.getController();
            controller.setPatientAndSpeciality(patient, specialityProf);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Piedra Azul - Citas Específicas");
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("/fxml/stylesheet.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de citas.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleRegresar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfessionalManageAppointmentsView.fxml"));
            Parent root = loader.load();

            ProfessionalManageAppointmentsController controller = loader.getController();
            //controller.setPatient(patient);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Piedra Azul - Gestionar Citas");
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("/fxml/stylesheet.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver a la vista de Gestionar Citas.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}