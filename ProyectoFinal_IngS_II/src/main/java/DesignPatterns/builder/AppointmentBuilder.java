package DesignPatterns.builder;
import DesignPatterns.state.AppointmentState;
import java.time.LocalDate;
import java.time.LocalTime;
import models.Appointment;

public abstract class AppointmentBuilder {

    protected Appointment appointment;

    public void createNewAppointment() {
        appointment = new Appointment();
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public abstract void buildPatientData(int patientId);

    public abstract void buildProfessionalData(int professionalId);

    public abstract void buildDate(LocalDate date, LocalTime time);

    public abstract void buildDetails(String description, AppointmentState state);
}