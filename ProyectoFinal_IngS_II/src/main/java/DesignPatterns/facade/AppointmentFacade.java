package DesignPatterns.facade;

import models.Appointment;
import models.Patient;
import services.AppointmentService;
import services.PatientService;
import services.ServiceManager;

public class AppointmentFacade {

    private static AppointmentFacade instance;

    // En AppointmentFacade.java
    private PatientService patientService;
    private AppointmentService appointmentService;

    // Constructor para uso normal
    private AppointmentFacade() {
        this.patientService = ServiceManager.getInstance().getPatientService();
        this.appointmentService = ServiceManager.getInstance().getAppointmentService();
    }

    // NUEVO: Constructor para pruebas (Simulación)
    public AppointmentFacade(PatientService ps, AppointmentService as) {
        this.patientService = ps;
        this.appointmentService = as;
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
        Patient existingPatient = patientService.findByCed(patient.getIdPatient());

        if (existingPatient == null) {
            boolean regOk = patientService.regPatient(patient);
            if (!regOk) {
                return 1;
            }
            
            existingPatient = patientService.findByCed(patient.getIdPatient());
            if (existingPatient == null) {
                return 1;
            }
        }

        appointment.setPatientId(existingPatient.getCodPatient());

        boolean appOk = appointmentService.registerAppointment(appointment);
        
        if (appOk) {
            return 0;
        } else {
            return 2;
        }
    }
}
