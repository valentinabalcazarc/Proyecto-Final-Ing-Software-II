package Main;
import DataBase.SQLRepository;
import Vista.winLogin;
import javax.swing.UIManager;


public class main {

    public static void main(String[] args) {
        SQLRepository.conectar();
        seleccionarLookAndField();
        
        
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
