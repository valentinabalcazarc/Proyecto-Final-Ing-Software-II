package configuration;

import models.User;
import enums.RoleUserEnum;
import enums.StatusUserEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationBCryptTest {

    private AuthenticationBCrypt authService;

    @BeforeEach
    public void setUp() {
        authService = new AuthenticationBCrypt();
    }

    @Test
    public void testEncrypt_retornaStringNoNulo() {
        String hash = authService.encrypt("miPassword123");
        assertNotNull(hash);
    }

    @Test
    public void testEncrypt_retornaHashBcrypt_empiezaConPrefijo() {
        String hash = authService.encrypt("clave");
        assertTrue(hash.startsWith("$2a$") || hash.startsWith("$2b$"),
                "El hash debe tener prefijo BCrypt ($2a$ o $2b$)");
    }

    @Test
    public void testEncrypt_dosHashesDistintosParaMismaContrasena() {
        // BCrypt usa salt aleatorio → cada llamada produce un hash distinto
        String hash1 = authService.encrypt("misma");
        String hash2 = authService.encrypt("misma");
        assertNotEquals(hash1, hash2,
                "Dos hashes de la misma contraseña no deben ser iguales (salt aleatorio)");
    }

    @Test
    public void testEncrypt_contrasenaVacia_produceHashValido() {
        String hash = authService.encrypt("");
        assertNotNull(hash);
        assertTrue(hash.startsWith("$2a$") || hash.startsWith("$2b$"));
    }

    // ── verify con hash BCrypt ─────────────────────────────────────────────

    @Test
    public void testVerify_contrasenaCorrecta_conHashBcrypt_retornaTrue() {
        String rawPassword = "segura123";
        String hash = authService.encrypt(rawPassword);

        User user = new User(1, 12345678, hash, "Test", "",
                "User", "", StatusUserEnum.Active, RoleUserEnum.Patient, "", "");

        assertTrue(authService.verify(user, rawPassword));
    }

    @Test
    public void testVerify_contrasenaIncorrecta_conHashBcrypt_retornaFalse() {
        String hash = authService.encrypt("correcta");

        User user = new User(1, 12345678, hash, "Test", "",
                "User", "", StatusUserEnum.Active, RoleUserEnum.Patient, "", "");

        assertFalse(authService.verify(user, "incorrecta"));
    }

    @Test
    public void testVerify_userNull_retornaFalse() {
        assertFalse(authService.verify(null, "cualquierCosa"));
    }

    @Test
    public void testVerify_passwordNull_retornaFalse() {
        User user = new User(1, 12345678, "unHash", "Test", "",
                "User", "", StatusUserEnum.Active, RoleUserEnum.Patient, "", "");
        assertFalse(authService.verify(user, null));
    }

    @Test
    public void testVerify_ambosNull_retornaFalse() {
        assertFalse(authService.verify(null, null));
    }

    @Test
    public void testVerify_contrasenaDiferente_retornaFalse() {
        String hash = authService.encrypt("abc123");

        User user = new User(2, 99999999, hash, "Otro", "",
                "Usuario", "", StatusUserEnum.Active, RoleUserEnum.Admin, "", "");

        assertFalse(authService.verify(user, "xyz789"));
    }

    @Test
    public void testVerify_mismaContrasenaEncriptadaDosVeces_ambasVerifican() {
        String password = "repetida";
        String hash1 = authService.encrypt(password);
        String hash2 = authService.encrypt(password);

        User u1 = new User(1, 1, hash1, "A", "", "B", "",
                StatusUserEnum.Active, RoleUserEnum.Patient, "", "");
        User u2 = new User(2, 2, hash2, "C", "", "D", "",
                StatusUserEnum.Active, RoleUserEnum.Patient, "", "");

        assertTrue(authService.verify(u1, password));
        assertTrue(authService.verify(u2, password));
    }
}
