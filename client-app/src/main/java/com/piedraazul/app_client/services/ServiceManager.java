package com.piedraazul.app_client.services;

public class ServiceManager {
    private static ServiceManager instance;

    private final UserService userService;
    private final AppointmentService appointmentService;
    private final ProfessionalService professionalService;
    private final PatientService patientService;

    private ServiceManager() {
        // Ahora solo inicializas los servicios.
        // Cada servicio sabrá a qué puerto conectarse.
        this.userService = new UserServiceImpl();
        this.appointmentService = new AppointmentServiceImpl();
        this.professionalService = new ProfessionalServiceImpl();
        this.patientService = new PatientServiceImpl();
    }

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    public UserService getUserService() { return userService; }
    public AppointmentService getAppointmentService() { return appointmentService; }
    public ProfessionalService getProfessionalService() { return professionalService;}
    public PatientService getPatientService() { return patientService;}
}