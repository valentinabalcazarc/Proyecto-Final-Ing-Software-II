package Main;
import DataBase.SQLRepository;
import Vista.winLogin;
import javax.swing.UIManager;
import org.mindrot.jbcrypt.BCrypt;


public class main {

    public static void main(String[] args) {
        SQLRepository.conectar();
        seleccionarLookAndField();
        
        String password = "jesus123";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hash);
        
        
        System.out.println(new java.io.File("piedraAzul.db").getAbsolutePath());
        winLogin winPrincipal = new winLogin();
        winPrincipal.setVisible(true);
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
