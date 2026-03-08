package services;

import java.time.LocalDate;
import java.util.List;
import models.Appointment;
import models.AppointmentRep;

public interface AppointmentService {

    boolean registerAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    List<Object[]> getAppointmentsForTable();

    List<AppointmentRep> getAppointmentForReport();
    
    List<Object[]> searchAppointments(Integer codProf, LocalDate fecha);
}