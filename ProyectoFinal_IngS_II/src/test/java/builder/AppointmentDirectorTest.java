package builder;

import enums.StatusAppointment;
import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppointmentDirectorTest {
    
    private AppointmentDirector director;

    @Test
    public void testManualAppointment() {
        this.director = new AppointmentDirector();
        
        director.setAppointmentBuilder(new ManualAppointmentBuilder());
        StatusAppointment statusManual = StatusAppointment.Cancelled;

        director.buildManualAppointment(1, 100, LocalDate.now(), LocalTime.of(9, 0), "Consulta general", statusManual);
        Appointment cita = director.getAppointment();

        assertNotNull(cita);
        assertEquals(1, cita.getPatientId());
        assertEquals(StatusAppointment.Cancelled, cita.getStatus());
    }

    @Test
    public void testSelfServiceAppointment() {
        this.director = new AppointmentDirector();
        
        director.setAppointmentBuilder(new SelfServiceAppointmentBuilder());

        director.buildSelfServiceAppointment(2, 200, LocalDate.now().plusDays(1), LocalTime.of(10, 30), "Agendada por paciente");
        Appointment cita = director.getAppointment();

        assertNotNull(cita);
        assertEquals(2, cita.getPatientId());
        assertEquals(StatusAppointment.Scheduled, cita.getStatus());
    }

    @Test
    public void testRescheduledAppointment() {
        this.director = new AppointmentDirector();
        
        director.setAppointmentBuilder(new RescheduledAppointmentBuilder());

        director.buildRescheduledAppointment(3, 300, LocalDate.now().plusDays(2), LocalTime.of(14, 0), "Cambio de horario");
        Appointment cita = director.getAppointment();

        assertNotNull(cita);
        assertEquals(3, cita.getPatientId());
        assertEquals(StatusAppointment.Rescheduled, cita.getStatus());
    }
}