package Main;
import DataBase.SQLRepository;
import views.winExport;
import views.winLogin;
import filters.CedulaFormatter;
import filters.TransformCamelCaseNameStage;
import filters.TransformDateStage;
import java.io.Console;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import org.mindrot.jbcrypt.BCrypt;
import plugin.manager.AppointmentPluginManager;
import repository.AppointmentRepository;
import repository.AppointmentRepositoryImpl;
import repository.ProfessionalRepository;
import repository.ProfessionalRepositoryImpl;
import services.AppointmentService;
import services.AppointmentServiceImpl;
import services.ProfessionalService;
import services.ProfessionalServiceImpl;


public class main {

    public static void main(String[] args) {
        SQLRepository.conectar();
        seleccionarLookAndField();
        
        //Inicializar el plugin manager con la ruta base de la aplicación.
        String basePath = getBaseFilePath();
        try {
            AppointmentPluginManager.init(basePath);
        } catch (Exception ex) {
            Logger.getLogger("Application").log(Level.SEVERE, "Error al ejecutar la aplicación", ex);
        }
        
        /*
        CedulaFormatter cedF = new CedulaFormatter();
        
        int ced = 1058932819;
        String cedS = (String) cedF.filter(ced);
        
        System.out.println(cedS);
        
        TransformCamelCaseNameStage nameF = new TransformCamelCaseNameStage();
        
        String name = "jesus eduardo lasso munoz";
        
        String nameFlt = nameF.filter(name);
        
        System.out.println(nameFlt);
        
        
        TransformDateStage dateFilter = new TransformDateStage();

        String fecha = "2006-03-23";

        String resultado = dateFilter.filter(fecha);

        System.out.println(resultado);
        */
        
        System.out.println(new java.io.File("piedraAzul.db").getAbsolutePath());
        
        winLogin login = new winLogin();
        login.setVisible(true);
        
        //Vista exportar
        /*
        AppointmentRepository appointmentRepository = new AppointmentRepositoryImpl();
        ProfessionalRepository professionalRepository = new ProfessionalRepositoryImpl();
        AppointmentService appointmentService = new AppointmentServiceImpl(appointmentRepository);
        ProfessionalService profesionalService = new ProfessionalServiceImpl(professionalRepository);
        
        winExport export = new winExport(appointmentService, profesionalService);
        export.setVisible(true);
        */
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
            path = URLDecoder.decode(path, "UTF-8"); //This should solve the problem with spaces and special characters.
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
}
