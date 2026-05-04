package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.dto.AppointmentDTO;
import com.piedraazul.appointment_service.dto.CreateAppointmentDTO;
import com.piedraazul.appointment_service.dto.UpdateAppointmentDTO;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.model.Appointment;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment create(CreateAppointmentDTO dto);
    Optional<Appointment> findById(Long id);
    List<Appointment> findAll();
    List<Appointment> findByStatus(StatusAppointment status);
    List<Appointment> findByCodProf(Long codProf);
    List<Appointment> findByCodPatient(Long codPatient);
    List<Appointment> findByCodProfAndDate(Long codProf, LocalDate date);
    Appointment update(Long id, UpdateAppointmentDTO dto);
    boolean cancel(Long id);
    List<AppointmentDTO> generateAvailableSlots(Long codProf, LocalDate date);
    List<Appointment> findByDateApp(LocalDate dateApp);
    List<Appointment> findBySpecialityProf(SpecialityProfEnum specialityProf);
    AppointmentDTO findFirstAvailableBySpeciality(SpecialityProfEnum speciality);
    List<AppointmentDTO> generateBySpeciality(SpecialityProfEnum speciality);
    List<AppointmentDTO> generateAvailableSlots(Long codProf, LocalDate date, SpecialityProfEnum speciality);
}

