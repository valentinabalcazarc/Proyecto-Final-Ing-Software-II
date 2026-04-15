package DesignPatterns.templateMethod;

import models.Appointment;

/**
 * Estrategia de agendamiento manual
 */
public class ManualScheduler extends AppointmentScheduler {

    @Override
    protected void validateUser(Appointment appointment) {
        // Se verifica que la cita tenga datos mínimos del paciente y profesional.
        if (appointment.getPatientId() <= 0 || appointment.getProfessionalId() <= 0) {
            throw new IllegalArgumentException(" Datos de paciente o profesional inválidos.");
        }
        
        System.out.println(" Paso 1 - Datos validados: paciente id=" + appointment.getPatientId() 
            + " | profesional id=" + appointment.getProfessionalId());
    }

    @Override
    protected void checkAvailability(Appointment appointment) {
        // Se verifica que el médico no tenga otra cita en la misma fecha y hora
        if (appointment.getDate() == null || appointment.getTime() == null) {
            throw new IllegalArgumentException(" Debe especificarse una fecha y hora para la cita.");
        }
        
        System.out.println(" Paso 2 - Sin conflictos de horario para: "
            + appointment.getDate() + " a las " + appointment.getTime());
    }

    @Override
    protected void assignProfessional(Appointment appointment) {
        System.out.println(" Paso 3 - Profesional confirmado manualmente: id="+ appointment.getProfessionalId());
    }

    @Override
    protected void confirmAppointment(Appointment appointment) {
        System.out.println(" Paso 4 - Cita registrada manualmente y confirmada.");
        System.out.println(" Resumen: paciente=" 
            + appointment.getPatientId() 
            + " | profesional=" + appointment.getProfessionalId()
            + " | fecha=" + appointment.getDate() 
            + " | hora=" + appointment.getTime()
            + " | observacion=" + appointment.getDescription());
    }
    
    @Override
        public String toString() {
        return "ManualScheduler";
    }
}