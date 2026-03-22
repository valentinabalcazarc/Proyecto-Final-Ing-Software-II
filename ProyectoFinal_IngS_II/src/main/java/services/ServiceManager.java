package services;

import configuration.IAuthenticationService;
import configuration.AuthenticationBCrypt;
import repository.AppointmentRepository;
import repository.AppointmentRepositoryImpl;
import repository.ProfessionalRepository;
import repository.ProfessionalRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;


public class ServiceManager {
    private static ServiceManager instance;
    
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final ProfessionalService professionalService;

    private ServiceManager() {
        // Inicialización de dependencias
        UserRepository userRepository = new UserRepositoryImpl();
        IAuthenticationService authService = new AuthenticationBCrypt();
        this.userService = new UserServiceImpl(userRepository, authService);
        
        AppointmentRepository appRepository = new AppointmentRepositoryImpl();
        this.appointmentService = new AppointmentServiceImpl(appRepository);
        
        ProfessionalRepository profRepository = new ProfessionalRepositoryImpl();
        this.professionalService = new ProfessionalServiceImpl(profRepository);
        
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
}
