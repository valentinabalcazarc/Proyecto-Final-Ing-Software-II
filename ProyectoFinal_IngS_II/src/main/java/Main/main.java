package Main;
import DataBase.SQLRepository;
import Vista.winExport;
import Vista.winLogin;
import filters.CedulaFormatter;
import filters.TransformCamelCaseNameStage;
import filters.TransformDateStage;
import javax.swing.UIManager;
import org.mindrot.jbcrypt.BCrypt;
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
        
        CedulaFormatter cedF = new CedulaFormatter();
        
        int ced = 1058932819;
        String cedS = (String) cedF.filter(ced);
        
        System.out.println(cedS);
        
        TransformCamelCaseNameStage nameF = new TransformCamelCaseNameStage();
        
        String name = "jesus eduardo lasso muñoz";
        
        String nameFlt = nameF.filter(name);
        
        System.out.println(nameFlt);
        
        
        TransformDateStage dateFilter = new TransformDateStage();

        String fecha = "2006-03-23";

        String resultado = dateFilter.filter(fecha);

        System.out.println(resultado);

        
        String password = "jesus123";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hash);
        
        
        System.out.println(new java.io.File("piedraAzul.db").getAbsolutePath());
        //winLogin winPrincipal = new winLogin();
        //winPrincipal.setVisible(true);
        
        //Vista exportar
        AppointmentRepository appointmentRepository = new AppointmentRepositoryImpl();
        ProfessionalRepository professionalRepository = new ProfessionalRepositoryImpl();
        AppointmentService appointmentService = new AppointmentServiceImpl(appointmentRepository);
        ProfessionalService profesionalService = new ProfessionalServiceImpl(professionalRepository);
        
        winExport export = new winExport(appointmentService, profesionalService);
        export.setVisible(true);
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
}
