
package DesignPatterns.templateMethod;

import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Ejemplo de uso del patrón Template Method para gestionar
 * el flujo de agendamiento de citas.
 */
public class AppointmentSchedulerTemplateExample {
    public static void main(String[] args) {

        System.out.println("--- Ejemplo de uso del patron Template Method ---\n");

        // ── Caso 1: Agendamiento autonomo del paciente ──
        System.out.println("--- [AutonomousScheduler] Caso 1: Agendamiento Autonomo (paciente) ---");

        Appointment appointment1 = new Appointment();
        appointment1.setPatientId(1);
        appointment1.setProfessionalId(10);
        appointment1.setDate(LocalDate.now().plusDays(2));
        appointment1.setTime(LocalTime.of(7, 30));
        appointment1.setDescription("Terapia neural - primera consulta");

        AppointmentScheduler autonomousScheduler = new AutonomousScheduler();
        autonomousScheduler.schedule(appointment1);

        // ── Caso 2: Agendamiento manual (médico o agendador) ──
        System.out.println("\n--- [ManualScheduler] Caso 2: Agendamiento Manual (medico/agendador) ---");

        Appointment appointment2 = new Appointment();
        appointment2.setPatientId(5);
        appointment2.setProfessionalId(3);
        appointment2.setDate(LocalDate.now().plusDays(1));
        appointment2.setTime(LocalTime.of(8, 0));
        appointment2.setDescription("Quiropraxia - control mensual");

        AppointmentScheduler manualScheduler = new ManualScheduler();
        manualScheduler.schedule(appointment2);

        // ── Caso 3: Error en agendamiento autónomo (paciente inválido) ──
        System.out.println("\n--- Caso 3: Error en agendamiento autonomo ---");

        Appointment appointment3 = new Appointment();
        appointment3.setPatientId(0);   // inválido
        appointment3.setProfessionalId(10);
        appointment3.setDate(LocalDate.now().plusDays(3));
        appointment3.setTime(LocalTime.of(9, 0));

        try {
            AppointmentScheduler scheduler3 = new AutonomousScheduler();
            scheduler3.schedule(appointment3);
        } catch (IllegalArgumentException e) {
            System.out.println("Error capturado correctamente: " + e.getMessage());
        }
    }   
}
