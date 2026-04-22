package com.piedraazul.app_client.services;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import java.time.LocalDate;
import java.util.List;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.AppointmentRep;

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

    List<Appointment> getAppointmentsByPatient(Integer patientId);

    List<Appointment> searchAppointmentsTyped(Integer codProf, LocalDate fecha);

    List<Appointment> getGeneratedAppointmentsTyped();

    List<Appointment> getGeneratedAppointmentsFilteredTyped(Integer codProf, LocalDate fecha);

    boolean saveAppointment(Appointment appointment, Integer patientId);

    boolean deleteAppointment(Integer appointmentId);
}