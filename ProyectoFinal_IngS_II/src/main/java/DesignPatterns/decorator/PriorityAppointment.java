package DesignPatterns.decorator;
import models.Appointment;

public class PriorityAppointment extends AppointmentDecorator {

    public PriorityAppointment(Appointment appointment) {
        super(appointment);
    }

    @Override
    public String getDescription() {
        return appointment.getDescription() + " [PRIORIDAD ALTA]";
    }
}