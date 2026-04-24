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
    
    List<Object[]> searchAppointments(Long codProf, LocalDate fecha);
    
    Appointment getFirstAvailableBySpeciality(SpecialityProfEnum speciality);
    
    List<Object[]> getGeneretedAppointmentsBySpeciality(SpecialityProfEnum speciality);
    
    List<Object[]> getGeneretedAppointmentsBySpecialityFiltered(Long codProf, LocalDate fecha, SpecialityProfEnum speciality);

    List<Appointment> getAppointmentsByPatient(Long patientId);

    List<Appointment> searchAppointmentsTyped(Long codProf, LocalDate fecha);

    List<Appointment> getGeneratedAppointmentsTyped();

    List<Appointment> getGeneratedAppointmentsFilteredTyped(Long codProf, LocalDate fecha);

    boolean saveAppointment(Appointment appointment, Long patientId);

    boolean deleteAppointment(Long appointmentId);
}