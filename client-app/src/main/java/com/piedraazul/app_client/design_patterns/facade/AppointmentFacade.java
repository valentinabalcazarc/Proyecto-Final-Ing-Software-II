package com.piedraazul.app_client.design_patterns.facade;

import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.models.User;
import com.piedraazul.app_client.services.ServiceManager;

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

    /**
     * Schedules an appointment for a patient.
     * If the patient doesn't exist, it's created. If it exists, it's updated.
     * Then the appointment is registered.
     * 
     * @return 0 if success, -1 otherwise.
     */
    public int scheduleAppointment(Patient patient, Appointment appointment) {
        try {
            // 1. Manage Patient
            Patient existing = ServiceManager.getInstance().getPatientService().findByCed(patient.getIdPatient());
            
            Long targetCodPatient;
            
            if (existing == null) {

                boolean created = ServiceManager.getInstance().getPatientService().regPatient(patient);
                if (!created) return -1;
                
                // Fetch the newly created patient to get their codPatient (internal ID)
                existing = ServiceManager.getInstance().getPatientService().findByCed(patient.getIdPatient());
                if (existing == null) return -1;
                targetCodPatient = existing.getCodPatient();
            } else {
                // Patient exists, use their internal ID
                targetCodPatient = existing.getCodPatient();
                // Optional: Update patient info if it has changed
            }

            // 2. Schedule Appointment
            //System.out.println(">> targetCodPatient: " + targetCodPatient);
            boolean success = ServiceManager.getInstance().getAppointmentService().saveAppointment(appointment, targetCodPatient);
            
            return success ? 0 : -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
