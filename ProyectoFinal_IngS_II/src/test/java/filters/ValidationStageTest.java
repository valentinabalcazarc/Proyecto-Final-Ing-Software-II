package filters;

import models.Appointment;
import enums.StatusAppointment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationStageTest {

    private ValidationStage filter;

    @BeforeEach
    public void setUp() {
        filter = new ValidationStage();
    }

    private Appointment crearCita(int patientId) {
        Appointment cita = new Appointment();
        cita.setPatientId(patientId);
        cita.setDate(LocalDate.now());
        cita.setTime(LocalTime.of(9, 0));
        cita.setStatus(StatusAppointment.Scheduled);
        return cita;
    }

    @Test
    public void testFilter_listaConUnaCita_devuelveTrue() {
        List<Appointment> lista = new ArrayList<>();
        lista.add(crearCita(1));
        assertTrue(filter.filter(lista),
                "Una lista con elementos debe devolver true (llena)");
    }

    @Test
    public void testFilter_listaVacia_devuelveFalse() {
        List<Appointment> lista = new ArrayList<>();
        assertFalse(filter.filter(lista),
                "Una lista vacía debe devolver false");
    }

    @Test
    public void testFilter_listaConMultiplesCitas_devuelveTrue() {
        List<Appointment> lista = new ArrayList<>();
        lista.add(crearCita(1));
        lista.add(crearCita(2));
        lista.add(crearCita(3));
        assertTrue(filter.filter(lista));
    }

    @Test
    public void testFilter_listaInmutableVacia_devuelveFalse() {
        List<Appointment> lista = Collections.emptyList();
        assertFalse(filter.filter(lista));
    }

    @Test
    public void testFilter_listaDespuesDeAgregarYLimpiar_devuelveFalse() {
        List<Appointment> lista = new ArrayList<>();
        lista.add(crearCita(5));
        lista.clear();
        assertFalse(filter.filter(lista));
    }

    @Test
    public void testFilter_retornoEsBoolean() {
        List<Appointment> lista = new ArrayList<>();
        Object resultado = filter.filter(lista);
        assertInstanceOf(Boolean.class, resultado);
    }
}
