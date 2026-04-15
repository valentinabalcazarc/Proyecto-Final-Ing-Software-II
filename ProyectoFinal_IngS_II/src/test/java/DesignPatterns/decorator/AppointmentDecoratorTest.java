package DesignPatterns.decorator;

import DesignPatterns.decorator.AppointmentDecorator;
import DesignPatterns.decorator.PriorityAppointment;
import DesignPatterns.decorator.UrgentAppointment;
import models.Appointment;
import DesignPatterns.state.CreatedState;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas del Patrón Decorator para Appointment")
public class AppointmentDecoratorTest {

    private Appointment appointment;
    private static final LocalDate FECHA = LocalDate.of(2026, 5, 15);
    private static final LocalTime HORA = LocalTime.of(10, 0);

    @BeforeEach
    public void setUp() {
        appointment = new Appointment();
        appointment.setPatientId(1);
        appointment.setProfessionalId(10);
        appointment.setDate(FECHA);
        appointment.setTime(HORA);
        appointment.setDescription("Consulta general");
    }

    // ============================================
    // Tests de AppointmentDecorator (clase base)
    // ============================================

    @Test
    @DisplayName("AppointmentDecorator delega getDescription al appointment envuelto")
    public void testDecorator_delegaDescription() {
        PriorityAppointment decorator = new PriorityAppointment(appointment);
        // La base delega, el concreto agrega — si quitamos el override vería la delegación pura
        assertNotNull(decorator.getDescription());
    }

    @Test
    @DisplayName("AppointmentDecorator delega getPatientId correctamente")
    public void testDecorator_delegaPatientId() {
        PriorityAppointment decorator = new PriorityAppointment(appointment);
        assertEquals(1, decorator.getPatientId(),
            "El decorador debe delegar getPatientId al appointment envuelto");
    }

    @Test
    @DisplayName("AppointmentDecorator delega getProfessionalId correctamente")
    public void testDecorator_delegaProfessionalId() {
        PriorityAppointment decorator = new PriorityAppointment(appointment);
        assertEquals(10, decorator.getProfessionalId(),
            "El decorador debe delegar getProfessionalId al appointment envuelto");
    }

    @Test
    @DisplayName("AppointmentDecorator delega getState correctamente")
    public void testDecorator_delegaState() {
        PriorityAppointment decorator = new PriorityAppointment(appointment);
        assertNotNull(decorator.getState(),
            "El decorador debe delegar getState al appointment envuelto");
        assertTrue(decorator.getState() instanceof CreatedState,
            "El estado debe ser el mismo que el del appointment original");
    }

    @Test
    @DisplayName("AppointmentDecorator delega getDate correctamente")
    public void testDecorator_delegaDate() {
        PriorityAppointment decorator = new PriorityAppointment(appointment);
        assertEquals(FECHA, decorator.getDate(),
            "El decorador debe delegar getDate al appointment envuelto");
    }

    // ============================================
    // Tests de PriorityAppointment (decorador concreto)
    // ============================================

    @Test
    @DisplayName("PriorityAppointment agrega [PRIORIDAD ALTA] a la descripción")
    public void testPriority_agregaEtiqueta() {
        PriorityAppointment priority = new PriorityAppointment(appointment);
        assertEquals("Consulta general [PRIORIDAD ALTA]", priority.getDescription(),
            "Debe concatenar [PRIORIDAD ALTA] a la descripción original");
    }

    @Test
    @DisplayName("PriorityAppointment es instancia de AppointmentDecorator")
    public void testPriority_esInstanciaDeDecorator() {
        PriorityAppointment priority = new PriorityAppointment(appointment);
        assertTrue(priority instanceof AppointmentDecorator,
            "PriorityAppointment debe ser subclase de AppointmentDecorator");
    }

    @Test
    @DisplayName("PriorityAppointment no modifica los demás atributos")
    public void testPriority_noModificaOtrosAtributos() {
        PriorityAppointment priority = new PriorityAppointment(appointment);
        assertEquals(1, priority.getPatientId());
        assertEquals(10, priority.getProfessionalId());
        assertEquals(FECHA, priority.getDate());
        assertEquals(HORA, priority.getTime());
    }

    @Test
    @DisplayName("PriorityAppointment toString retorna nombre correcto")
    public void testPriority_toString() {
        PriorityAppointment priority = new PriorityAppointment(appointment);
        assertEquals("PriorityAppointment", priority.toString());
    }

    // ============================================
    // Tests de decoradores apilados
    // ============================================

    @Test
    @DisplayName("Decoradores apilados: descripción acumula todas las etiquetas")
    public void testApilados_descripcionAcumulada() {
        PriorityAppointment priority = new PriorityAppointment(appointment);
        UrgentAppointment urgent = new UrgentAppointment(priority);

        assertTrue(urgent.getDescription().contains("[PRIORIDAD ALTA]"));
        assertTrue(urgent.getDescription().contains("[URGENTE]"));
    }

    @Test
    @DisplayName("Decoradores apilados: los atributos base se mantienen")
    public void testApilados_atributosBase() {
        PriorityAppointment priority = new PriorityAppointment(appointment);
        UrgentAppointment urgent = new UrgentAppointment(priority);

        assertEquals(1, urgent.getPatientId());
        assertEquals(10, urgent.getProfessionalId());
    }

    // ============================================
    // Tests de flujo completo
    // ============================================

    @Test
    @DisplayName("Flujo completo: cita normal vs cita con prioridad")
    public void testFlujoCompleto_citaNormalVsPrioridad() {
        Appointment citaNormal = new Appointment();
        citaNormal.setDescription("Revisión rutinaria");

        PriorityAppointment citaPrioritaria = new PriorityAppointment(citaNormal);

        assertFalse(citaNormal.getDescription().contains("[PRIORIDAD ALTA]"));
        assertTrue(citaPrioritaria.getDescription().contains("[PRIORIDAD ALTA]"));
    }
}