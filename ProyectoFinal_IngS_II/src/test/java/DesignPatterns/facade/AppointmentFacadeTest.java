package DesignPatterns.facade;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import models.Appointment;
import models.Patient;
import services.PatientService;
import services.AppointmentService;

@ExtendWith(MockitoExtension.class)
public class AppointmentFacadeTest {

    @Mock
    private PatientService patientServiceMock;

    @Mock
    private AppointmentService appointmentServiceMock;

    @Test
    public void testScheduleAppointment_Simulation() {
        AppointmentFacade facade = new AppointmentFacade(patientServiceMock, appointmentServiceMock);

        int cedulaPrueba = 123;
        Patient p = new Patient();
        p.setIdPatient(cedulaPrueba); 

        Appointment a = new Appointment();

        Patient pRegistrado = new Patient();
        pRegistrado.setCodPatient(99);
        pRegistrado.setIdPatient(cedulaPrueba);

       
        when(patientServiceMock.findByCed(cedulaPrueba)).thenReturn(null, pRegistrado);

        when(patientServiceMock.regPatient(any(Patient.class))).thenReturn(true);
        when(appointmentServiceMock.registerAppointment(any(Appointment.class))).thenReturn(true);

        int resultado = facade.scheduleAppointment(p, a);

        assertEquals(0, resultado, "El Facade debería retornar éxito (0)");
        assertEquals(99, a.getPatientId(), "La cita debería tener el ID del paciente simulado (99)");

        verify(patientServiceMock, times(2)).findByCed(cedulaPrueba);
        verify(appointmentServiceMock).registerAppointment(a);
    }
}