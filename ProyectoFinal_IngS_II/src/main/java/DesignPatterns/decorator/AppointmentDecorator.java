package DesignPatterns.decorator;

import models.Appointment;
import DesignPatterns.state.AppointmentState;

public abstract class AppointmentDecorator extends Appointment {

    protected Appointment appointment;

    public AppointmentDecorator(Appointment appointment) {
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
}