package DesignPatterns.builder;

import DesignPatterns.state.AppointmentState;
import DesignPatterns.state.RescheduledState;
import java.time.LocalDate;
import java.time.LocalTime;

public class RescheduledAppointmentBuilder extends AppointmentBuilder {

    @Override
    public void buildPatientData(int patientId) {
        appointment.setPatientId(patientId);
    }

    @Override
    public void buildProfessionalData(int professionalId) {
        appointment.setProfessionalId(professionalId);
    }

    @Override
    public void buildDate(LocalDate date, LocalTime time) {
        appointment.setDate(date);
        appointment.setTime(time);
    }

    @Override
    public void buildDetails(String description, AppointmentState state) {
        appointment.setDescription("REPROGRAMADA: " + description);
        appointment.setState(new RescheduledState(appointment));
    }
}