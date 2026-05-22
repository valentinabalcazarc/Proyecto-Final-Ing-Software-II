package com.piedraazul.appointment_service.domain.port.out;

import com.piedraazul.appointment_service.domain.model.Appointment;
import com.piedraazul.appointment_service.domain.model.PatientRef;
import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.StatusAppointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepositoryPort {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    List<Appointment> findAll();
    List<Appointment> findByProfessionalRef(ProfessionalRef professionalRef);
    List<Appointment> findByPatientRef(PatientRef patientRef);
    List<Appointment> findByStatusApp(StatusAppointment status);
    List<Appointment> findByProfessionalRefAndDateApp(ProfessionalRef professionalRef, LocalDate date);
    List<Appointment> findByDateApp(LocalDate date);
    List<Appointment> findByProfessionalRefSpecialityProf(SpecialityProfEnum speciality);
    List<Appointment> findByDateAppAndStatusAppNot(LocalDate date, StatusAppointment status);
}
