package models;

import enums.StatusAppointment;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {

    private Appointment appointment;
    private static final LocalDate FECHA = LocalDate.of(2026, 5, 10);
    private static final LocalTime HORA  = LocalTime.of(9, 30);

    @BeforeEach
    public void setUp() {
        appointment = new Appointment();
    }

    @Test
    public void testConstructorVacio_estadoPorDefectoEsScheduled() {
        assertEquals(StatusAppointment.Scheduled, appointment.getStatus());
    }

    @Test
    public void testConstructorCompleto_asignaTodosLosCampos() {
        Appointment cita = new Appointment(1, 10, 20, FECHA, HORA, "Consulta", StatusAppointment.Completed);
        assertEquals(1, cita.getId());
        assertEquals(10, cita.getPatientId());
        assertEquals(20, cita.getProfessionalId());
        assertEquals(FECHA, cita.getDate());
        assertEquals(HORA, cita.getTime());
        assertEquals("Consulta", cita.getDescription());
        assertEquals(StatusAppointment.Completed, cita.getStatus());
    }

    @Test
    public void testSetId_asignaIdCorrectamente() {
        appointment.setId(99);
        assertEquals(99, appointment.getId());
    }

    @Test
    public void testSetPatientId_asignaIdPaciente() {
        appointment.setPatientId(5);
        assertEquals(5, appointment.getPatientId());
    }

    @Test
    public void testSetProfessionalId_asignaIdProfesional() {
        appointment.setProfessionalId(15);
        assertEquals(15, appointment.getProfessionalId());
    }

    @Test
    public void testSetDate_asignaFecha() {
        appointment.setDate(FECHA);
        assertEquals(FECHA, appointment.getDate());
    }

    @Test
    public void testSetTime_asignaHora() {
        appointment.setTime(HORA);
        assertEquals(HORA, appointment.getTime());
    }

    @Test
    public void testSetDescription_asignaDescripcion() {
        appointment.setDescription("Control de rutina");
        assertEquals("Control de rutina", appointment.getDescription());
    }

    @Test
    public void testSetStatus_actualizaEstado() {
        appointment.setStatus(StatusAppointment.Cancelled);
        assertEquals(StatusAppointment.Cancelled, appointment.getStatus());
    }

    @Test
    public void testSetStatus_cambiaDeScheduledARescheduled() {
        appointment.setStatus(StatusAppointment.Rescheduled);
        assertEquals(StatusAppointment.Rescheduled, appointment.getStatus());
    }

    @Test
    public void testCitaNueva_camposNumerosInicialesEnCero() {
        assertEquals(0, appointment.getId());
        assertEquals(0, appointment.getPatientId());
        assertEquals(0, appointment.getProfessionalId());
    }

    @Test
    public void testCitaNueva_camposStringYFechaInicianEnNull() {
        assertNull(appointment.getDescription());
        assertNull(appointment.getDate());
        assertNull(appointment.getTime());
    }
}
