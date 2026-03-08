package repository;

import java.time.LocalDate;
import java.util.List;
import models.Appointment;
import models.AppointmentRep;

public interface AppointmentRepository {

    boolean save(Appointment appointment);
    List<Appointment> findAll();
    List<Object[]> findAllForTable();
    List<AppointmentRep> findAllForReport();
    List<Object[]> findFiltered(Integer codProf, LocalDate fecha);
}