package com.piedraazul.app_client.services;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import java.time.LocalDate;
import java.util.List;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.AppointmentRep;

public interface AppointmentService {

    boolean registerAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();
    
    Appointment getFirstAvailableBySpeciality(SpecialityProfEnum speciality);

    public List<Appointment> getGeneretedAppointmentsBySpeciality(SpecialityProfEnum speciality);

    List<Appointment> getGeneretedAppointmentsBySpecialityFiltered(Long codProf, LocalDate fecha, SpecialityProfEnum speciality);

    List<Appointment> getAppointmentsByPatient(Long patientId);

    List<Appointment> searchAppointmentsTyped(Long codProf, LocalDate fecha);

    List<Appointment> getGeneratedAppointmentsTyped();

    List<Appointment> getGeneratedAppointmentsFilteredTyped(Long codProf, LocalDate fecha);

    List<Appointment> getGeneratedAppointmentsBySpecialityTyped(SpecialityProfEnum speciality);

    List<Appointment> getGeneratedAppointmentsBySpecialityFilteredTyped(Long codProf, LocalDate fecha, SpecialityProfEnum speciality);

    boolean saveAppointment(Appointment appointment, Long patientId);

    boolean deleteAppointment(Long appointmentId);
}