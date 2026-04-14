package DesignPatterns.state;

import models.Appointment;

/**
 * Define el comportamiento común de todos los estados de citas.
 * Es una clase base concreta que establece el contexto y permite transiciones de estado.
 */
public class AppointmentState {
    private Appointment context;

    public AppointmentState(Appointment appointment) {
        setContext(appointment);
    }

    public AppointmentState(AppointmentState source) {
        setContext(source.getContext());
    }

    public Appointment getContext() {
        return context;
    }

    public void setContext(Appointment newAppointment) {
        context = newAppointment;
    }

    /**
     * Método que debe implementar cada estado para definir sus transiciones posibles.
     * Regresa el nuevo estado después de la transición.
     */
    public AppointmentState transitionState() {
        return null;
    }

    /**
     * Estado inicial de una cita es Created
     */
    public static AppointmentState InitialState(Appointment appointment) {
        return new CreatedState(appointment);
    }
}
