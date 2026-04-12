package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.dto.AppointmentDTO;
import com.piedraazul.appointment_service.dto.UpdateAppointmentDTO;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.model.Appointment;
import com.piedraazul.appointment_service.model.PatientRef;
import com.piedraazul.appointment_service.model.ProfessionalRef;
import com.piedraazul.appointment_service.repository.AppointmentRepository;
import com.piedraazul.appointment_service.repository.PatientRefRepository;
import com.piedraazul.appointment_service.repository.ProfessionalRefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ProfessionalRefRepository professionalRefRepository;
    private final PatientRefRepository patientRefRepository;

    @Override
    public Appointment create(AppointmentDTO dto) {
        PatientRef patientRef = patientRefRepository.findById(dto.getCodPatient())
                .orElseThrow(() -> new RuntimeException("No existe el paciente con código: " + dto.getCodPatient()));

        ProfessionalRef professionalRef = professionalRefRepository.findById(dto.getCodProf())
                .orElseThrow(() -> new RuntimeException("No existe el profesional con código: " + dto.getCodProf()));

        Appointment appointment = new Appointment();
        appointment.setPatientRef(patientRef);
        appointment.setProfessionalRef(professionalRef);
        appointment.setDateApp(dto.getDateApp());
        appointment.setTimeApp(dto.getTimeApp());
        appointment.setDescApp(dto.getDescApp());
        appointment.setStatusApp(StatusAppointment.Scheduled);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> findByCodProf(Long codProf) {
        ProfessionalRef prof = professionalRefRepository.findById(codProf)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado: " + codProf));
        return appointmentRepository.findByProfessionalRef(prof);
    }

    @Override
    public List<Appointment> findByCodPatient(Long codPatient) {
        PatientRef patient = patientRefRepository.findById(codPatient)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado: " + codPatient));
        return appointmentRepository.findByPatientRef(patient);
    }

    @Override
    public List<Appointment> findByStatus(StatusAppointment status) {
        return appointmentRepository.findByStatusApp(status);
    }

    @Override
    public List<Appointment> findByCodProfAndDate(Long codProf, LocalDate date) {
        ProfessionalRef prof = professionalRefRepository.findById(codProf)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado: " + codProf));
        return appointmentRepository.findByProfessionalRefAndDateApp(prof, date);
    }

    @Override
    public Appointment update(Long id, UpdateAppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        if (dto.getDateApp() != null) appointment.setDateApp(dto.getDateApp());
        if (dto.getTimeApp() != null) appointment.setTimeApp(dto.getTimeApp());
        if (dto.getDescApp() != null) appointment.setDescApp(dto.getDescApp());
        if (dto.getStatusApp() != null) appointment.setStatusApp(dto.getStatusApp());
        return appointmentRepository.save(appointment);
    }

    @Override
    public boolean cancel(Long id) {
        Optional<Appointment> opt = appointmentRepository.findById(id);
        if (opt.isEmpty()) return false;
        Appointment appointment = opt.get();
        appointment.setStatusApp(StatusAppointment.Cancelled);
        appointmentRepository.save(appointment);
        return true;
    }
}