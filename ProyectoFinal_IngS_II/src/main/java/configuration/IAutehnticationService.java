
package configuration;

/**
 *
 * @author SAM
 */
public interface IAutehnticationService {
    /*
        @brief encripta la contraseña que ingresa el usuario al registrarse
    */
    String encrypt(String password);
    
    /*
        @brief verifica el hash almacenado en la BD con el generado por la contraseña
        ingresada durante el login
    */
    boolean verify(String passUser, String password);
}
