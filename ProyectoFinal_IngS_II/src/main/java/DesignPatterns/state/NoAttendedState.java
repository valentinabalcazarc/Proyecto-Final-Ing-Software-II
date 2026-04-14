package DesignPatterns.state;

import models.Appointment;

/**
 * Estado NoAttendedState: La cita no fue atendida.
 * Este es un estado terminal, no permite transiciones a otros estados.
 */
public class NoAttendedState extends AppointmentState {
    
    public NoAttendedState(Appointment appointment) {
        super(appointment);
    }

    public NoAttendedState(AppointmentState source) {
        super(source);
    }

    /**
     * Método específico del estado NoAttended
     */
    public void recordNoShow() {
        System.out.println("Se ha registrado que la cita no fue atendida (No Show)");
    }

    @Override
    public AppointmentState transitionState() {
        // Este es un estado terminal, no permite transiciones
        System.out.println("Warning: No se pueden hacer transiciones desde estado NoAttended");
        return getContext().getState();
    }

    @Override
    public String toString() {
        return "NoAttendedState";
    }
}
