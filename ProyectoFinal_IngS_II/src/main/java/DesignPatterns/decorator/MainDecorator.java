package DesignPatterns.decorator;

import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;

public class MainDecorator {
    public static void main(String[] args) {
        System.out.println("=== PATRÓN DECORATOR ===\n");

        
        Appointment appointment = new Appointment();
        appointment.setPatientId(1);
        appointment.setProfessionalId(10);
        appointment.setDate(LocalDate.of(2026, 5, 15));
        appointment.setTime(LocalTime.of(10, 0));
        appointment.setDescription("Consulta general");

        System.out.println("--- Cita base ---");
        System.out.println("Descripcion: " + appointment.getDescription());
        System.out.println("Paciente ID: " + appointment.getPatientId());
        System.out.println("Profesional ID: " + appointment.getProfessionalId());
        System.out.println("Fecha: " + appointment.getDate());
        System.out.println("Hora: " + appointment.getTime());

       
        System.out.println("\n--- Cita con Prioridad Alta ---");
        PriorityAppointment priority = new PriorityAppointment(appointment);
        System.out.println("Descripcion: " + priority.getDescription());
        System.out.println("Paciente ID: " + priority.getPatientId());
        System.out.println("Estado: " + priority.getState());


        System.out.println("\n--- Cita con Prioridad Alta + Urgente ---");
        UrgentAppointment urgent = new UrgentAppointment(priority);
        System.out.println("Descripcion: " + urgent.getDescription());
        System.out.println("Paciente ID: " + urgent.getPatientId());
        System.out.println("Estado: " + urgent.getState());
    }
}