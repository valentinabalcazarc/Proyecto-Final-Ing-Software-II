package DesignPatterns.templateMethod;

import DesignPatterns.state.AppointmentState;
import DesignPatterns.state.CreatedState;
import DesignPatterns.state.AttendedState;
import DesignPatterns.state.RescheduledState;
import DesignPatterns.state.CanceledState;
import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de pruebas de integración entre el patrón Template Method
 * y el patrón State. Verifica que después de agendar una cita,
 * sus transiciones de estado funcionan correctamente.
 */
@DisplayName("Pruebas de Integración: Template Method + State")
public class AppointmentSchedulerTemplateIntegrationTest {

    private static final LocalDate FECHA = LocalDate.of(2026, 7, 15);
    private static final LocalTime HORA = LocalTime.of(11, 30);

    // Tests: Estado después de AutonomousScheduler
    @Test
    @DisplayName("Cita agendada autónomamente inicia en estado Created")
    public void testAutonomous_citaIniciaEnCreated() {
        Appointment cita = new Appointment();
        cita.setPatientId(1);
        cita.setProfessionalId(10);
        cita.setDate(FECHA);
        cita.setTime(HORA);

        // El estado inicial es CreatedState antes de agendar
        assertTrue(cita.getState() instanceof CreatedState,
            "Toda cita nueva debe iniciar en CreatedState");

        // Agendar no cambia el estado — el estado es manejado por el patrón State
        AppointmentScheduler scheduler = new AutonomousScheduler();
        scheduler.schedule(cita);

        assertTrue(cita.getState() instanceof CreatedState,
            "Después de schedule(), la cita debe seguir en CreatedState");
    }

    @Test
    @DisplayName("Cita agendada autónomamente puede transicionar a Attended")
    public void testAutonomous_citaPuedeTransicionarAAttended() {
        Appointment cita = new Appointment();
        cita.setPatientId(2);
        cita.setProfessionalId(10);
        cita.setDate(FECHA);
        cita.setTime(HORA);

        new AutonomousScheduler().schedule(cita);

        // Simular que el paciente asistió a la cita
        CreatedState createdState = (CreatedState) cita.getState();
        createdState.toAttended();

        assertTrue(cita.getState() instanceof AttendedState,
            "La cita debe poder transicionar a Attended después de ser agendada");
    }

    @Test
    @DisplayName("Cita agendada autónomamente puede ser cancelada")
    public void testAutonomous_citaPuedeSerCancelada() {
        Appointment cita = new Appointment();
        cita.setPatientId(3);
        cita.setProfessionalId(10);
        cita.setDate(FECHA);
        cita.setTime(HORA);

        new AutonomousScheduler().schedule(cita);

        CreatedState createdState = (CreatedState) cita.getState();
        createdState.toCanceled();

        assertTrue(cita.getState() instanceof CanceledState,
            "La cita puede cancelarse después de ser agendada autónomamente");
    }

    @Test
    @DisplayName("Cita agendada autónomamente puede ser reprogramada")
    public void testAutonomous_citaPuedeSerReprogramada() {
        Appointment cita = new Appointment();
        cita.setPatientId(4);
        cita.setProfessionalId(10);
        cita.setDate(FECHA);
        cita.setTime(HORA);

        new AutonomousScheduler().schedule(cita);

        CreatedState createdState = (CreatedState) cita.getState();
        createdState.toRescheduled();

        assertTrue(cita.getState() instanceof RescheduledState,
            "La cita puede reprogramarse después de ser agendada autónomamente");
    }

    // Tests: Estado después de ManualScheduler
    @Test
    @DisplayName("Cita agendada manualmente inicia en estado Created")
    public void testManual_citaIniciaEnCreated() {
        Appointment cita = new Appointment();
        cita.setPatientId(5);
        cita.setProfessionalId(3);
        cita.setDate(FECHA);
        cita.setTime(HORA);

        new ManualScheduler().schedule(cita);

        assertTrue(cita.getState() instanceof CreatedState,
            "La cita agendada manualmente debe iniciar en CreatedState");
    }

    @Test
    @DisplayName("Cita agendada manualmente puede transicionar a Attended")
    public void testManual_citaPuedeTransicionarAAttended() {
        Appointment cita = new Appointment();
        cita.setPatientId(6);
        cita.setProfessionalId(3);
        cita.setDate(FECHA);
        cita.setTime(HORA);

        new ManualScheduler().schedule(cita);

        CreatedState createdState = (CreatedState) cita.getState();
        createdState.toAttended();

        assertTrue(cita.getState() instanceof AttendedState);
        assertEquals(6, cita.getPatientId(), "El patientId debe preservarse");
    }

    // Tests: Flujos Completos de Integración
    @Test
    @DisplayName("Flujo completo: Autonomous -> Created -> Rescheduled -> Attended")
    public void testFlujoCopleto_AutonomousRescheduledAttended() {
        Appointment cita = new Appointment();
        cita.setPatientId(10);
        cita.setProfessionalId(5);
        cita.setDate(FECHA);
        cita.setTime(HORA);

        // 1. Agendar autónomamente
        new AutonomousScheduler().schedule(cita);
        assertTrue(cita.getState() instanceof CreatedState);

        // 2. Reprogramar
        CreatedState createdState = (CreatedState) cita.getState();
        createdState.toRescheduled();
        assertTrue(cita.getState() instanceof RescheduledState);

        // 3. Finalmente asistir
        RescheduledState rescheduledState = (RescheduledState) cita.getState();
        rescheduledState.toAttended();
        assertTrue(cita.getState() instanceof AttendedState);

        assertEquals(10, cita.getPatientId(), "Datos preservados en todo el flujo");
    }

    @Test
    @DisplayName("Flujo completo: Manual -> Created -> Canceled (estado terminal)")
    public void testFlujoCompleto_ManualCancelada() {
        Appointment cita = new Appointment();
        cita.setPatientId(11);
        cita.setProfessionalId(2);
        cita.setDate(FECHA);
        cita.setTime(HORA);

        new ManualScheduler().schedule(cita);
        assertTrue(cita.getState() instanceof CreatedState);

        CreatedState createdState = (CreatedState) cita.getState();
        createdState.toCanceled();
        assertTrue(cita.getState() instanceof CanceledState);

        // Intentar transición desde estado terminal: no debe cambiar
        cita.getState().transitionState();
        assertTrue(cita.getState() instanceof CanceledState,
            "CanceledState es terminal: no permite más transiciones");
    }

    @Test
    @DisplayName("Flujo: dos schedulers distintos, dos citas independientes")
    public void testFlujo_DosSchedulersDosEstadosIndependientes() {
        Appointment cita1 = new Appointment();
        cita1.setPatientId(20);
        cita1.setProfessionalId(1);
        cita1.setDate(FECHA);
        cita1.setTime(LocalTime.of(7, 0));

        Appointment cita2 = new Appointment();
        cita2.setPatientId(21);
        cita2.setProfessionalId(2);
        cita2.setDate(FECHA);
        cita2.setTime(LocalTime.of(8, 0));

        new AutonomousScheduler().schedule(cita1);
        new ManualScheduler().schedule(cita2);

        // Transicionar cita1 a Attended
        ((CreatedState) cita1.getState()).toAttended();

        // cita2 no debe ser afectada
        assertTrue(cita1.getState() instanceof AttendedState);
        assertTrue(cita2.getState() instanceof CreatedState,
            "El estado de cita2 no debe verse afectado por cita1");
    }
}