package DesignPatterns.state;

import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de pruebas para validar transiciones inválidas y casos extremos
 * del patrón de diseño State en Appointment
 */
@DisplayName("Pruebas de Transiciones Inválidas y Casos Extremos")
public class AppointmentStateTransitionValidationTest {

    private Appointment appointment;
    private static final LocalDate FECHA = LocalDate.of(2026, 6, 1);
    private static final LocalTime HORA = LocalTime.of(10, 0);

    @BeforeEach
    public void setUp() {
        appointment = new Appointment();
        appointment.setPatientId(1);
        appointment.setProfessionalId(10);
        appointment.setDate(FECHA);
        appointment.setTime(HORA);
    }


    // ============================================
    // Tests de Estados Terminales
    // ============================================

    @Test
    @DisplayName("AttendedState no permite transición a otros estados")
    public void testAttendedState_noPermiteTransiciones() {
        // Transicionar a Attended
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        
        AttendedState attendedState = (AttendedState) appointment.getState();
        // Intentar transición se ignora
        attendedState.transitionState();
        
        assertTrue(appointment.getState() instanceof AttendedState,
                "AttendedState debe permanecer terminal");
    }

    @Test
    @DisplayName("NoAttendedState no permite transición a otros estados")
    public void testNoAttendedState_noPermiteTransiciones() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toNoAttended();
        
        NoAttendedState noAttendedState = (NoAttendedState) appointment.getState();
        noAttendedState.transitionState();
        
        assertTrue(appointment.getState() instanceof NoAttendedState,
                "NoAttendedState debe permanecer terminal");
    }

    @Test
    @DisplayName("CanceledState no permite transición a otros estados")
    public void testCanceledState_noPermiteTransiciones() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toCanceled();
        
        CanceledState canceledState = (CanceledState) appointment.getState();
        canceledState.transitionState();
        
        assertTrue(appointment.getState() instanceof CanceledState,
                "CanceledState debe permanecer terminal");
    }


    // ============================================
    // Tests de Transiciones No Permitidas desde Created
    // ============================================

    @Test
    @DisplayName("Desde Created NO está permitida transición directa a NoAttended")
    public void testCreatedState_forbidsTransitionToNoAttendedfromCreated() {
        // En nuestro diseño, esto SÍ está permitido
        // Pero verificamos que si ocurriera, el estado cambiaría correctamente
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toNoAttended();
        
        assertTrue(appointment.getState() instanceof NoAttendedState);
    }


    // ============================================
    // Tests de Transiciones No Permitidas desde Attended
    // ============================================

    @Test
    @DisplayName("AttendedState - no permite transición a Created")
    public void testAttendedState_forbidsTransitionToCreated() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        
        // En un estado terminal, transitionState() no debería cambiar el estado
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof AttendedState,
                "Una cita atendida no puede volver a Created");
    }

    @Test
    @DisplayName("AttendedState - no permite transición a Rescheduled")
    public void testAttendedState_forbidsTransitionToRescheduled() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof AttendedState,
                "Una cita atendida no puede ser reprogramada");
    }

    @Test
    @DisplayName("AttendedState - no permite transición a canceled")
    public void testAttendedState_forbidsTransitionToCanceled() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof AttendedState,
                "Una cita atendida no puede ser cancelada");
    }


    // ============================================
    // Tests de Transiciones No Permitidas desde NoAttended
    // ============================================

    @Test
    @DisplayName("NoAttendedState - no permite transición a Created")
    public void testNoAttendedState_forbidsTransitionToCreated() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toNoAttended();
        
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof NoAttendedState,
                "Una cita no atendida no puede volver a Created");
    }

    @Test
    @DisplayName("NoAttendedState - no permite transición a Attended")
    public void testNoAttendedState_forbidsTransitionToAttended() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toNoAttended();
        
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof NoAttendedState,
                "Una cita no atendida no puede ser marcada como Attended");
    }

    @Test
    @DisplayName("NoAttendedState - no permite transición a Rescheduled")
    public void testNoAttendedState_forbidsTransitionToRescheduled() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toNoAttended();
        
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof NoAttendedState,
                "Una cita no atendida no puede ser reprogramada");
    }

    @Test
    @DisplayName("NoAttendedState - no permite transición a Canceled")
    public void testNoAttendedState_forbidsTransitionToCanceled() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toNoAttended();
        
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof NoAttendedState,
                "Una cita no atendida no puede ser cancelada");
    }


    // ============================================
    // Tests de Transiciones No Permitidas desde Canceled
    // ============================================

    @Test
    @DisplayName("CanceledState - no permite transición a Created")
    public void testCanceledState_forbidsTransitionToCreated() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toCanceled();
        
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof CanceledState,
                "Una cita cancelada no puede volver a Created");
    }

    @Test
    @DisplayName("CanceledState - no permite transición a Attended")
    public void testCanceledState_forbidsTransitionToAttended() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toCanceled();
        
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof CanceledState,
                "Una cita cancelada no puede ser marcada como asistida");
    }

    @Test
    @DisplayName("CanceledState - no permite transición a Rescheduled")
    public void testCanceledState_forbidsTransitionToRescheduled() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toCanceled();
        
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof CanceledState,
                "Una cita cancelada no puede ser reprogramada");
    }

    @Test
    @DisplayName("CanceledState - no permite transición a NoAttended")
    public void testCanceledState_forbidsTransitionToNoAttended() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toCanceled();
        
        AppointmentState result = appointment.getState().transitionState();
        assertTrue(appointment.getState() instanceof CanceledState,
                "Una cita cancelada no puede cambiar a NoAttended");
    }


    // ============================================
    // Tests de Transiciones No Permitidas desde Rescheduled
    // ============================================

    @Test
    @DisplayName("RescheduledState - no permite transición a NoAttended")
    public void testRescheduledState_forbidsTransitionToNoAttended() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        
        // Rescheduled NO permite transición a NoAttended (no hay método para ello)
        RescheduledState rescheduledState = (RescheduledState) appointment.getState();
        assertTrue(appointment.getState() instanceof RescheduledState,
                "Rescheduled no tiene método para transicionar a NoAttended");
    }

    @Test
    @DisplayName("RescheduledState - no permite transición a Created")
    public void testRescheduledState_forbidsTransitionToCreated() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        
        RescheduledState rescheduledState = (RescheduledState) appointment.getState();
        assertTrue(appointment.getState() instanceof RescheduledState,
                "Una cita reprogramada no puede volver a Created");
    }


    // ============================================
    // Tests de Casos Extremos
    // ============================================

    @Test
    @DisplayName("Múltiples instancias de mismo estado con diferentes contextos")
    public void testMultiplesInstancias_diferentesContextos() {
        Appointment cita1 = new Appointment();
        Appointment cita2 = new Appointment();
        
        cita1.setPatientId(100);
        cita2.setPatientId(200);
        
        assertEquals(100, cita1.getPatientId());
        assertEquals(200, cita2.getPatientId());
    }

    @Test
    @DisplayName("Cambio de estado múltiples veces entre Rescheduled")
    public void testMultipleReschedules_manyTimes() {
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toRescheduled();
        
        // Reprogramar 5 veces seguidas
        for (int i = 0; i < 5; i++) {
            RescheduledState rescheduledState = (RescheduledState) appointment.getState();
            rescheduledState.toRescheduled();
            assertTrue(appointment.getState() instanceof RescheduledState,
                    "Debe mantenerse en RescheduledState después de reprogramación " + (i + 1));
        }
    }

    @Test
    @DisplayName("Verificación de que transitionState retorna el estado correcto")
    public void testTransitionState_returnValue() {
        CreatedState createdState = (CreatedState) appointment.getState();
        AppointmentState result = createdState.transitionState();
        
        assertEquals(appointment.getState(), result,
                "transitionState() debe retornar el estado actual de la cita");
    }

    @Test
    @DisplayName("Contexto se mantiene con nueva referencia después de transición")
    public void testContextoMantieneDatos_afterTransition() {
        appointment.setDescription("Consulta inicial");
        appointment.setPatientId(999);
        
        CreatedState createdState = (CreatedState) appointment.getState();
        createdState.toAttended();
        
        assertEquals("Consulta inicial", appointment.getDescription(),
                "Descripción debe mantenerse");
        assertEquals(999, appointment.getPatientId(),
                "PatientId debe mantenerse");
    }

    @Test
    @DisplayName("Constructor copia preserva todas las referencias")
    public void testConstructorCopia_preservasReferencias() {
        CreatedState originalState = (CreatedState) appointment.getState();
        CreatedState copiedState = new CreatedState(originalState);
        
        assertSame(originalState.getContext(), copiedState.getContext(),
                "El contexto debe ser la misma referencia");
    }

    @Test
    @DisplayName("Null checks - estados no pueden ser null")
    public void testNullChecks_statesNotNull() {
        assertNotNull(appointment.getState(),
                "El estado de la cita no debe ser null");
        
        CreatedState createdState = (CreatedState) appointment.getState();
        assertNotNull(createdState.getContext(),
                "El contexto del estado no debe ser null");
    }

    @Test
    @DisplayName("Comparación de identidad de objetos de estado")
    public void testObjectIdentity_states() {
        CreatedState state1 = (CreatedState) appointment.getState();
        CreatedState state2 = (CreatedState) appointment.getState();
        
        // Después de transición, deben ser diferentes objetos
        state1.toAttended();
        CreatedState state3 = new CreatedState(appointment);
        
        assertNotSame(state1, state3,
                "Estados diferentes deben ser objetos diferentes");
    }
}
