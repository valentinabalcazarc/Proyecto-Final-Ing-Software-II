package DesignPatterns.state;

import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de pruebas para validar serialización, comparación
 * y casos de uso avanzados del patrón State
 */
@DisplayName("Pruebas Avanzadas: Serialización y Comportamiento Estado")
public class AppointmentStateAdvancedTest {

    private Appointment appointment;
    private static final LocalDate FECHA = LocalDate.of(2026, 8, 20);
    private static final LocalTime HORA = LocalTime.of(15, 0);

    @BeforeEach
    public void setUp() {
        appointment = new Appointment();
        appointment.setPatientId(1);
        appointment.setProfessionalId(10);
        appointment.setDate(FECHA);
        appointment.setTime(HORA);
        appointment.setDescription("Consulta de prueba");
    }


    // ============================================
    // Tests de Representación de Estados
    // ============================================

    @Test
    @DisplayName("toString de CreatedState")
    public void testToString_Created() {
        CreatedState created = new CreatedState(appointment);
        assertEquals("CreatedState", created.toString());
    }

    @Test
    @DisplayName("toString de AttendedState")
    public void testToString_Attended() {
        AttendedState attended = new AttendedState(appointment);
        assertEquals("AttendedState", attended.toString());
    }

    @Test
    @DisplayName("toString de RescheduledState")
    public void testToString_Rescheduled() {
        RescheduledState rescheduled = new RescheduledState(appointment);
        assertEquals("RescheduledState", rescheduled.toString());
    }

    @Test
    @DisplayName("toString de NoAttendedState")
    public void testToString_NoAttended() {
        NoAttendedState noAttended = new NoAttendedState(appointment);
        assertEquals("NoAttendedState", noAttended.toString());
    }

    @Test
    @DisplayName("toString de CanceledState")
    public void testToString_Canceled() {
        CanceledState canceled = new CanceledState(appointment);
        assertEquals("CanceledState", canceled.toString());
    }


    // ============================================
    // Tests de Métodos Específicos por Estado
    // ============================================

    @Test
    @DisplayName("CreatedState tiene métodos para todas las transiciones")
    public void testCreatedState_todosLosMetodos() {
        CreatedState created = (CreatedState) appointment.getState();
        
        assertDoesNotThrow(() -> created.toAttended());
        appointment.setState(new CreatedState(appointment)); // Reset
        
        assertDoesNotThrow(() -> created.toRescheduled());
        appointment.setState(new CreatedState(appointment));
        
        assertDoesNotThrow(() -> created.toNoAttended());
        appointment.setState(new CreatedState(appointment));
        
        assertDoesNotThrow(() -> created.toCanceled());
    }

    @Test
    @DisplayName("AttendedState tiene método markAsAttended")
    public void testAttendedState_metodoMockAsAttended() {
        CreatedState created = (CreatedState) appointment.getState();
        created.toAttended();
        
        AttendedState attended = (AttendedState) appointment.getState();
        assertDoesNotThrow(() -> attended.markAsAttended(),
                "Debe tener el método markAsAttended");
    }

    @Test
    @DisplayName("RescheduledState tiene método updateRescheduleInfo")
    public void testRescheduledState_metodoUpdateRescheduleInfo() {
        CreatedState created = (CreatedState) appointment.getState();
        created.toRescheduled();
        
        RescheduledState rescheduled = (RescheduledState) appointment.getState();
        assertDoesNotThrow(() -> rescheduled.updateRescheduleInfo("Nueva información"),
                "Debe tener el método updateRescheduleInfo");
    }

    @Test
    @DisplayName("NoAttendedState tiene método recordNoShow")
    public void testNoAttendedState_metodoRecordNoShow() {
        CreatedState created = (CreatedState) appointment.getState();
        created.toNoAttended();
        
        NoAttendedState noAttended = (NoAttendedState) appointment.getState();
        assertDoesNotThrow(() -> noAttended.recordNoShow(),
                "Debe tener el método recordNoShow");
    }

    @Test
    @DisplayName("CanceledState tiene método markAsCanceled")
    public void testCanceledState_metodoMarkAsCanceled() {
        CreatedState created = (CreatedState) appointment.getState();
        created.toCanceled();
        
        CanceledState canceled = (CanceledState) appointment.getState();
        assertDoesNotThrow(() -> canceled.markAsCanceled(),
                "Debe tener el método markAsCanceled");
    }


    // ============================================
    // Tests de Encapsulación
    // ============================================

    @Test
    @DisplayName("Estados encapsulan su comportamiento")
    public void testEncapsulacion_estadosIndependientes() {
        Appointment cita1 = new Appointment();
        Appointment cita2 = new Appointment();
        Appointment cita3 = new Appointment();
        
        // Cada cita tiene su propio estado
        CreatedState state1 = (CreatedState) cita1.getState();
        state1.toAttended();
        
        assertTrue(cita1.getState() instanceof AttendedState);
        assertTrue(cita2.getState() instanceof CreatedState);
        assertTrue(cita3.getState() instanceof CreatedState);
    }


    // ============================================
    // Tests de Getters
    // ============================================

    @Test
    @DisplayName("Getter getContext retorna contexto correcto")
    public void testGetContext_retornaCorrectamente() {
        CreatedState created = (CreatedState) appointment.getState();
        assertEquals(appointment, created.getContext(),
                "getContext debe retornar la cita actual");
    }

    @Test
    @DisplayName("getContext se mantiene después de transiciones")
    public void testGetContext_seMantieneEnTransiciones() {
        CreatedState created = (CreatedState) appointment.getState();
        Appointment contextoAnterior = created.getContext();
        
        created.toAttended();
        
        AttendedState attended = (AttendedState) appointment.getState();
        assertEquals(contextoAnterior, attended.getContext(),
                "El contexto debe ser la misma referencia");
    }


    // ============================================
    // Tests de Setter setContext
    // ============================================

    @Test
    @DisplayName("Setter setContext cambia el contexto")
    public void testSetContext_cambiaContexto() {
        Appointment nueva = new Appointment();
        nueva.setPatientId(999);
        
        CreatedState created = (CreatedState) appointment.getState();
        created.setContext(nueva);
        
        assertEquals(999, created.getContext().getPatientId(),
                "El contexto debe cambiar al nuevo Appointment");
    }

    @Test
    @DisplayName("Múltiples estados pueden compartir contexto")
    public void testContextoCompartido_multipleEstados() {
        AttendedState attended = new AttendedState(appointment);
        RescheduledState rescheduled = new RescheduledState(appointment);
        CanceledState canceled = new CanceledState(appointment);
        
        assertSame(appointment, attended.getContext());
        assertSame(appointment, rescheduled.getContext());
        assertSame(appointment, canceled.getContext());
    }


    // ============================================
    // Tests de Constructor Copia
    // ============================================

    @Test
    @DisplayName("Constructor copia de CreatedState")
    public void testConstructorCopia_Created() {
        CreatedState original = new CreatedState(appointment);
        CreatedState copia = new CreatedState(original);
        
        assertEquals(original.getContext(), copia.getContext(),
                "La copia debe mantener el mismo contexto");
    }

    @Test
    @DisplayName("Constructor copia de AttendedState")
    public void testConstructorCopia_Attended() {
        AttendedState original = new AttendedState(appointment);
        AttendedState copia = new AttendedState(original);
        
        assertEquals(original.getContext(), copia.getContext());
    }

    @Test
    @DisplayName("Constructor copia de RescheduledState")
    public void testConstructorCopia_Rescheduled() {
        RescheduledState original = new RescheduledState(appointment);
        RescheduledState copia = new RescheduledState(original);
        
        assertEquals(original.getContext(), copia.getContext());
    }

    @Test
    @DisplayName("Constructor copia de NoAttendedState")
    public void testConstructorCopia_NoAttended() {
        NoAttendedState original = new NoAttendedState(appointment);
        NoAttendedState copia = new NoAttendedState(original);
        
        assertEquals(original.getContext(), copia.getContext());
    }

    @Test
    @DisplayName("Constructor copia de CanceledState")
    public void testConstructorCopia_Canceled() {
        CanceledState original = new CanceledState(appointment);
        CanceledState copia = new CanceledState(original);
        
        assertEquals(original.getContext(), copia.getContext());
    }


    // ============================================
    // Tests de Identidad de Objetos
    // ============================================

    @Test
    @DisplayName("Cada transición crea una nueva instancia de estado")
    public void testNuevaInstancia_enCadaTransicion() {
        CreatedState estado1 = (CreatedState) appointment.getState();
        estado1.toAttended();
        AttendedState estado2 = (AttendedState) appointment.getState();
        
        assertNotSame(estado1, estado2,
                "Debe crear una nueva instancia en la transición");
    }

    @Test
    @DisplayName("Estados diferentes son objetos distintos")
    public void testDistintosObjetos_estadosDiferentes() {
        CreatedState created = new CreatedState(appointment);
        AttendedState attended = new AttendedState(appointment);
        RescheduledState rescheduled = new RescheduledState(appointment);
        
        assertNotSame(created, attended);
        assertNotSame(attended, rescheduled);
        assertNotSame(created, rescheduled);
    }


    // ============================================
    // Tests de Flujos de Negocio Realistas
    // ============================================

    @Test
    @DisplayName("Escenario: Cita atendida normalmente")
    public void testEscenario_CitaAtendida() {
        // Cita creada
        assertTrue(appointment.getState() instanceof CreatedState);
        assertEquals(1, appointment.getPatientId());
        
        // Paciente asiste
        CreatedState created = (CreatedState) appointment.getState();
        created.toAttended();
        
        assertTrue(appointment.getState() instanceof AttendedState);
        assertEquals(1, appointment.getPatientId()); // Datos preservados
    }

    @Test
    @DisplayName("Escenario: Cita reprogramada una vez y luego asistida")
    public void testEscenario_CitaReprogramadaUnaVez() {
        CreatedState created = (CreatedState) appointment.getState();
        created.toRescheduled();
        
        assertTrue(appointment.getState() instanceof RescheduledState);
        
        RescheduledState rescheduled = (RescheduledState) appointment.getState();
        rescheduled.updateRescheduleInfo("Nueva cita: 2026-09-01");
        rescheduled.toAttended();
        
        assertTrue(appointment.getState() instanceof AttendedState);
    }

    @Test
    @DisplayName("Escenario: Cita no asistida por el paciente")
    public void testEscenario_CitaNoAsistida() {
        CreatedState created = (CreatedState) appointment.getState();
        created.toNoAttended();
        
        assertTrue(appointment.getState() instanceof NoAttendedState);
        
        NoAttendedState noAttended = (NoAttendedState) appointment.getState();
        noAttended.recordNoShow();
    }

    @Test
    @DisplayName("Escenario: Cita cancelada por la clínica")
    public void testEscenario_CitaCancelada() {
        CreatedState created = (CreatedState) appointment.getState();
        created.toCanceled();
        
        assertTrue(appointment.getState() instanceof CanceledState);
        
        CanceledState canceled = (CanceledState) appointment.getState();
        canceled.markAsCanceled();
    }

    @Test
    @DisplayName("Escenario: Cita reprogramada 3 veces y finalmente asistida")
    public void testEscenario_MultiplesReprogramaciones() {
        CreatedState created = (CreatedState) appointment.getState();
        created.toRescheduled();
        
        // Tres reprogramaciones
        for (int i = 1; i <= 3; i++) {
            RescheduledState rescheduled = (RescheduledState) appointment.getState();
            rescheduled.updateRescheduleInfo("Nueva fecha " + i);
            if (i < 3) {
                rescheduled.toRescheduled();
            }
        }
        
        // Finalmente asistida
        RescheduledState final_state = (RescheduledState) appointment.getState();
        final_state.toAttended();
        
        assertTrue(appointment.getState() instanceof AttendedState);
        assertEquals(1, appointment.getPatientId());
    }


    // ============================================
    // Tests de Validación de Tipos
    // ============================================

    @Test
    @DisplayName("Validar que estado es instancia de AppointmentState")
    public void testTipos_estadoEsAppointmentState() {
        AppointmentState state = appointment.getState();
        assertNotNull(state);
        assertTrue(state instanceof AppointmentState);
    }

    @Test
    @DisplayName("Validar tipos específicos después de transiciones")
    public void testTipos_validacionPostTransicion() {
        CreatedState created = (CreatedState) appointment.getState();
        created.toAttended();
        
        AppointmentState newState = appointment.getState();
        assertTrue(newState instanceof AttendedState);
        assertTrue(newState instanceof AppointmentState);
    }
}
