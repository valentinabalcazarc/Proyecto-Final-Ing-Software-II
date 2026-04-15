package DesignPatterns.decorator;

import models.Appointment;
import DesignPatterns.state.AppointmentState;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class AppointmentDecorator extends Appointment {

    protected Appointment appointment;

    public AppointmentDecorator(Appointment appointment) {
        super();
        this.appointment = appointment;
    }

    @Override
    public String getDescription() {
        return appointment.getDescription();
    }

    @Override
    public int getPatientId() {
        return appointment.getPatientId();
    }

    @Override
    public AppointmentState getState() {
        return appointment.getState();
    }
    @Override
    public LocalDate getDate() {
        return appointment.getDate();
}

    @Override
    public LocalTime getTime() {
        return appointment.getTime();
}

    @Override
    public int getProfessionalId() {
    return appointment.getProfessionalId();
}

}

