package models;

import enums.RoleUserEnum;
import enums.StatusUserEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testConstructorCompleto_asignaTodosLosCampos() {
        User u = new User(1, 12345678, "pass123", "Carlos", "Alberto",
                "Ruiz", "Mora", StatusUserEnum.Active, RoleUserEnum.Patient,
                "¿Color favorito?", "azul");

        assertEquals(1, u.getCodUser());
        assertEquals(12345678, u.getCedUser());
        assertEquals("pass123", u.getPassUser());
        assertEquals("Carlos", u.getNameUser());
        assertEquals("Alberto", u.getSecondNameUser());
        assertEquals("Ruiz", u.getLastNameUser());
        assertEquals("Mora", u.getSecondLastNameUser());
        assertEquals(StatusUserEnum.Active, u.getStatusUser());
        assertEquals(RoleUserEnum.Patient, u.getRoleUser());
        assertEquals("¿Color favorito?", u.getSecurityQuestion());
        assertEquals("azul", u.getSecurityAnswer());
    }

    @Test
    public void testConstructorVacio_noCreaExcepcion() {
        assertNotNull(user);
    }

    @Test
    public void testSetCodUser_asignaCodigo() {
        user.setCodUser(5);
        assertEquals(5, user.getCodUser());
    }

    @Test
    public void testSetCedUser_asignaCedula() {
        user.setCedUser(1073654321);
        assertEquals(1073654321, user.getCedUser());
    }

    @Test
    public void testSetPassUser_asignaContrasena() {
        user.setPassUser("miPass123");
        assertEquals("miPass123", user.getPassUser());
    }

    @Test
    public void testSetNameUser_asignaNombre() {
        user.setNameUser("Ana");
        assertEquals("Ana", user.getNameUser());
    }

    @Test
    public void testSetSecondNameUser_asignaSegundoNombre() {
        user.setSecondNameUser("Lucía");
        assertEquals("Lucía", user.getSecondNameUser());
    }

    @Test
    public void testSetLastNameUser_asignaApellido() {
        user.setLastNameUser("Gómez");
        assertEquals("Gómez", user.getLastNameUser());
    }

    @Test
    public void testSetSecondLastNameUser_asignaSegundoApellido() {
        user.setSecondLastNameUser("Vargas");
        assertEquals("Vargas", user.getSecondLastNameUser());
    }

    @Test
    public void testSetStatusUser_asignaEstadoActivo() {
        user.setStatusUser(StatusUserEnum.Active);
        assertEquals(StatusUserEnum.Active, user.getStatusUser());
    }

    @Test
    public void testSetStatusUser_asignaEstadoInactivo() {
        user.setStatusUser(StatusUserEnum.Inactive);
        assertEquals(StatusUserEnum.Inactive, user.getStatusUser());
    }

    @Test
    public void testSetRoleUser_asignaRolProfesional() {
        user.setRoleUser(RoleUserEnum.Professional);
        assertEquals(RoleUserEnum.Professional, user.getRoleUser());
    }

    @Test
    public void testSetRoleUser_asignaRolAdmin() {
        user.setRoleUser(RoleUserEnum.Admin);
        assertEquals(RoleUserEnum.Admin, user.getRoleUser());
    }

    @Test
    public void testSetSecurityQuestion_asignaPregunta() {
        user.setSecurityQuestion("¿Nombre de tu mascota?");
        assertEquals("¿Nombre de tu mascota?", user.getSecurityQuestion());
    }

    @Test
    public void testSetSecurityAnswer_asignaRespuesta() {
        user.setSecurityAnswer("firulais");
        assertEquals("firulais", user.getSecurityAnswer());
    }

    @Test
    public void testUserVacio_camposStringInicialesEnNull() {
        assertNull(user.getNameUser());
        assertNull(user.getPassUser());
        assertNull(user.getSecurityQuestion());
        assertNull(user.getSecurityAnswer());
    }
}
