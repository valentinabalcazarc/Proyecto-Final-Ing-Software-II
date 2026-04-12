package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.dto.AppointmentDTO;
import com.piedraazul.appointment_service.dto.UpdateAppointmentDTO;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.model.Appointment;
import com.piedraazul.appointment_service.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment create(AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setCodProf(dto.getCodProf());
        appointment.setCodPatient(dto.getCodPatient());
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
        return appointmentRepository.findByCodProf(codProf);
    }

    @Override
    public List<Appointment> findByCodPatient(Long codPatient) {
        return appointmentRepository.findByCodPatient(codPatient);
    }

    @Override
    public List<Appointment> findByStatus(StatusAppointment status) {
        return appointmentRepository.findByStatusApp(status);
    }

    @Override
    public List<Appointment> findByCodProfAndDate(Long codProf, LocalDate date) {
        return appointmentRepository.findByCodProfAndDateApp(codProf, date);
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