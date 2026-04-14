package DesignPatterns.state;

import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de pruebas unitarias para validar el correcto funcionamiento
 * del patrón de diseño State en la clase Appointment
 */
@DisplayName("Pruebas del Patrón State para Appointment")
public class AppointmentStateTest {

    private Appointment appointment;
    private static final LocalDate FECHA = LocalDate.of(2026, 5, 15);
    private static final LocalTime HORA = LocalTime.of(14, 30);

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
    // Tests del Estado Inicial (Created)
    // ============================================

    @Test
    @DisplayName("Estado inicial debe ser Created")
    public void testEstadoInicial_esCreated() {
        assertTrue(appointment.getState() instanceof CreatedState,
                "El estado inicial debe ser CreatedState");
    }

    @Test
    @DisplayName("CreatedState debe tener referencia correcta al contexto")
    public void testCreatedState_referenciasCorrectas() {
        CreatedState createdState = (CreatedState) appointment.getState();
        assertNotNull(createdState.getContext(),
                "El estado debe tener una referencia al contexto (Appointment)");
        assertEquals(appointment, createdState.getContext(),
                "El contexto debe ser la cita actual");
    }


    // ============================================
    // Tests de Transiciones desde Created
    // ============================================

    @Test
    @DisplayName("Created -> Attended: transición válida")
    public void testTransicion_CreatedAAttended() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        
        assertTrue(appointment.getState() instanceof AttendedState,
                "El estado debe cambiar a AttendedState");
    }

    @Test
    @DisplayName("Created -> Rescheduled: transición válida")
    public void testTransicion_CreatedARescheduled() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        
        assertTrue(appointment.getState() instanceof RescheduledState,
                "El estado debe cambiar a RescheduledState");
    }

    @Test
    @DisplayName("Created -> NoAttended: transición válida")
    public void testTransicion_CreatedANoAttended() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toNoAttended();
        
        assertTrue(appointment.getState() instanceof NoAttendedState,
                "El estado debe cambiar a NoAttendedState");
    }

    @Test
    @DisplayName("Created -> Canceled: transición válida")
    public void testTransicion_CreatedACanceled() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toCanceled();
        
        assertTrue(appointment.getState() instanceof CanceledState,
                "El estado debe cambiar a CanceledState");
    }


    // ============================================
    // Tests del Estado Attended (Terminal)
    // ============================================

    @Test
    @DisplayName("AttendedState es un estado terminal")
    public void testAttendedState_esTerminal() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        
        AttendedState attendedState = (AttendedState) appointment.getState();
        AppointmentState resultadoTransicion = attendedState.transitionState();
        
        assertTrue(appointment.getState() instanceof AttendedState,
                "El estado no debe cambiar desde Attended");
    }

    @Test
    @DisplayName("AttendedState tiene método específico markAsAttended")
    public void testAttendedState_metodoEspecifico() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        
        AttendedState attendedState = (AttendedState) appointment.getState();
        assertDoesNotThrow(() -> attendedState.markAsAttended(),
                "El método markAsAttended no debe lanzar excepciones");
    }


    // ============================================
    // Tests del Estado Rescheduled
    // ============================================

    @Test
    @DisplayName("Rescheduled -> Attended: transición válida")
    public void testTransicion_RescheduledAAttended() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        
        RescheduledState rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toAttended();
        
        assertTrue(appointment.getState() instanceof AttendedState,
                "Debe transicionar de Rescheduled a Attended");
    }

    @Test
    @DisplayName("Rescheduled -> Rescheduled: auto-transición permitida")
    public void testTransicion_RescheduledARescheduled() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        
        RescheduledState rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toRescheduled();
        
        assertTrue(appointment.getState() instanceof RescheduledState,
                "Debe permitir reprogramaciones múltiples");
    }

    @Test
    @DisplayName("Rescheduled -> Canceled: transición válida")
    public void testTransicion_RescheduledACanceled() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        
        RescheduledState rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toCanceled();
        
        assertTrue(appointment.getState() instanceof CanceledState,
                "Debe transicionar de Rescheduled a Canceled");
    }

    @Test
    @DisplayName("RescheduledState tiene método específico updateRescheduleInfo")
    public void testRescheduledState_metodoEspecifico() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        
        RescheduledState rescheduledState = (RescheduledState) appointment.getState();
        assertDoesNotThrow(() -> rescheduledState.updateRescheduleInfo("Nueva fecha: 2026-06-01"),
                "El método updateRescheduleInfo no debe lanzar excepciones");
    }


    // ============================================
    // Tests del Estado NoAttended (Terminal)
    // ============================================

    @Test
    @DisplayName("NoAttendedState es un estado terminal")
    public void testNoAttendedState_esTerminal() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toNoAttended();
        
        NoAttendedState noAttendedState = (NoAttendedState) appointment.getState();
        AppointmentState resultadoTransicion = noAttendedState.transitionState();
        
        assertTrue(appointment.getState() instanceof NoAttendedState,
                "El estado no debe cambiar desde NoAttended");
    }

    @Test
    @DisplayName("NoAttendedState tiene método específico recordNoShow")
    public void testNoAttendedState_metodoEspecifico() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toNoAttended();
        
        NoAttendedState noAttendedState = (NoAttendedState) appointment.getState();
        assertDoesNotThrow(() -> noAttendedState.recordNoShow(),
                "El método recordNoShow no debe lanzar excepciones");
    }


    // ============================================
    // Tests del Estado Canceled (Terminal)
    // ============================================

    @Test
    @DisplayName("CanceledState es un estado terminal")
    public void testCanceledState_esTerminal() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toCanceled();
        
        CanceledState canceledState = (CanceledState) appointment.getState();
        AppointmentState resultadoTransicion = canceledState.transitionState();
        
        assertTrue(appointment.getState() instanceof CanceledState,
                "El estado no debe cambiar desde Canceled");
    }

    @Test
    @DisplayName("CanceledState tiene método específico markAsCanceled")
    public void testCanceledState_metodoEspecifico() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toCanceled();
        
        CanceledState canceledState = (CanceledState) appointment.getState();
        assertDoesNotThrow(() -> canceledState.markAsCanceled(),
                "El método markAsCanceled no debe lanzar excepciones");
    }


    // ============================================
    // Tests de Cadenas de Transiciones
    // ============================================

    @Test
    @DisplayName("Cadena: Created -> Rescheduled -> Attended")
    public void testCadenaTransiciones_CreatedRescheduledAttended() {
        // Created -> Rescheduled
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        assertTrue(appointment.getState() instanceof RescheduledState);
        
        // Rescheduled -> Attended
        RescheduledState rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toAttended();
        assertTrue(appointment.getState() instanceof AttendedState);
    }

    @Test
    @DisplayName("Cadena: Created -> Rescheduled -> Rescheduled -> Attended")
    public void testCadenaTransiciones_MultipleReprogramaciones() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        
        // Primera reprogramación
        RescheduledState rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toRescheduled();
        assertTrue(appointment.getState() instanceof RescheduledState);
        
        // Segunda reprogramación
        rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toRescheduled();
        assertTrue(appointment.getState() instanceof RescheduledState);
        
        // Finalmente asistida
        rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toAttended();
        assertTrue(appointment.getState() instanceof AttendedState);
    }

    @Test
    @DisplayName("Cadena: Created -> Rescheduled -> Canceled")
    public void testCadenaTransiciones_CreatedRescheduledCanceled() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        
        RescheduledState rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toCanceled();
        
        assertTrue(appointment.getState() instanceof CanceledState);
    }


    // ============================================
    // Tests de Constructor de Estado Copion
    // ============================================

    @Test
    @DisplayName("Constructor copia de AppointmentState preserva contexto")
    public void testConstructorCopia_preservaContexto() {
        CreatedState createdState = (CreatedState) appointment.getState();
        CreatedState createdStateCopy = new CreatedState(createdState);
        
        assertEquals(appointment, createdStateCopy.getContext(),
                "El contexto debe ser preservado en el constructor copia");
    }

    @Test
    @DisplayName("Cambio de estado mantiene la referencia al contexto")
    public void testCambioEstado_mantieneDatos() {
        appointment.setPatientId(100);
        appointment.setProfessionalId(200);
        
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        
        // Verificar que los datos se mantienen
        assertEquals(100, appointment.getPatientId());
        assertEquals(200, appointment.getProfessionalId());
    }


    // ============================================
    // Tests del Estado Inicial del Sistema
    // ============================================

    @Test
    @DisplayName("InitialState retorna CreatedState")
    public void testInitialState_retornaCreatedState() {
        Appointment newAppointment = new Appointment();
        AppointmentState initialState = AppointmentState.InitialState(newAppointment);
        
        assertTrue(initialState instanceof CreatedState,
                "El estado inicial debe ser CreatedState");
    }

    @Test
    @DisplayName("Múltiples citas tienen estado inicial independiente")
    public void testMultiplesCitas_estadosIndependientes() {
        Appointment cita1 = new Appointment();
        Appointment cita2 = new Appointment();
        
        CreatedState state1 = (CreatedState) cita1.getState();
        state1.toAttended();
        
        // La cita2 no debe ser afectada
        assertTrue(cita2.getState() instanceof CreatedState);
        assertTrue(cita1.getState() instanceof AttendedState);
    }


    // ============================================
    // Tests de Validación de Flujos Completos
    // ============================================

    @Test
    @DisplayName("Flujo completo: Cita asistida")
    public void testFlujoCitaAsistida() {
        assertEquals(1, appointment.getPatientId());
        assertTrue(appointment.getState() instanceof CreatedState);
        
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        
        assertTrue(appointment.getState() instanceof AttendedState);
        assertEquals(1, appointment.getPatientId()); // Los datos se mantienen
    }

    @Test
    @DisplayName("Flujo completo: Cita cancelada")
    public void testFlujoCitaCancelada() {
        assertTrue(appointment.getState() instanceof CreatedState);
        
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toCanceled();
        
        assertTrue(appointment.getState() instanceof CanceledState);
    }

    @Test
    @DisplayName("Flujo completo: Cita no atendida")
    public void testFlujoCitaNoAtendida() {
        assertTrue(appointment.getState() instanceof CreatedState);
        
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toNoAttended();
        
        assertTrue(appointment.getState() instanceof NoAttendedState);
    }

    @Test
    @DisplayName("Flujo completo: Cita reprogramada múltiples veces")
    public void testFlujoCitaReprogramadaMultiples() {
        assertTrue(appointment.getState() instanceof CreatedState);
        
        // Primera reprogramación
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        assertTrue(appointment.getState() instanceof RescheduledState);
        
        // Segunda y tercera reprogramación
        RescheduledState rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toRescheduled();
        assertTrue(appointment.getState() instanceof RescheduledState);
        
        rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toRescheduled();
        assertTrue(appointment.getState() instanceof RescheduledState);
        
        // Finalmente asistida
        rescheduledState = (RescheduledState) appointment.getState();
        rescheduledState.toAttended();
        assertTrue(appointment.getState() instanceof AttendedState);
    }


    // ============================================
    // Tests de Métodos toString
    // ============================================

    @Test
    @DisplayName("Cada estado tiene toString() descriptivo")
    public void testToString_estadosDescriptivos() {
        CreatedState createdState = new CreatedState(appointment);
        assertEquals("CreatedState", createdState.toString());
        
        AttendedState attendedState = new AttendedState(appointment);
        assertEquals("AttendedState", attendedState.toString());
        
        RescheduledState rescheduledState = new RescheduledState(appointment);
        assertEquals("RescheduledState", rescheduledState.toString());
        
        NoAttendedState noAttendedState = new NoAttendedState(appointment);
        assertEquals("NoAttendedState", noAttendedState.toString());
        
        CanceledState canceledState = new CanceledState(appointment);
        assertEquals("CanceledState", canceledState.toString());
    }
}
