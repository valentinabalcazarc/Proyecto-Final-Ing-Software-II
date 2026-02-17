
package configuration;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author SAM
 */
public class AuthenticationBCrypt implements IAuthenticationService{

    /*
        @param password: la contraseña en texto plano al momento de registro
    */
    @Override
    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /*
        @param passUser: el hash almacenado en la base de datos
        @param password: la contraseña en texto plano ingresada en el login
    */
    @Override
    public boolean verify(User user, String password) {
        
        if(BCrypt.checkpw(password, user.getPassUser())){
            
            return true;
        }
        
        if(user.getPassUser().equals(password)){
            
            String newpass = encrypt(user.getPassUser());
            
            user.setPassUser(newpass);
            
            return true;
        }
        
        return false;
    }
    
}
