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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SchedulerAutomaticRecommendationController {

    private Patient patient;
    private SpecialityProfEnum specialityProf;
    private Appointment appointment;
    private Appointment prevAppointment;
    private Professional professional;

    @FXML
    private Label lblFecha;
    @FXML
    private Label lblHora;
    @FXML
    private Label lblProfesional;
    @FXML
    private Button btnConfirmar;

    public void setPatientAndSpeciality(Patient patient, SpecialityProfEnum specialityProf,Appointment prevAppointment) {
        this.patient = patient;
        this.specialityProf = specialityProf;
        this.prevAppointment = prevAppointment;
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
            showAlert(Alert.AlertType.ERROR, "Error", "Faltan datos para agendar la cita.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de que desea reagendar esta cita?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            boolean success = ServiceManager.getInstance().getAppointmentService().deleteAppointment(prevAppointment.getId());
            if (success) {
                // ── Patrón Builder + Director ──
                AppointmentDirector director = new AppointmentDirector(new AppointmentBuilder());
                Appointment slotRef = new AppointmentBuilder()
                        .setProfessionalId(this.appointment.getProfessionalId())
                        .setDate(this.appointment.getDate())
                        .setTime(this.appointment.getTime())
                        .setStatus("Rescheduled")
                        .build();
                Appointment appToSchedule = director.buildAutonomousAppointment(
                        slotRef, this.patient.getCodPatient());

                int result = AppointmentFacade.getInstance().scheduleAppointment(this.patient, appToSchedule);

                if (result == 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "¡Cita reagendada con éxito!");
                    NavigationService.getInstance().navigateTo(
                            "/fxml/SchedulerMainView.fxml",
                            "Piedra Azul - Menú Principal",
                            (Button) event.getSource());
                } else if (result == 1) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Error al registrar al nuevo paciente.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Error al registrar la cita.");
                }

            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cancelar la cita.");
            }
        }
    }

    @FXML
    private void handleOtrosHorarios(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/SchedulerSelectSpecificAppointmentView.fxml"));
            Parent root = loader.load();

            SchedulerSelectSpecificAppointmentController controller = loader.getController();
            controller.setPatientAndSpeciality(patient, specialityProf,prevAppointment);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Piedra Azul - Citas Específicas");
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("/fxml/stylesheet.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de citas.");
        }
    }

    @FXML
    private void handleRegresar(ActionEvent event) {
        NavigationService.getInstance().navigateTo("/fxml/SchedulerManageAppointments.fxml", "Gestionar Citas",
                (Button) event.getSource());
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}