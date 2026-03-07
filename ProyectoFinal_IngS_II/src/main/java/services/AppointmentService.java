package services;

import java.time.LocalDate;
import java.util.List;
import models.Appointment;

public interface AppointmentService {

    boolean registerAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    List<Object[]> getAppointmentsForTable();
    
    List<Object[]> searchAppointments(Integer codProf, LocalDate fecha);
}