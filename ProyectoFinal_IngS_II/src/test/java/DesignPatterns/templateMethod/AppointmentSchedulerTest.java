package DesignPatterns.templateMethod;

import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de pruebas unitarias para validar el correcto funcionamiento
 * del patrón de diseño Template Method en AppointmentScheduler.
 */
@DisplayName("Pruebas del Patrón Template Method para AppointmentScheduler")
public class AppointmentSchedulerTest {

    private Appointment appointment;
    private static final LocalDate FECHA = LocalDate.of(2026, 5, 15);
    private static final LocalTime HORA = LocalTime.of(7, 30);
    
    @BeforeEach
    public void setUp() {
        appointment = new Appointment();
        appointment.setPatientId(1);
        appointment.setProfessionalId(10);
        appointment.setDate(FECHA);
        appointment.setTime(HORA);
        appointment.setDescription("Terapia neural - primera consulta");
    }


    
    // Tests de AutonomousScheduler - Datos Válidos
    @Test
    @DisplayName("AutonomousScheduler: agenda exitosamente con datos válidos")
    public void testAutonomous_conDatosValidos_noLanzaExcepcion() {
        AppointmentScheduler scheduler = new AutonomousScheduler();

        // assertDoesNotThrow verifica que el flujo completo corra sin errores
        assertDoesNotThrow(() -> scheduler.schedule(appointment),
            "El agendamiento autónomo no debe lanzar excepción con datos válidos");
    }

    @Test
    @DisplayName("AutonomousScheduler: toString retorna nombre correcto")
    public void testAutonomous_toString_correcto() {
        AppointmentScheduler scheduler = new AutonomousScheduler();
        assertEquals("AutonomousScheduler", scheduler.toString(),
            "toString debe retornar 'AutonomousScheduler'");
    }

    @Test
    @DisplayName("AutonomousScheduler: es instancia de AppointmentScheduler")
    public void testAutonomous_esInstanciaDeScheduler() {
        AppointmentScheduler scheduler = new AutonomousScheduler();
        assertTrue(scheduler instanceof AppointmentScheduler,
            "AutonomousScheduler debe ser subclase de AppointmentScheduler");
    }


    // Tests de AutonomousScheduler - Datos Inválidos
    @Test
    @DisplayName("AutonomousScheduler: lanza excepción si patientId es 0")
    public void testAutonomous_conPatientIdCero_lanzaExcepcion() {
        appointment.setPatientId(0); // inválido
        AppointmentScheduler scheduler = new AutonomousScheduler();

        assertThrows(IllegalArgumentException.class,
            () -> scheduler.schedule(appointment),
            "Debe lanzar excepción cuando patientId <= 0");
    }

    @Test
    @DisplayName("AutonomousScheduler: lanza excepción si patientId es negativo")
    public void testAutonomous_conPatientIdNegativo_lanzaExcepcion() {
        appointment.setPatientId(-5); // inválido
        AppointmentScheduler scheduler = new AutonomousScheduler();

        assertThrows(IllegalArgumentException.class,
            () -> scheduler.schedule(appointment),
            "Debe lanzar excepción cuando patientId es negativo");
    }

    @Test
    @DisplayName("AutonomousScheduler: lanza excepción si la fecha es nula")
    public void testAutonomous_conFechaNula_lanzaExcepcion() {
        appointment.setDate(null); // inválido
        AppointmentScheduler scheduler = new AutonomousScheduler();

        assertThrows(IllegalArgumentException.class,
            () -> scheduler.schedule(appointment),
            "Debe lanzar excepción cuando la fecha es null");
    }

    @Test
    @DisplayName("AutonomousScheduler: lanza excepción si la hora es nula")
    public void testAutonomous_conHoraNula_lanzaExcepcion() {
        appointment.setTime(null); // inválido
        AppointmentScheduler scheduler = new AutonomousScheduler();

        assertThrows(IllegalArgumentException.class,
            () -> scheduler.schedule(appointment),
            "Debe lanzar excepción cuando la hora es null");
    }

    @Test
    @DisplayName("AutonomousScheduler: lanza excepción si professionalId es 0")
    public void testAutonomous_conProfessionalIdCero_lanzaExcepcion() {
        appointment.setProfessionalId(0); // inválido
        AppointmentScheduler scheduler = new AutonomousScheduler();

        assertThrows(IllegalArgumentException.class,
            () -> scheduler.schedule(appointment),
            "Debe lanzar excepción cuando professionalId <= 0");
    }

    // Tests de ManualScheduler - Datos Válidos
    @Test
    @DisplayName("ManualScheduler: agenda exitosamente con datos válidos")
    public void testManual_conDatosValidos_noLanzaExcepcion() {
        AppointmentScheduler scheduler = new ManualScheduler();

        assertDoesNotThrow(() -> scheduler.schedule(appointment),
            "El agendamiento manual no debe lanzar excepción con datos válidos");
    }

    @Test
    @DisplayName("ManualScheduler: toString retorna nombre correcto")
    public void testManual_toString_correcto() {
        AppointmentScheduler scheduler = new ManualScheduler();
        assertEquals("ManualScheduler", scheduler.toString(),
            "toString debe retornar 'ManualScheduler'");
    }

    @Test
    @DisplayName("ManualScheduler: es instancia de AppointmentScheduler")
    public void testManual_esInstanciaDeScheduler() {
        AppointmentScheduler scheduler = new ManualScheduler();
        assertTrue(scheduler instanceof AppointmentScheduler,
            "ManualScheduler debe ser subclase de AppointmentScheduler");
    }

    // Tests de ManualScheduler - Datos Inválidos
    @Test
    @DisplayName("ManualScheduler: lanza excepción si patientId es 0")
    public void testManual_conPatientIdCero_lanzaExcepcion() {
        appointment.setPatientId(0); // inválido
        AppointmentScheduler scheduler = new ManualScheduler();

        assertThrows(IllegalArgumentException.class,
            () -> scheduler.schedule(appointment),
            "Debe lanzar excepción cuando patientId <= 0");
    }

    @Test
    @DisplayName("ManualScheduler: lanza excepción si professionalId es 0")
    public void testManual_conProfessionalIdCero_lanzaExcepcion() {
        appointment.setProfessionalId(0); // inválido
        AppointmentScheduler scheduler = new ManualScheduler();

        assertThrows(IllegalArgumentException.class,
            () -> scheduler.schedule(appointment),
            "Debe lanzar excepción cuando professionalId <= 0");
    }

    @Test
    @DisplayName("ManualScheduler: lanza excepción si la fecha es nula")
    public void testManual_conFechaNula_lanzaExcepcion() {
        appointment.setDate(null); // inválido
        AppointmentScheduler scheduler = new ManualScheduler();

        assertThrows(IllegalArgumentException.class,
            () -> scheduler.schedule(appointment),
            "Debe lanzar excepción cuando la fecha es null");
    }

    @Test
    @DisplayName("ManualScheduler: lanza excepción si la hora es nula")
    public void testManual_conHoraNula_lanzaExcepcion() {
        appointment.setTime(null); // inválido
        AppointmentScheduler scheduler = new ManualScheduler();

        assertThrows(IllegalArgumentException.class,
            () -> scheduler.schedule(appointment),
            "Debe lanzar excepción cuando la hora es null");
    }
    
    // Tests del Flujo Completo
    @Test
    @DisplayName("Flujo completo Autonomous: los 4 pasos ejecutan sin error")
    public void testFlujoCompleto_Autonomous() {
        Appointment cita = new Appointment();
        cita.setPatientId(5);
        cita.setProfessionalId(3);
        cita.setDate(LocalDate.now().plusDays(1));
        cita.setTime(LocalTime.of(8, 0));
        cita.setDescription("Quiropraxia");

        AppointmentScheduler scheduler = new AutonomousScheduler();
        assertDoesNotThrow(() -> scheduler.schedule(cita));
    }

    @Test
    @DisplayName("Flujo completo Manual: los 4 pasos ejecutan sin error")
    public void testFlujoCompleto_Manual() {
        Appointment cita = new Appointment();
        cita.setPatientId(7);
        cita.setProfessionalId(2);
        cita.setDate(LocalDate.now().plusDays(3));
        cita.setTime(LocalTime.of(9, 30));
        cita.setDescription("Fisioterapia");

        AppointmentScheduler scheduler = new ManualScheduler();
        assertDoesNotThrow(() -> scheduler.schedule(cita));
    }

    @Test
    @DisplayName("Múltiples citas agendadas de forma independiente")
    public void testMultiplesCitas_independientes() {
        Appointment cita1 = new Appointment();
        cita1.setPatientId(1);
        cita1.setProfessionalId(10);
        cita1.setDate(LocalDate.now().plusDays(1));
        cita1.setTime(LocalTime.of(7, 0));

        Appointment cita2 = new Appointment();
        cita2.setPatientId(2);
        cita2.setProfessionalId(11);
        cita2.setDate(LocalDate.now().plusDays(2));
        cita2.setTime(LocalTime.of(8, 0));

        AppointmentScheduler scheduler = new ManualScheduler();

        // Ambas citas se agendan sin interferirse entre sí
        assertDoesNotThrow(() -> scheduler.schedule(cita1));
        assertDoesNotThrow(() -> scheduler.schedule(cita2));
    }
}