package DesignPatterns.state;

import models.Appointment;

/**
 * Estado RescheduledState: La cita ha sido reprogramada.
 * Desde este estado se pueden hacer transiciones a:
 * - Attended
 * - Rescheduled (a sí mismo)
 * - Canceled
 */
public class RescheduledState extends AppointmentState {
    
    public RescheduledState(Appointment appointment) {
        super(appointment);
    }

    public RescheduledState(AppointmentState source) {
        super(source);
    }

    /**
     * Transiciona la cita al estado Attended
     */
    public AppointmentState toAttended() {
        getContext().setState(new AttendedState(this));
        System.out.println("Cita transicionada de Rescheduled a: Attended");
        return getContext().getState();
    }

    /**
     * Transiciona la cita a otro estado Rescheduled
     * (puede reprogramarse múltiples veces)
     */
    public AppointmentState toRescheduled() {
        getContext().setState(new RescheduledState(this));
        System.out.println("Cita reprogramada nuevamente: Rescheduled");
        return getContext().getState();
    }

    /**
     * Transiciona la cita al estado Canceled
     */
    public AppointmentState toCanceled() {
        getContext().setState(new CanceledState(this));
        System.out.println("Cita transicionada de Rescheduled a: Canceled");
        return getContext().getState();
    }

    /**
     * Método específico del estado Rescheduled
     */
    public void updateRescheduleInfo(String newInfo) {
        System.out.println("Información de reprogramación actualizada: " + newInfo);
    }

    @Override
    public AppointmentState transitionState() {
        return getContext().getState();
    }

    @Override
    public String toString() {
        return "RescheduledState";
    }
}
