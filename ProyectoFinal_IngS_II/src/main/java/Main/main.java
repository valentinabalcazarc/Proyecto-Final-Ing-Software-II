package Main;
import DataBase.SQLRepository;
import views.winLogin;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import plugin.manager.AppointmentPluginManager;

import repository.UserRepositoryImpl;
import repository.UserRepository;

import services.AppointmentService;
import services.ProfessionalService;
import services.UserService;
import services.UserServiceImpl;

import configuration.IAuthenticationService;
import configuration.AuthenticationBCrypt;






public class main {
    private static UserService userService;
    private static AppointmentService appointmentService;
    private static ProfessionalService professionalService;

    public static void main(String[] args) {
        SQLRepository.conectar();
        seleccionarLookAndField();
        
        String basePath = getBaseFilePath();
        try {
            AppointmentPluginManager.init(basePath);
        } catch (Exception ex) {
            Logger.getLogger("Application").log(Level.SEVERE, "Error al ejecutar la aplicación", ex);
        }
        
        winLogin login = new winLogin();
        login.setVisible(true);
        
    }
    
    
    private static void seleccionarLookAndField(){
        for(UIManager.LookAndFeelInfo laf:UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(laf.getName()))
                try {
                UIManager.setLookAndFeel(laf.getClassName());
                 } catch (Exception ex) {
            }
        }
    }
    
    private static String getBaseFilePath() {
        try {
            String path = main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = URLDecoder.decode(path, "UTF-8");
            File pathFile = new File(path);
            if (pathFile.isFile()) {
                path = pathFile.getParent();
                
                if (!path.endsWith(File.separator)) {
                    path += File.separator;
                }
                
            }
            System.out.println(path);
            return path;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, "Error al eliminar espacios en la ruta del archivo", ex);
            return null;
        }
    }
    
    /*private static void mostrarCita(Appointment cita){
        System.out.println(
            "Cita -> Paciente: " + cita.getPatientId() +
            ", Profesional: " + cita.getProfessionalId() +
            ", Fecha: " + cita.getDate() +
            ", Hora: " + cita.getTime() +
            ", Descripcion: " + cita.getDescription() +
            ", Estado: " + cita.getStatus()
        );
    }*/
}
