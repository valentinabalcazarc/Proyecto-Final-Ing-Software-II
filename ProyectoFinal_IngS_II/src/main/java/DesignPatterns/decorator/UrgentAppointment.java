package DesignPatterns.decorator;

import models.Appointment;

public class UrgentAppointment extends AppointmentDecorator {

    public UrgentAppointment(Appointment appointment) {
        super(appointment);
    }

    @Override
    public String getDescription() {
        return appointment.getDescription() + " [URGENTE]";
    }
    @Override
    public String toString() {
        return "UrgentAppointment";
}
}