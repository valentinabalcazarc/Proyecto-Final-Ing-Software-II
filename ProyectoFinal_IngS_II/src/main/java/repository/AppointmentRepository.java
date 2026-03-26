package repository;

import enums.SpecialityProfEnum;
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
    List<Object[]> generateAppForTable();
    List<Object[]> filterGeneretedApp(Integer codProf, LocalDate fecha);
    Appointment findFirstAvailableBySpeciality(SpecialityProfEnum speciality);
    List<Object[]> generateAppBySpeciality(SpecialityProfEnum speciality);
    List<Object[]> filterGeneratedAppBySpeciality(Integer codProf, LocalDate fecha, SpecialityProfEnum speciality);
}