package services;

import enums.SpecialityProfEnum;
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
    
    List<Object[]> getGeneretedAppointments();
    
    List<Object[]> getGeneretedAppointmentsFiltered(Integer codProf, LocalDate fecha);
    
    Appointment getFirstAvailableBySpeciality(SpecialityProfEnum speciality);
    
    List<Object[]> getGeneretedAppointmentsBySpeciality(SpecialityProfEnum speciality);
    
    List<Object[]> getGeneretedAppointmentsBySpecialityFiltered(Integer codProf, LocalDate fecha, SpecialityProfEnum speciality);
}