package DesignPatterns.state;

import models.Appointment;

/**
 * Estado AttendedState: La cita ha sido atendida.
 * Este es un estado terminal, no permite transiciones a otros estados.
 */
public class AttendedState extends AppointmentState {
    
    public AttendedState(Appointment appointment) {
        super(appointment);
    }

    public AttendedState(AppointmentState source) {
        super(source);
    }

    /**
     * Método específico del estado Attended
     */
    public void markAsAttended() {
        System.out.println("La cita ha sido marcada como: Attended");
    }

    @Override
    public AppointmentState transitionState() {
        // Este es un estado terminal, no permite transiciones
        System.out.println("Warning: No se pueden hacer transiciones desde estado Attended");
        return getContext().getState();
    }

    @Override
    public String toString() {
        return "AttendedState";
    }
}
