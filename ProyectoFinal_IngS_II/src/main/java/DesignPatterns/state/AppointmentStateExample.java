package DesignPatterns.state;

import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Ejemplo de uso del patrón State para gestionar transiciones de estados en Appointment
 */
public class AppointmentStateExample {

    public static void main(String[] args) {
        // Crear una nueva cita (estado inicial: Created)
        Appointment appointment = new Appointment();
        appointment.setPatientId(1);
        appointment.setProfessionalId(10);
        appointment.setDate(LocalDate.now().plusDays(1));
        appointment.setTime(LocalTime.of(14, 30));
        appointment.setDescription("Consulta general");

        System.out.println("=== Ejemplo de uso del patrón State ===\n");
        System.out.println("1. Cita creada con estado inicial: " + appointment.getState().toString());

        // Transicionar a Attended desde Created
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        System.out.println("2. Después de asistir: " + appointment.getState().toString());

        // Una vez que la cita está en estado Attended, no se pueden hacer más transiciones
        System.out.println("3. Intentar transición desde Attended (estado terminal):");
        appointment.getState().transitionState();

        // Crear otra cita y transicionarla a Rescheduled
        System.out.println("\n--- Otra secuencia ---");
        Appointment appointment2 = new Appointment();
        appointment2.setPatientId(2);
        appointment2.setDescription("Control de seguimiento");
        System.out.println("1. Cita 2 creada: " + appointment2.getState().toString());

        CreatedState createdState2 = (CreatedState) appointment2.getState();
        createdState2.toRescheduled();
        System.out.println("2. Reprogramada: " + appointment2.getState().toString());

        RescheduledState rescheduledState = (RescheduledState) appointment2.getState();
        rescheduledState.updateRescheduleInfo("Nueva hora: 10:00 AM");
        rescheduledState.toAttended();
        System.out.println("3. Asistida después de reprogramar: " + appointment2.getState().toString());

        // Crear cita que se cancela
        System.out.println("\n--- Cita cancelada ---");
        Appointment appointment3 = new Appointment();
        appointment3.setPatientId(3);
        appointment3.setDescription("Cita cancelada");
        System.out.println("1. Cita 3 creada: " + appointment3.getState().toString());

        CreatedState createdState3 = (CreatedState) appointment3.getState();
        createdState3.toCanceled();
        System.out.println("2. Cancelada: " + appointment3.getState().toString());

        // Crear cita no atendida
        System.out.println("\n--- Cita no atendida ---");
        Appointment appointment4 = new Appointment();
        appointment4.setPatientId(4);
        appointment4.setDescription("Cita no asistida");
        System.out.println("1. Cita 4 creada: " + appointment4.getState().toString());

        CreatedState createdState4 = (CreatedState) appointment4.getState();
        createdState4.toNoAttended();
        System.out.println("2. No fue atendida: " + appointment4.getState().toString());
    }
}
