package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence;

import com.piedraazul.appointment_service.domain.model.Appointment;
import com.piedraazul.appointment_service.domain.model.PatientRef;
import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.domain.port.out.AppointmentRepositoryPort;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper.AppointmentMapper;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper.PatientRefMapper;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.mapper.ProfessionalRefMapper;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.repository.AppointmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentPersistenceAdapter implements AppointmentRepositoryPort {

    private final AppointmentJpaRepository appointmentJpaRepository;
    private final AppointmentMapper appointmentMapper;
    private final ProfessionalRefMapper professionalRefMapper;
    private final PatientRefMapper patientRefMapper;

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = appointmentMapper.toEntity(appointment);
        AppointmentEntity saved = appointmentJpaRepository.save(entity);
        return appointmentMapper.toDomain(saved);
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentJpaRepository.findById(id).map(appointmentMapper::toDomain);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentJpaRepository.findAll().stream()
                .map(appointmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByProfessionalRef(ProfessionalRef professionalRef) {
        return appointmentJpaRepository.findByProfessionalRef(professionalRefMapper.toEntity(professionalRef))
                .stream().map(appointmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByPatientRef(PatientRef patientRef) {
        return appointmentJpaRepository.findByPatientRef(patientRefMapper.toEntity(patientRef))
                .stream().map(appointmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByStatusApp(StatusAppointment status) {
        return appointmentJpaRepository.findByStatusApp(status)
                .stream().map(appointmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByProfessionalRefAndDateApp(ProfessionalRef professionalRef, LocalDate date) {
        return appointmentJpaRepository.findByProfessionalRefAndDateApp(professionalRefMapper.toEntity(professionalRef), date)
                .stream().map(appointmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByDateApp(LocalDate date) {
        return appointmentJpaRepository.findByDateApp(date)
                .stream().map(appointmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByProfessionalRefSpecialityProf(SpecialityProfEnum speciality) {
        return appointmentJpaRepository.findByProfessionalRefSpecialityProf(speciality)
                .stream().map(appointmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByDateAppAndStatusAppNot(LocalDate date, StatusAppointment status) {
        return appointmentJpaRepository.findByDateAppAndStatusAppNot(date, status)
                .stream().map(appointmentMapper::toDomain).collect(Collectors.toList());
    }
}
