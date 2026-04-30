package com.piedraazul.appointment_service.service;

import com.piedraazul.appointment_service.dto.AppointmentDTO;
import com.piedraazul.appointment_service.dto.UpdateAppointmentDTO;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.model.Appointment;
import com.piedraazul.appointment_service.model.PatientRef;
import com.piedraazul.appointment_service.model.ProfessionalRef;
import com.piedraazul.appointment_service.repository.AppointmentRepository;
import com.piedraazul.appointment_service.repository.PatientRefRepository;
import com.piedraazul.appointment_service.repository.ProfessionalRefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime; // Agregada
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set; // Agregada
import java.util.stream.Collectors; // Agregada

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ProfessionalRefRepository professionalRefRepository;
    private final PatientRefRepository patientRefRepository;

    private final int HORA_FIN = 18;

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
    public List<Appointment> findByDateApp(LocalDate dateApp){ return appointmentRepository.findByDateApp(dateApp);}

    @Override
    public List<Appointment> findBySpecialityProf(SpecialityProfEnum specialityProf) {
        return appointmentRepository.findByProfessionalRefSpecialityProf(specialityProf);
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

    @Override
    public List<AppointmentDTO> generateAvailableSlots(Long codProf, LocalDate date) {
        List<AppointmentDTO> availableSlots = new ArrayList<>();

        // 1. Obtener profesionales (Usamos el repositorio correcto)
        List<ProfessionalRef> professionals;
        if (codProf != null) {
            professionals = professionalRefRepository.findById(codProf)
                    .map(List::of).orElse(List.of());
        } else {
            professionals = professionalRefRepository.findAll();
            // Nota: Podrías agregar un estado a ProfessionalRef si quieres filtrar activos
        }

        // 2. Obtener citas ocupadas (No canceladas) para la fecha
        List<Appointment> occupied = appointmentRepository.findByDateAppAndStatusAppNot(date, StatusAppointment.Cancelled);

        // Crear set de llaves "codProf-HH:mm"
        Set<String> busyKeys = occupied.stream()
                .map(a -> a.getProfessionalRef().getCodProf() + "-" + a.getTimeApp().toString())
                .collect(Collectors.toSet());

        LocalTime startDay = LocalTime.of(7, 0);
        LocalTime endDay = LocalTime.of(HORA_FIN, 0);

        for (ProfessionalRef prof : professionals) {
            LocalTime currentTime = startDay;
            Integer interval = prof.getAttentionInterval();

            // Validación de seguridad por si el intervalo es nulo o 0
            if (interval == null || interval <= 0) interval = 30;

            while (currentTime.plusMinutes(interval).isBefore(endDay.plusMinutes(1))) {
                // Si es hoy, evitar horas pasadas
                if (date.equals(LocalDate.now()) && !currentTime.isAfter(LocalTime.now())) {
                    currentTime = currentTime.plusMinutes(interval);
                    continue;
                }

                String key = prof.getCodProf() + "-" + currentTime.toString();
                if (!busyKeys.contains(key)) {
                    AppointmentDTO slot = new AppointmentDTO();
                    slot.setDateApp(date);
                    slot.setTimeApp(currentTime);
                    slot.setCodProf(prof.getCodProf());
                    slot.setProfessionalName(prof.getNameProf() + " " + prof.getLastNameProf());
                    slot.setTypeProf(prof.getTypeProf());
                    slot.setSpecialityProf(prof.getSpecialityProf());

                    availableSlots.add(slot);
                }
                currentTime = currentTime.plusMinutes(interval);
            }
        }
        return availableSlots;
    }

    @Override
    public AppointmentDTO findFirstAvailableBySpeciality(SpecialityProfEnum speciality) {
        LocalDate dateSearch = LocalDate.now();
        LocalDate limitDate = dateSearch.plusDays(60);
        LocalTime now = LocalTime.now();

        while (dateSearch.isBefore(limitDate)) {
            // Saltar fines de semana
            if (dateSearch.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    dateSearch.getDayOfWeek() == DayOfWeek.SUNDAY) {
                dateSearch = dateSearch.plusDays(1);
                continue;
            }

            // Obtener profesionales de esa especialidad
            List<ProfessionalRef> professionals =
                    professionalRefRepository.findBySpecialityProf(speciality);

            // Obtener citas ocupadas ese día (no canceladas)
            List<Appointment> occupied =
                    appointmentRepository.findByDateAppAndStatusAppNot(dateSearch, StatusAppointment.Cancelled);

            Set<String> busyKeys = occupied.stream()
                    .map(a -> a.getProfessionalRef().getCodProf() + "-" + a.getTimeApp().toString())
                    .collect(Collectors.toSet());

            for (ProfessionalRef prof : professionals) {
                Integer interval = prof.getAttentionInterval();
                if (interval == null || interval <= 0) interval = 30;

                LocalTime currentTime = LocalTime.of(7, 0);
                LocalTime endDay = LocalTime.of(HORA_FIN, 0);

                while (!currentTime.plusMinutes(interval).isAfter(endDay)) {
                    // Si es hoy, evitar horas pasadas
                    if (dateSearch.equals(LocalDate.now()) && !currentTime.isAfter(now)) {
                        currentTime = currentTime.plusMinutes(interval);
                        continue;
                    }

                    String key = prof.getCodProf() + "-" + currentTime;
                    if (!busyKeys.contains(key)) {
                        // Encontró el primer slot disponible
                        AppointmentDTO slot = new AppointmentDTO();
                        slot.setDateApp(dateSearch);
                        slot.setTimeApp(currentTime);
                        slot.setCodProf(prof.getCodProf());
                        slot.setProfessionalName(prof.getNameProf() + " " + prof.getLastNameProf());
                        slot.setSpecialityProf(prof.getSpecialityProf());
                        slot.setTypeProf(prof.getTypeProf());
                        return slot;
                    }
                    currentTime = currentTime.plusMinutes(interval);
                }
            }
            dateSearch = dateSearch.plusDays(1);
        }
        return null;
    }

    @Override
    public List<AppointmentDTO> generateBySpeciality(SpecialityProfEnum speciality) {
        List<AppointmentDTO> availableSlots = new ArrayList<>();
        LocalDate date = LocalDate.now();
        LocalTime now = LocalTime.now();

        // Filtrar profesionales por especialidad
        List<ProfessionalRef> professionals =
                professionalRefRepository.findBySpecialityProf(speciality);

        List<Appointment> occupied = appointmentRepository
                .findByDateAppAndStatusAppNot(date, StatusAppointment.Cancelled);

        Set<String> busyKeys = occupied.stream()
                .map(a -> a.getProfessionalRef().getCodProf() + "-" + a.getTimeApp().toString())
                .collect(Collectors.toSet());

        LocalTime startDay = LocalTime.of(7, 0);
        LocalTime endDay = LocalTime.of(HORA_FIN, 0);

        for (ProfessionalRef prof : professionals) {
            Integer interval = prof.getAttentionInterval();
            if (interval == null || interval <= 0) interval = 30;

            LocalTime currentTime = startDay;
            while (currentTime.plusMinutes(interval).isBefore(endDay.plusMinutes(1))) {
                if (date.equals(LocalDate.now()) && !currentTime.isAfter(now)) {
                    currentTime = currentTime.plusMinutes(interval);
                    continue;
                }

                String key = prof.getCodProf() + "-" + currentTime;
                if (!busyKeys.contains(key)) {
                    AppointmentDTO slot = new AppointmentDTO();
                    slot.setDateApp(date);
                    slot.setTimeApp(currentTime);
                    slot.setCodProf(prof.getCodProf());
                    slot.setProfessionalName(prof.getNameProf() + " " + prof.getLastNameProf());
                    slot.setSpecialityProf(prof.getSpecialityProf());
                    slot.setTypeProf(prof.getTypeProf());
                    availableSlots.add(slot);
                }
                currentTime = currentTime.plusMinutes(interval);
            }
        }
        return availableSlots;
    }

    @Override
    public List<AppointmentDTO> generateAvailableSlots(Long codProf, LocalDate date, SpecialityProfEnum speciality) {
        List<AppointmentDTO> availableSlots = new ArrayList<>();
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        LocalTime now = LocalTime.now();

        // Obtener profesionales según filtros
        List<ProfessionalRef> professionals;
        if (codProf != null) {
            professionals = professionalRefRepository.findById(codProf)
                    .map(List::of).orElse(List.of());
        } else if (speciality != null) {
            professionals = professionalRefRepository.findBySpecialityProf(speciality);
        } else {
            professionals = professionalRefRepository.findAll();
        }

        List<Appointment> occupied = appointmentRepository
                .findByDateAppAndStatusAppNot(targetDate, StatusAppointment.Cancelled);

        Set<String> busyKeys = occupied.stream()
                .map(a -> a.getProfessionalRef().getCodProf() + "-" + a.getTimeApp().toString())
                .collect(Collectors.toSet());

        LocalTime startDay = LocalTime.of(7, 0);
        LocalTime endDay = LocalTime.of(HORA_FIN, 0);

        for (ProfessionalRef prof : professionals) {
            Integer interval = prof.getAttentionInterval();
            if (interval == null || interval <= 0) interval = 30;

            LocalTime currentTime = startDay;
            while (currentTime.plusMinutes(interval).isBefore(endDay.plusMinutes(1))) {
                if (targetDate.equals(LocalDate.now()) && !currentTime.isAfter(now)) {
                    currentTime = currentTime.plusMinutes(interval);
                    continue;
                }

                String key = prof.getCodProf() + "-" + currentTime;
                if (!busyKeys.contains(key)) {
                    AppointmentDTO slot = new AppointmentDTO();
                    slot.setDateApp(targetDate);
                    slot.setTimeApp(currentTime);
                    slot.setCodProf(prof.getCodProf());
                    slot.setProfessionalName(prof.getNameProf() + " " + prof.getLastNameProf());
                    slot.setSpecialityProf(prof.getSpecialityProf());
                    slot.setTypeProf(prof.getTypeProf());
                    availableSlots.add(slot);
                }
                currentTime = currentTime.plusMinutes(interval);
            }
        }
        return availableSlots;
    }
}