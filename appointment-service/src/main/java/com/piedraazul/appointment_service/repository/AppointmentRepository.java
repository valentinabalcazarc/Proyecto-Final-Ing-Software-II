package com.piedraazul.appointment_service.repository;

import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.model.Appointment;
import com.piedraazul.appointment_service.model.PatientRef;
import com.piedraazul.appointment_service.model.ProfessionalRef;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByProfessionalRef(ProfessionalRef professionalRef);
    List<Appointment> findByPatientRef(PatientRef patientRef);
    List<Appointment> findByStatusApp(StatusAppointment status);
    List<Appointment> findByProfessionalRefAndDateApp(ProfessionalRef professionalRef, LocalDate date);
}