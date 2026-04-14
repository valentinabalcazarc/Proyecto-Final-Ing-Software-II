package DesignPatterns.state;

import models.Appointment;

/**
 * Estado CanceledState: La cita ha sido cancelada.
 * Este es un estado terminal, no permite transiciones a otros estados.
 */
public class CanceledState extends AppointmentState {
    
    public CanceledState(Appointment appointment) {
        super(appointment);
    }

    public CanceledState(AppointmentState source) {
        super(source);
    }

    /**
     * Método específico del estado Canceled
     */
    public void markAsCanceled() {
        System.out.println("La cita ha sido cancelada");
    }

    @Override
    public AppointmentState transitionState() {
        // Este es un estado terminal, no permite transiciones
        System.out.println("Warning: No se pueden hacer transiciones desde estado Canceled");
        return getContext().getState();
    }

    @Override
    public String toString() {
        return "CanceledState";
    }
}
