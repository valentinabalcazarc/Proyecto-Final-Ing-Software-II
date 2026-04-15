package DesignPatterns.templateMethod;

import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de pruebas para validar el comportamiento detallado
 * del patrón Template Method.
 */
@DisplayName("Pruebas Avanzadas: Comportamiento del Template Method")
public class AppointmentSchedulerAdvancedTest {

    private Appointment appointment;
    private static final LocalDate FECHA = LocalDate.of(2026, 8, 20);
    private static final LocalTime HORA = LocalTime.of(9, 0);

    @BeforeEach
    public void setUp() {
        appointment = new Appointment();
        appointment.setPatientId(1);
        appointment.setProfessionalId(10);
        appointment.setDate(FECHA);
        appointment.setTime(HORA);
        appointment.setDescription("Consulta de prueba avanzada");
    }

    // Tests de Polimorfismo
    @Test
    @DisplayName("Ambos schedulers son instancias de AppointmentScheduler")
    public void testPolimorfismo_ambosSonSubclases() {
        AppointmentScheduler autonomous = new AutonomousScheduler();
        AppointmentScheduler manual = new ManualScheduler();
        assertTrue(autonomous instanceof AppointmentScheduler);
        assertTrue(manual instanceof AppointmentScheduler);
    }

    @Test
    @DisplayName("Schedulers diferentes son objetos distintos")
    public void testIdentidad_schedulersSonDistintos() {
        AppointmentScheduler autonomous = new AutonomousScheduler();
        AppointmentScheduler manual = new ManualScheduler();

        assertNotSame(autonomous, manual,
            "Dos schedulers distintos no deben ser el mismo objeto");
    }

    @Test
    @DisplayName("Dos instancias del mismo scheduler son objetos distintos")
    public void testIdentidad_dosInstanciasDelMismoTipo() {
        AppointmentScheduler s1 = new AutonomousScheduler();
        AppointmentScheduler s2 = new AutonomousScheduler();

        assertNotSame(s1, s2,
            "Cada instancia debe ser un objeto independiente");
    }

    // Tests de toString
    @Test
    @DisplayName("toString de AutonomousScheduler")
    public void testToString_Autonomous() {
        assertEquals("AutonomousScheduler", new AutonomousScheduler().toString());
    }

    @Test
    @DisplayName("toString de ManualScheduler")
    public void testToString_Manual() {
        assertEquals("ManualScheduler", new ManualScheduler().toString());
    }

    @Test
    @DisplayName("toString de schedulers son diferentes entre sí")
    public void testToString_sonDiferentes() {
        String autonomousStr = new AutonomousScheduler().toString();
        String manualStr = new ManualScheduler().toString();

        assertNotEquals(autonomousStr, manualStr,
            "Cada scheduler debe tener un nombre distinto");
    }

    // Tests de Datos Preservados
    @Test
    @DisplayName("Los datos del appointment no cambian después de schedule()")
    public void testDatosPreservados_despuesDeSchedule() {
        appointment.setPatientId(99);
        appointment.setProfessionalId(88);

        AppointmentScheduler scheduler = new AutonomousScheduler();
        scheduler.schedule(appointment);

        // schedule() ejecuta los 4 pasos pero no debe modificar los datos
        assertEquals(99, appointment.getPatientId(),
            "El patientId no debe cambiar después de schedule()");
        assertEquals(88, appointment.getProfessionalId(),
            "El professionalId no debe cambiar después de schedule()");
    }

    @Test
    @DisplayName("La fecha y hora se preservan después de schedule()")
    public void testFechaHoraPreservadas_despuesDeSchedule() {
        AppointmentScheduler scheduler = new ManualScheduler();
        scheduler.schedule(appointment);

        assertEquals(FECHA, appointment.getDate());
        assertEquals(HORA, appointment.getTime());
    }

    @Test
    @DisplayName("La descripción se preserva después de schedule()")
    public void testDescripcionPreservada_despuesDeSchedule() {
        String descripcionOriginal = "Consulta de seguimiento Piedrazul";
        appointment.setDescription(descripcionOriginal);

        AppointmentScheduler scheduler = new ManualScheduler();
        scheduler.schedule(appointment);

        assertEquals(descripcionOriginal, appointment.getDescription());
    }

    // Tests de Null Checks
    @Test
    @DisplayName("Scheduler no debe ser null al instanciar")
    public void testNullCheck_schedulerNoEsNull() {
        AppointmentScheduler autonomous = new AutonomousScheduler();
        AppointmentScheduler manual = new ManualScheduler();

        assertNotNull(autonomous);
        assertNotNull(manual);
    }

    @Test
    @DisplayName("AutonomousScheduler: appointment null lanza NullPointerException")
    public void testNullCheck_appointmentNuloEnAutonomous() {
        AppointmentScheduler scheduler = new AutonomousScheduler();

        // Pasar null directamente debe causar un error en el primer paso
        assertThrows(Exception.class,
            () -> scheduler.schedule(null),
            "Debe lanzar excepción cuando appointment es null");
    }

    @Test
    @DisplayName("ManualScheduler: appointment null lanza NullPointerException")
    public void testNullCheck_appointmentNuloEnManual() {
        AppointmentScheduler scheduler = new ManualScheduler();

        assertThrows(Exception.class,
            () -> scheduler.schedule(null),
            "Debe lanzar excepción cuando appointment es null");
    }

    // Tests de Escenarios Realistas de Piedrazul
    @Test
    @DisplayName("Escenario: paciente agenda terapia neural autónomamente")
    public void testEscenario_PacienteAgendaTerapiaNeuralSolo() {
        Appointment cita = new Appointment();
        cita.setPatientId(42);
        cita.setProfessionalId(1);
        cita.setDate(LocalDate.of(2026, 9, 10));
        cita.setTime(LocalTime.of(7, 0));
        cita.setDescription("Terapia neural");

        AppointmentScheduler scheduler = new AutonomousScheduler();
        assertDoesNotThrow(() -> scheduler.schedule(cita));
        assertEquals(42, cita.getPatientId());
    }

    @Test
    @DisplayName("Escenario: agendador registra cita de quiropraxia manualmente")
    public void testEscenario_AgendadorRegistraQuiropraxia() {
        Appointment cita = new Appointment();
        cita.setPatientId(15);
        cita.setProfessionalId(3);
        cita.setDate(LocalDate.of(2026, 9, 11));
        cita.setTime(LocalTime.of(8, 30));
        cita.setDescription("Quiropraxia - control mensual");

        AppointmentScheduler scheduler = new ManualScheduler();
        assertDoesNotThrow(() -> scheduler.schedule(cita));
        assertEquals(15, cita.getPatientId());
    }

    @Test
    @DisplayName("Escenario: médico agenda 3 citas consecutivas manualmente")
    public void testEscenario_MedicoAgendaTresCitasConsecutivas() {
        AppointmentScheduler scheduler = new ManualScheduler();

        for (int i = 1; i <= 3; i++) {
            Appointment cita = new Appointment();
            cita.setPatientId(i);
            cita.setProfessionalId(5);
            cita.setDate(LocalDate.now().plusDays(1));
            cita.setTime(LocalTime.of(7 + i, 0));
            cita.setDescription("Cita " + i);

            int finalI = i;
            assertDoesNotThrow(() -> scheduler.schedule(cita),
                "La cita " + finalI + " debe agendarse sin error");
        }
    }

    @Test
    @DisplayName("Escenario: mismo scheduler agenda citas de diferentes pacientes")
    public void testEscenario_MismoSchedulerDiferentesPacientes() {
        AppointmentScheduler scheduler = new AutonomousScheduler();

        Appointment cita1 = new Appointment();
        cita1.setPatientId(10);
        cita1.setProfessionalId(1);
        cita1.setDate(LocalDate.now().plusDays(1));
        cita1.setTime(LocalTime.of(7, 0));

        Appointment cita2 = new Appointment();
        cita2.setPatientId(20);
        cita2.setProfessionalId(2);
        cita2.setDate(LocalDate.now().plusDays(2));
        cita2.setTime(LocalTime.of(8, 0));

        // El mismo scheduler puede usarse para multiples citas independientes
        assertDoesNotThrow(() -> scheduler.schedule(cita1));
        assertDoesNotThrow(() -> scheduler.schedule(cita2));

        // Los datos de cada cita son independientes
        assertEquals(10, cita1.getPatientId());
        assertEquals(20, cita2.getPatientId());
    }
}