package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.dto.AppointmentDTO;
import com.piedraazul.appointment_service.dto.UpdateAppointmentDTO;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.model.Appointment;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment create(AppointmentDTO dto);
    Optional<Appointment> findById(Long id);
    List<Appointment> findAll();
    List<Appointment> findByStatus(StatusAppointment status);
    List<Appointment> findByCodProf(Long codProf);
    List<Appointment> findByCodPatient(Long codPatient);
    List<Appointment> findByCodProfAndDate(Long codProf, LocalDate date);
    Appointment update(Long id, UpdateAppointmentDTO dto);
    boolean cancel(Long id);
}