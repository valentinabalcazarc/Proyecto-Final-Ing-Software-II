package com.piedraazul.appointment_service.domain.port.in;

import com.piedraazul.appointment_service.application.dto.AppointmentDTO;
import com.piedraazul.appointment_service.application.dto.CreateAppointmentDTO;
import com.piedraazul.appointment_service.application.dto.UpdateAppointmentDTO;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.domain.model.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentServicePort {
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
