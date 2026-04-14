package DesignPatterns.state;

import models.Appointment;

/**
 * Estado CreatedState: Estado inicial de una cita.
 * Desde este estado se pueden hacer transiciones a:
 * - Attended
 * - Rescheduled
 * - NoAttended
 * - Canceled
 */
public class CreatedState extends AppointmentState {
    
    public CreatedState(Appointment appointment) {
        super(appointment);
    }

    public CreatedState(AppointmentState source) {
        super(source);
    }

    /**
     * Transiciona la cita al estado Attended
     */
    public AppointmentState toAttended() {
        getContext().setState(new AttendedState(this));
        System.out.println("Cita transicionada a: Attended");
        return getContext().getState();
    }

    /**
     * Transiciona la cita al estado Rescheduled
     */
    public AppointmentState toRescheduled() {
        getContext().setState(new RescheduledState(this));
        System.out.println("Cita transicionada a: Rescheduled");
        return getContext().getState();
    }

    /**
     * Transiciona la cita al estado NoAttended
     */
    public AppointmentState toNoAttended() {
        getContext().setState(new NoAttendedState(this));
        System.out.println("Cita transicionada a: NoAttended");
        return getContext().getState();
    }

    /**
     * Transiciona la cita al estado Canceled
     */
    public AppointmentState toCanceled() {
        getContext().setState(new CanceledState(this));
        System.out.println("Cita transicionada a: Canceled");
        return getContext().getState();
    }

    @Override
    public AppointmentState transitionState() {
        // En este estado, las transiciones se hacen explícitamente
        return getContext().getState();
    }
}
