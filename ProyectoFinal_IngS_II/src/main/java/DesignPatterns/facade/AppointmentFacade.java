package DesignPatterns.facade;

import models.Appointment;
import models.Patient;
import services.ServiceManager;

public class AppointmentFacade {

    private static AppointmentFacade instance;

    private AppointmentFacade() {
    }

    public static AppointmentFacade getInstance() {
        if (instance == null) {
            instance = new AppointmentFacade();
        }
        return instance;
    }

    public int scheduleAppointment(Patient patient, Appointment appointment) {
        // return 0 exito
        // return 1 error al registrar paciente
        // return 2 error al registrar cita
        Patient existingPatient = ServiceManager.getInstance().getPatientService().findByCed(patient.getIdPatient());

        if (existingPatient == null) {
            boolean regOk = ServiceManager.getInstance().getPatientService().regPatient(patient);
            if (!regOk) {
                return 1;
            }
            
            existingPatient = ServiceManager.getInstance().getPatientService().findByCed(patient.getIdPatient());
            if (existingPatient == null) {
                return 1;
            }
        }

        appointment.setPatientId(existingPatient.getCodPatient());

        boolean appOk = ServiceManager.getInstance().getAppointmentService().registerAppointment(appointment);
        
        if (appOk) {
            return 0;
        } else {
            return 2;
        }
    }
}
