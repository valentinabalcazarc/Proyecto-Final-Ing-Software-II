package Main;
import DataBase.SQLRepository;
import builder.AppointmentDirector;
import builder.ManualAppointmentBuilder;
import builder.RescheduledAppointmentBuilder;
import builder.SelfServiceAppointmentBuilder;
import enums.StatusAppointment;
import models.Appointment;
import views.winLogin;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import plugin.manager.AppointmentPluginManager;



public class main {

    public static void main(String[] args) {
        SQLRepository.conectar();
        seleccionarLookAndField();
        
        String basePath = getBaseFilePath();
        try {
            AppointmentPluginManager.init(basePath);
        } catch (Exception ex) {
            Logger.getLogger("Application").log(Level.SEVERE, "Error al ejecutar la aplicación", ex);
        }
        /*
        StatusAppointment statusmanual = StatusAppointment.Cancelled;
        
        AppointmentDirector director = new AppointmentDirector();

        // =============================
        // 1. CITA MANUAL
        // =============================
        director.setAppointmentBuilder(new ManualAppointmentBuilder());

        director.buildManualAppointment(
                1,
                100,
                LocalDate.now(),
                LocalTime.of(9, 0),
                "Consulta general",
                statusmanual
        );

        Appointment citaManual = director.getAppointment();

        System.out.println("===== CITA MANUAL =====");
        mostrarCita(citaManual);


        // =============================
        // 2. CITA AUTO-SERVICIO
        // =============================
        director.setAppointmentBuilder(new SelfServiceAppointmentBuilder());

        director.buildSelfServiceAppointment(
                2,
                200,
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 30),
                "Agendada por paciente"
        );

        Appointment citaAuto = director.getAppointment();

        System.out.println("===== CITA AUTO-SERVICIO =====");
        mostrarCita(citaAuto);


        // =============================
        // 3. CITA REPROGRAMADA
        // =============================
        director.setAppointmentBuilder(new RescheduledAppointmentBuilder());

        director.buildRescheduledAppointment(
                3,
                300,
                LocalDate.now().plusDays(2),
                LocalTime.of(14, 0),
                "Cambio de horario"
        );

        Appointment citaReprogramada = director.getAppointment();

        System.out.println("===== CITA REPROGRAMADA =====");
        mostrarCita(citaReprogramada);
        
        System.out.println(new java.io.File("piedraAzul.db").getAbsolutePath());
        */
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
    
    private static void mostrarCita(Appointment cita){
        System.out.println(
            "Cita -> Paciente: " + cita.getPatientId() +
            ", Profesional: " + cita.getProfessionalId() +
            ", Fecha: " + cita.getDate() +
            ", Hora: " + cita.getTime() +
            ", Descripcion: " + cita.getDescription() +
            ", Estado: " + cita.getStatus()
        );
    }
}
