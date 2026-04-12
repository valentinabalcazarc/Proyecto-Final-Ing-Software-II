package com.piedraazul.appointment_service.repository;

import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByCodProf(Long codProf);
    List<Appointment> findByCodPatient(Long codPatient);
    List<Appointment> findByStatusApp(StatusAppointment status);
    List<Appointment> findByCodProfAndDateApp(Long codProf, LocalDate date);
}