package services;

import java.time.LocalDate;
import java.util.List;
import models.Appointment;
import models.AppointmentRep;
import repository.AppointmentRepository;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public boolean registerAppointment(Appointment appointment) {

        if (appointment == null) {
            return false;
        }

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Object[]> getAppointmentsForTable() {
        return appointmentRepository.findAllForTable();
    }
    
    @Override
    public List<AppointmentRep> getAppointmentForReport(){
        return appointmentRepository.findAllForReport();
    }
    
    @Override
    public List<Object[]> searchAppointments(Integer codProf, LocalDate fecha) {
        return appointmentRepository.findFiltered(codProf, fecha);
    }
}