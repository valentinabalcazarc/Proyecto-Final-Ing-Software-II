package DesignPatterns.state;

import DesignPatterns.builder.AppointmentDirector;
import DesignPatterns.builder.ManualAppointmentBuilder;
import DesignPatterns.builder.SelfServiceAppointmentBuilder;
import DesignPatterns.builder.RescheduledAppointmentBuilder;
import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de pruebas para validar la integración del patrón State
 * con el patrón Builder (AppointmentDirector)
 */
@DisplayName("Pruebas de Integración: State + Builder")
public class AppointmentStateBuilderIntegrationTest {

    private AppointmentDirector director;
    private static final LocalDate FECHA = LocalDate.of(2026, 7, 15);
    private static final LocalTime HORA = LocalTime.of(11, 30);

    @BeforeEach
    public void setUp() {
        director = new AppointmentDirector();
    }


    // ============================================
    // Tests de ManualAppointmentBuilder con Estado
    // ============================================

    @Test
    @DisplayName("ManualAppointmentBuilder crea cita con estado especificado")
    public void testManualBuilder_estadoEspecificado() {
        director.setAppointmentBuilder(new ManualAppointmentBuilder());
        AppointmentState customState = new AttendedState(new Appointment());
        
        director.buildManualAppointment(1, 10, FECHA, HORA, "Consulta", customState);
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof AttendedState,
                "La cita creada debe tener el estado especificado");
        assertEquals(1, cita.getPatientId());
    }

    @Test
    @DisplayName("ManualAppointmentBuilder con estado CanceledState")
    public void testManualBuilder_estadoCanceled() {
        director.setAppointmentBuilder(new ManualAppointmentBuilder());
        Appointment tempCita = new Appointment();
        AppointmentState canceledState = new CanceledState(tempCita);
        
        director.buildManualAppointment(2, 20, FECHA, HORA, "Cita cancelada", canceledState);
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof CanceledState);
    }

    @Test
    @DisplayName("ManualAppointmentBuilder con estado RescheduledState")
    public void testManualBuilder_estadoRescheduled() {
        director.setAppointmentBuilder(new ManualAppointmentBuilder());
        Appointment tempCita = new Appointment();
        AppointmentState rescheduledState = new RescheduledState(tempCita);
        
        director.buildManualAppointment(3, 30, FECHA, HORA, "Cita reprogramada", rescheduledState);
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof RescheduledState);
    }


    // ============================================
    // Tests de SelfServiceAppointmentBuilder con Estado
    // ============================================

    @Test
    @DisplayName("SelfServiceAppointmentBuilder crea cita con estado Created")
    public void testSelfServiceBuilder_creaEstadoCreated() {
        director.setAppointmentBuilder(new SelfServiceAppointmentBuilder());
        
        director.buildSelfServiceAppointment(1, 10, FECHA, HORA, "Auto-cita");
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof CreatedState,
                "Cita creada por servicio automático debe tener estado Created");
        assertEquals(1, cita.getPatientId());
    }

    @Test
    @DisplayName("SelfServiceAppointmentBuilder mantiene descripción")
    public void testSelfServiceBuilder_mantieneDatos() {
        director.setAppointmentBuilder(new SelfServiceAppointmentBuilder());
        
        director.buildSelfServiceAppointment(2, 20, FECHA, HORA, "Consulta auto-agendada");
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getDescription().contains("Auto-agendada"),
                "La descripción debe indicar que fue auto-agendada");
        assertEquals(2, cita.getPatientId());
    }

    @Test
    @DisplayName("SelfServiceAppointmentBuilder mantiene datos de fecha y hora")
    public void testSelfServiceBuilder_fechaHora() {
        director.setAppointmentBuilder(new SelfServiceAppointmentBuilder());
        
        director.buildSelfServiceAppointment(3, 30, FECHA, HORA, "Test");
        Appointment cita = director.getAppointment();
        
        assertEquals(FECHA, cita.getDate());
        assertEquals(HORA, cita.getTime());
    }


    // ============================================
    // Tests de RescheduledAppointmentBuilder con Estado
    // ============================================

    @Test
    @DisplayName("RescheduledAppointmentBuilder crea cita con estado Rescheduled")
    public void testRescheduledBuilder_creaEstadoRescheduled() {
        director.setAppointmentBuilder(new RescheduledAppointmentBuilder());
        
        director.buildRescheduledAppointment(1, 10, FECHA, HORA, "Cita original");
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof RescheduledState,
                "Cita reprogramada debe tener estado Rescheduled");
        assertEquals(1, cita.getPatientId());
    }

    @Test
    @DisplayName("RescheduledAppointmentBuilder marca descripción como REPROGRAMADA")
    public void testRescheduledBuilder_descriptionMarcada() {
        director.setAppointmentBuilder(new RescheduledAppointmentBuilder());
        
        director.buildRescheduledAppointment(2, 20, FECHA, HORA, "Consulta general");
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getDescription().startsWith("REPROGRAMADA:"),
                "Descripción debe ser marcada con REPROGRAMADA:");
    }

    @Test
    @DisplayName("RescheduledAppointmentBuilder permite transición a Attended")
    public void testRescheduledBuilder_transicionAAttended() {
        director.setAppointmentBuilder(new RescheduledAppointmentBuilder());
        
        director.buildRescheduledAppointment(3, 30, FECHA, HORA, "Test");
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof RescheduledState);
        
        // Transicionar a Attended
        RescheduledState rescheduledState = (RescheduledState) cita.getState();
        rescheduledState.toAttended();
        
        assertTrue(cita.getState() instanceof AttendedState,
                "Debe permitir transición de Rescheduled a Attended");
    }


    // ============================================
    // Tests de Secuencias de Builders
    // ============================================

    @Test
    @DisplayName("Secuencia: SelfService -> Rescheduled -> Attended")
    public void testSecuencia_SelfServiceRescheduledAttended() {
        // 1. Crear cita automática
        director.setAppointmentBuilder(new SelfServiceAppointmentBuilder());
        director.buildSelfServiceAppointment(1, 10, FECHA, HORA, "Primera cita");
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof CreatedState);
        
        // 2. Cambiar a Rescheduled manualmente
        CreatedState createdState = (CreatedState) cita.getState();
        createdState.toRescheduled();
        
        assertTrue(cita.getState() instanceof RescheduledState);
        
        // 3. Cambiar a Attended
        RescheduledState rescheduledState = (RescheduledState) cita.getState();
        rescheduledState.toAttended();
        
        assertTrue(cita.getState() instanceof AttendedState);
    }

    @Test
    @DisplayName("Secuencia: Manual Canceled -> no permite transiciones")
    public void testSecuencia_ManualCanceledNoTransitions() {
        director.setAppointmentBuilder(new ManualAppointmentBuilder());
        Appointment tempCita = new Appointment();
        AppointmentState canceledState = new CanceledState(tempCita);
        
        director.buildManualAppointment(1, 10, FECHA, HORA, "Cita cancelada", canceledState);
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof CanceledState);
        
        // Intentar transición (no debe cambiar)
        cita.getState().transitionState();
        assertTrue(cita.getState() instanceof CanceledState,
                "CanceledState no debe permitir transiciones");
    }


    // ============================================
    // Tests de Consistencia de Datos
    // ============================================

    @Test
    @DisplayName("Datos se preservan después de construcción con diferente estado")
    public void testDatosPreservados_afterBuilding() {
        director.setAppointmentBuilder(new ManualAppointmentBuilder());
        Appointment tempCita = new Appointment();
        AppointmentState noAttendedState = new NoAttendedState(tempCita);
        
        String descripcion = "Consulta de seguimiento";
        director.buildManualAppointment(99, 88, FECHA, HORA, descripcion, noAttendedState);
        Appointment cita = director.getAppointment();
        
        assertEquals(99, cita.getPatientId());
        assertEquals(88, cita.getProfessionalId());
        assertEquals(FECHA, cita.getDate());
        assertEquals(HORA, cita.getTime());
        assertEquals(descripcion, cita.getDescription());
        assertTrue(cita.getState() instanceof NoAttendedState);
    }

    @Test
    @DisplayName("Múltiples citas con diferentes estados creadas secuencialmente")
    public void testMultiplesCitas_diferentesEstados() {
        // Primera cita - SelfService (Created)
        director.setAppointmentBuilder(new SelfServiceAppointmentBuilder());
        director.buildSelfServiceAppointment(1, 10, FECHA, HORA, "Cita 1");
        Appointment cita1 = director.getAppointment();
        assertTrue(cita1.getState() instanceof CreatedState);
        
        // Segunda cita - Rescheduled
        director.setAppointmentBuilder(new RescheduledAppointmentBuilder());
        director.buildRescheduledAppointment(2, 20, FECHA, HORA, "Cita 2");
        Appointment cita2 = director.getAppointment();
        assertTrue(cita2.getState() instanceof RescheduledState);
        
        // Tercera cita - Manual (Attended)
        director.setAppointmentBuilder(new ManualAppointmentBuilder());
        Appointment tempCita = new Appointment();
        director.buildManualAppointment(3, 30, FECHA, HORA, "Cita 3", new AttendedState(tempCita));
        Appointment cita3 = director.getAppointment();
        assertTrue(cita3.getState() instanceof AttendedState);
        
        // Verificar independencia
        assertEquals(1, cita1.getPatientId());
        assertEquals(2, cita2.getPatientId());
        assertEquals(3, cita3.getPatientId());
    }


    // ============================================
    // Tests de Comportamiento del Builder con Estados Terminales
    // ============================================

    @Test
    @DisplayName("Builder con AttendedState crea cita terminal directamente")
    public void testBuilder_estadoTerminal_Attended() {
        director.setAppointmentBuilder(new ManualAppointmentBuilder());
        Appointment tempCita = new Appointment();
        
        director.buildManualAppointment(1, 10, FECHA, HORA, "Cita ya asistida", 
                new AttendedState(tempCita));
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof AttendedState);
        assertEquals(1, cita.getPatientId());
    }

    @Test
    @DisplayName("Builder con NoAttendedState crea cita terminal directamente")
    public void testBuilder_estadoTerminal_NoAttended() {
        director.setAppointmentBuilder(new ManualAppointmentBuilder());
        Appointment tempCita = new Appointment();
        
        director.buildManualAppointment(2, 20, FECHA, HORA, "Cita no asistida", 
                new NoAttendedState(tempCita));
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof NoAttendedState);
        assertEquals(2, cita.getPatientId());
    }

    @Test
    @DisplayName("Builder con CanceledState crea cita terminal directamente")
    public void testBuilder_estadoTerminal_Canceled() {
        director.setAppointmentBuilder(new ManualAppointmentBuilder());
        Appointment tempCita = new Appointment();
        
        director.buildManualAppointment(3, 30, FECHA, HORA, "Cita cancelada", 
                new CanceledState(tempCita));
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof CanceledState);
        assertEquals(3, cita.getPatientId());
    }


    // ============================================
    // Tests de Validación de Flujos Completos
    // ============================================

    @Test
    @DisplayName("Flujo completo: Auto-crear -> Reprogramar -> Atender")
    public void testFlujocompleto_SelfServiceToAttended() {
        // Crear cita automáticamente
        director.setAppointmentBuilder(new SelfServiceAppointmentBuilder());
        director.buildSelfServiceAppointment(1, 10, FECHA.minusDays(1), HORA, "Primera consulta");
        Appointment cita = director.getAppointment();
        
        // Verificar estado inicial
        assertTrue(cita.getState() instanceof CreatedState,
                "Debe iniciarse en Created");
        
        // Reprogramar
        CreatedState createdState = (CreatedState) cita.getState();
        createdState.toRescheduled();
        
        assertTrue(cita.getState() instanceof RescheduledState,
                "Debe estar en Rescheduled después de reprogramación");
        
        // Finalmente atender
        RescheduledState rescheduledState = (RescheduledState) cita.getState();
        rescheduledState.toAttended();
        
        assertTrue(cita.getState() instanceof AttendedState,
                "Debe estar en Attended después de asistir");
        
        assertEquals(1, cita.getPatientId());
    }

    @Test
    @DisplayName("Flujo completo: Manual Rescheduled -> Reprogramar múltiples -> Atender")
    public void testFlujocompleto_RescheduledMultiple() {
        director.setAppointmentBuilder(new RescheduledAppointmentBuilder());
        director.buildRescheduledAppointment(2, 20, FECHA, HORA, "Cita reprogramada");
        Appointment cita = director.getAppointment();
        
        assertTrue(cita.getState() instanceof RescheduledState);
        
        // Reprogramar 3 veces más
        for (int i = 0; i < 3; i++) {
            RescheduledState rescheduledState = (RescheduledState) cita.getState();
            rescheduledState.toRescheduled();
            assertTrue(cita.getState() instanceof RescheduledState,
                    "Debe permanecer en Rescheduled después de reprogramación");
        }
        
        // Finalmente asistir
        RescheduledState finalState = (RescheduledState) cita.getState();
        finalState.toAttended();
        
        assertTrue(cita.getState() instanceof AttendedState);
    }
}
