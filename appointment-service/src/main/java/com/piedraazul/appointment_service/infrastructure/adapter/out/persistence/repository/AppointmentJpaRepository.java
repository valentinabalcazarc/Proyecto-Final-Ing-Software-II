package com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.repository;

import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.PatientRefEntity;
import com.piedraazul.appointment_service.infrastructure.adapter.out.persistence.entity.ProfessionalRefEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByProfessionalRef(ProfessionalRefEntity professionalRef);
    List<AppointmentEntity> findByPatientRef(PatientRefEntity patientRef);
    List<AppointmentEntity> findByStatusApp(StatusAppointment status);
    List<AppointmentEntity> findByProfessionalRefAndDateApp(ProfessionalRefEntity ProfessionalRefEntity, LocalDate date);
    List<AppointmentEntity> findByDateAppAndStatusAppNot(LocalDate date, StatusAppointment status);
    List<AppointmentEntity> findByDateApp(LocalDate dateApp);
    List<AppointmentEntity> findByProfessionalRefSpecialityProf(SpecialityProfEnum specialityProf);
}
