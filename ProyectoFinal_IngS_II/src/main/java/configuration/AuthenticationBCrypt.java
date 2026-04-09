package configuration;

import models.User;
import org.mindrot.jbcrypt.BCrypt;
import repository.UserRepository;
import repository.UserRepositoryImpl;

public class AuthenticationBCrypt implements IAuthenticationService {

    private UserRepository userRepository = new UserRepositoryImpl();

    /*
        @param password: la contraseña en texto plano al momento de registro
    */
    @Override
    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /*
        @param user: usuario con contraseña almacenada
        @param password: contraseña ingresada en login
    */
    @Override
    public boolean verify(User user, String password) {

        if (user == null || password == null) {
            return false;
        }

        String storedPass = user.getPassUser();

        if (storedPass.startsWith("$2a$") || storedPass.startsWith("$2b$")) {

            // Caso normal
            return BCrypt.checkpw(password, storedPass);
        }

        // Caso texto plano (colocado inserts sql)
        if (storedPass.equals(password)) {

            String newHash = encrypt(password);
            user.setPassUser(newHash);

            //cambio a hash y actualizo en la base de datos
            userRepository.update(user);

            return true;
        }

        return false;
    }
}