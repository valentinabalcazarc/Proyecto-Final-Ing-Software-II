package builder;

import enums.StatusAppointment;
import java.time.LocalDate;
import java.time.LocalTime;
import models.Appointment;

public class AppointmentDirector {

    private AppointmentBuilder appointmentBuilder;

    public void setAppointmentBuilder(AppointmentBuilder builder) {
        this.appointmentBuilder = builder;
    }

    public Appointment getAppointment() {
        return appointmentBuilder.getAppointment();
    }

    public void buildManualAppointment(int patientId, int professionalId, LocalDate date, LocalTime time, String description, StatusAppointment status) {

        appointmentBuilder.createNewAppointment();
        appointmentBuilder.buildPatientData(patientId);
        appointmentBuilder.buildProfessionalData(professionalId);
        appointmentBuilder.buildDate(date, time);
        appointmentBuilder.buildDetails(description, status);
    }

    public void buildSelfServiceAppointment(int patientId, int professionalId, LocalDate date, LocalTime time, String description) {

        appointmentBuilder.createNewAppointment();
        appointmentBuilder.buildPatientData(patientId);
        appointmentBuilder.buildProfessionalData(professionalId);
        appointmentBuilder.buildDate(date, time);
        appointmentBuilder.buildDetails(description, StatusAppointment.Scheduled);
    }

    public void buildRescheduledAppointment(int patientId, int professionalId, LocalDate date, LocalTime time, String description) {

        appointmentBuilder.createNewAppointment();
        appointmentBuilder.buildPatientData(patientId);
        appointmentBuilder.buildProfessionalData(professionalId);
        appointmentBuilder.buildDate(date, time);
        appointmentBuilder.buildDetails(description, StatusAppointment.Rescheduled);
    }
}