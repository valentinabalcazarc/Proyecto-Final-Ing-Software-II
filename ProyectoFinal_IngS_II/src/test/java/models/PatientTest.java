package models;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    private Patient patient;
    private static final LocalDate FECHA_NAC = LocalDate.of(1995, 8, 20);

    @BeforeEach
    public void setUp() {
        patient = new Patient();
    }

    @Test
    public void testConstructorCompleto_asignaTodosLosCampos() {
        Patient p = new Patient(1, 1073654321, "Juan", "Carlos", "Pérez", "García",
                310123456, FECHA_NAC, "M");

        assertEquals(1, p.getCodPatient());
        assertEquals(1073654321, p.getIdPatient());
        assertEquals("Juan", p.getNamePatient());
        assertEquals("Carlos", p.getSecondNamePatient());
        assertEquals("Pérez", p.getLastNamePatient());
        assertEquals("García", p.getSecondLastNamePatient());
        assertEquals(310123456, p.getPhonePatient());
        assertEquals(FECHA_NAC, p.getDateBirthPatient());
        assertEquals("M", p.getGenderPatient());
    }

    @Test
    public void testConstructorVacio_noCreaExcepcion() {
        assertNotNull(patient);
    }

    @Test
    public void testSetCodPatient_asignaCodigo() {
        patient.setCodPatient(10);
        assertEquals(10, patient.getCodPatient());
    }

    @Test
    public void testSetIdPatient_asignaIdentificacion() {
        patient.setIdPatient(1073000001);
        assertEquals(1073000001, patient.getIdPatient());
    }

    @Test
    public void testSetNamePatient_asignaNombre() {
        patient.setNamePatient("María");
        assertEquals("María", patient.getNamePatient());
    }

    @Test
    public void testSetSecondNamePatient_asignaSegundoNombre() {
        patient.setSecondNamePatient("Fernanda");
        assertEquals("Fernanda", patient.getSecondNamePatient());
    }

    @Test
    public void testSetLastNamePatient_asignaApellido() {
        patient.setLastNamePatient("López");
        assertEquals("López", patient.getLastNamePatient());
    }

    @Test
    public void testSetSecondLastNamePatient_asignaSegundoApellido() {
        patient.setSecondLastNamePatient("Torres");
        assertEquals("Torres", patient.getSecondLastNamePatient());
    }

    @Test
    public void testSetPhonePatient_asignaTelefono() {
        patient.setPhonePatient(300987654);
        assertEquals(300987654, patient.getPhonePatient());
    }

    @Test
    public void testSetDateBirthPatient_asignaFechaNacimiento() {
        patient.setDateBirthPatient(FECHA_NAC);
        assertEquals(FECHA_NAC, patient.getDateBirthPatient());
    }

    @Test
    public void testSetGenderPatient_asignaGenero() {
        patient.setGenderPatient("F");
        assertEquals("F", patient.getGenderPatient());
    }

    @Test
    public void testPatientVacio_camposStringInicialesEnNull() {
        assertNull(patient.getNamePatient());
        assertNull(patient.getLastNamePatient());
        assertNull(patient.getGenderPatient());
    }

    @Test
    public void testPatientVacio_camposNumerosEnCero() {
        assertEquals(0, patient.getCodPatient());
        assertEquals(0, patient.getIdPatient());
    }
}