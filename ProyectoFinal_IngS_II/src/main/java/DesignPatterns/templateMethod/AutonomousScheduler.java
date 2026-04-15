package DesignPatterns.templateMethod;

import models.Appointment;

/**
 * Estrategia de agendamiento autonomo.
 */
public class AutonomousScheduler extends AppointmentScheduler {

    @Override
    protected void validateUser(Appointment appointment) {
        // En agendamiento autónomo se verifica que el paciente
        // esté registrado y activo en el sistema.
        if (appointment.getPatientId() <= 0) {
            throw new IllegalArgumentException(" Paciente no valido. Id: " + appointment.getPatientId());
        }
        System.out.println(" Paso 1 - Usuario validado: paciente id=" + appointment.getPatientId());
    }

    @Override
    protected void checkAvailability(Appointment appointment) {
        // El sistema busca automáticamente el horario más cercano disponible
        // según la especialidad, médico y configuración del sistema.
        if (appointment.getDate() == null || appointment.getTime() == null) {
            throw new IllegalArgumentException(" Fecha u hora no disponibles para agendar.");
        }
        System.out.println(" Paso 2 - Disponibilidad verificada: "+ appointment.getDate() + " a las " + appointment.getTime());
    }

    @Override
    protected void assignProfessional(Appointment appointment) {
        // El sistema asigna automáticamente el profesional disponible.
        // Si el paciente ya eligió uno, el sistema lo confirma.
        if (appointment.getProfessionalId() <= 0) {
            throw new IllegalArgumentException(" No hay profesional disponible para asignar.");
        }
        System.out.println(" Paso 3 - Profesional asignado automaticamente: id=" + appointment.getProfessionalId());
    }

    @Override
    protected void confirmAppointment(Appointment appointment) {
        // La cita queda confirmada. El estado pasa de Created a Confirmed.
        System.out.println(" Paso 4 - Cita confirmada exitosamente.");
        System.out.println(" Resumen: paciente=" 
            + appointment.getPatientId() 
            + " | profesional=" + appointment.getProfessionalId()
            + " | fecha=" + appointment.getDate() 
            + " | hora=" + appointment.getTime());
    }
    
    @Override
        public String toString() {
        return "AutonomousScheduler";
    }
}