package com.piedraazul.appointment_service.application.service;

import com.piedraazul.appointment_service.application.dto.AppointmentDTO;
import com.piedraazul.appointment_service.application.dto.CreateAppointmentDTO;
import com.piedraazul.appointment_service.application.dto.UpdateAppointmentDTO;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.domain.model.Appointment;
import com.piedraazul.appointment_service.domain.model.PatientRef;
import com.piedraazul.appointment_service.domain.model.ProfessionalRef;
import com.piedraazul.appointment_service.domain.port.in.AppointmentServicePort;
import com.piedraazul.appointment_service.domain.port.out.AppointmentRepositoryPort;
import com.piedraazul.appointment_service.domain.port.out.PatientRefRepositoryPort;
import com.piedraazul.appointment_service.domain.port.out.ProfessionalRefRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService implements AppointmentServicePort {

    private final AppointmentRepositoryPort appointmentRepository;
    private final ProfessionalRefRepositoryPort professionalRefRepository;
    private final PatientRefRepositoryPort patientRefRepository;
    private final FestivosService festivosService;

    @Override
    public Appointment create(CreateAppointmentDTO dto) {
        PatientRef patientRef = patientRefRepository.findById(dto.getCodPatient())
                .orElseThrow(() -> new RuntimeException("No existe el paciente con código: " + dto.getCodPatient()));

        ProfessionalRef professionalRef = professionalRefRepository.findById(dto.getCodProf())
                .orElseThrow(() -> new RuntimeException("No existe el profesional con código: " + dto.getCodProf()));

        // Validar que el paciente no tenga una cita en estado "Scheduled" (Agendada)
        boolean hasScheduledAppointment = appointmentRepository.findByPatientRef(patientRef)
                .stream()
                .anyMatch(a -> a.getStatusApp() == StatusAppointment.Scheduled);
        if (hasScheduledAppointment) {
            throw new RuntimeException(
                    "El paciente ya tiene una cita agendada. No puede agendar otra hasta que la cita actual sea completada o cancelada.");
        }

        Appointment appointment = new Appointment(
                professionalRef,
                patientRef,
                dto.getDateApp(),
                dto.getTimeApp(),
                dto.getDescApp());
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
    public List<Appointment> findByDateApp(LocalDate dateApp) {
        return appointmentRepository.findByDateApp(dateApp);
    }

    @Override
    public List<Appointment> findBySpecialityProf(SpecialityProfEnum specialityProf) {
        return appointmentRepository.findByProfessionalRefSpecialityProf(specialityProf);
    }

    @Override
    public Appointment update(Long id, UpdateAppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));

        if (dto.getDateApp() != null || dto.getTimeApp() != null) {
            LocalDate newDate = dto.getDateApp() != null ? dto.getDateApp() : appointment.getDateApp();
            LocalTime newTime = dto.getTimeApp() != null ? dto.getTimeApp() : appointment.getTimeApp();
            appointment.reschedule(newDate, newTime);
        }

        if (dto.getDescApp() != null) {
            appointment.updateDescription(dto.getDescApp());
        }

        if (dto.getStatusApp() != null) {
            if (dto.getStatusApp() == StatusAppointment.Cancelled
                    && appointment.getStatusApp() != StatusAppointment.Cancelled) {
                appointment.cancel();
            } else if (dto.getStatusApp() == StatusAppointment.Completed
                    && appointment.getStatusApp() != StatusAppointment.Completed) {
                appointment.complete();
            }
        }

        return appointmentRepository.save(appointment);
    }

    @Override
    public boolean cancel(Long id) {
        Optional<Appointment> opt = appointmentRepository.findById(id);
        if (opt.isEmpty())
            return false;
        Appointment appointment = opt.get();
        appointment.cancel();
        appointmentRepository.save(appointment);
        return true;
    }

    @Override
    public List<AppointmentDTO> generateAvailableSlots(Long codProf, LocalDate date) {
        List<AppointmentDTO> availableSlots = new ArrayList<>();

        List<ProfessionalRef> professionals;
        if (codProf != null) {
            professionals = professionalRefRepository.findById(codProf)
                    .map(List::of).orElse(List.of());
        } else {
            professionals = professionalRefRepository.findAll();
        }

        List<Appointment> occupied = appointmentRepository.findByDateAppAndStatusAppNot(date,
                StatusAppointment.Cancelled);

        Set<String> busyKeys = occupied.stream()
                .map(a -> a.getProfessionalRef().getCodProf() + "-" + a.getTimeApp().toString())
                .collect(Collectors.toSet());

        for (ProfessionalRef prof : professionals) {
            // FIX: usar horario real del profesional
            LocalTime startDay = prof.getArrivalTime() != null ? prof.getArrivalTime() : LocalTime.of(7, 0);
            LocalTime endDay = prof.getDepartureTime() != null ? prof.getDepartureTime() : LocalTime.of(18, 0);

            // FIX: respetar días no laborables de la semana
            if (prof.getUnavailableDaysList().contains(date.getDayOfWeek())) {
                continue;
            }

            Integer interval = prof.getAttentionInterval();
            if (interval == null || interval <= 0)
                interval = 30;

            LocalTime currentTime = startDay;
            while (!currentTime.plusMinutes(interval).isAfter(endDay)) {
                if (date.equals(LocalDate.now()) && currentTime.isBefore(LocalTime.now())) {
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

            // Saltar festivos
            if (festivosService.esFestivo(dateSearch)) {
                dateSearch = dateSearch.plusDays(1);
                continue;
            }

            // Obtener profesionales de esa especialidad
            List<ProfessionalRef> professionals = professionalRefRepository.findBySpecialityProf(speciality);

            // Obtener citas ocupadas ese día (no canceladas)
            List<Appointment> occupied = appointmentRepository.findByDateAppAndStatusAppNot(dateSearch,
                    StatusAppointment.Cancelled);

            Set<String> busyKeys = occupied.stream()
                    .map(a -> a.getProfessionalRef().getCodProf() + "-" + a.getTimeApp().toString())
                    .collect(Collectors.toSet());

            for (ProfessionalRef prof : professionals) {
                Integer interval = prof.getAttentionInterval();
                if (interval == null || interval <= 0)
                    interval = 30;

                // FIX: usar horario real del profesional
                LocalTime currentTime = prof.getArrivalTime() != null ? prof.getArrivalTime() : LocalTime.of(7, 0);
                LocalTime endDay = prof.getDepartureTime() != null ? prof.getDepartureTime() : LocalTime.of(18, 0);

                // FIX: respetar días no laborables
                if (prof.getUnavailableDaysList().contains(dateSearch.getDayOfWeek())) {
                    continue;
                }

                while (!currentTime.plusMinutes(interval).isAfter(endDay)) {
                    // Si es hoy, evitar horas pasadas
                    if (dateSearch.equals(LocalDate.now()) && currentTime.isBefore(now)) {
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
        List<ProfessionalRef> professionals = professionalRefRepository.findBySpecialityProf(speciality);

        List<Appointment> occupied = appointmentRepository
                .findByDateAppAndStatusAppNot(date, StatusAppointment.Cancelled);

        Set<String> busyKeys = occupied.stream()
                .map(a -> a.getProfessionalRef().getCodProf() + "-" + a.getTimeApp().toString())
                .collect(Collectors.toSet());

        for (ProfessionalRef prof : professionals) {
            // Usar horario real del profesional
            LocalTime profStart = prof.getArrivalTime() != null ? prof.getArrivalTime() : LocalTime.of(7, 0);
            LocalTime profEnd = prof.getDepartureTime() != null ? prof.getDepartureTime() : LocalTime.of(18, 0);

            // Respetar días no laborables
            if (prof.getUnavailableDaysList().contains(date.getDayOfWeek())) {
                continue;
            }

            Integer interval = prof.getAttentionInterval();
            if (interval == null || interval <= 0)
                interval = 30;

            LocalTime currentTime = profStart;
            while (!currentTime.plusMinutes(interval).isAfter(profEnd)) {
                if (date.equals(LocalDate.now()) && currentTime.isBefore(now)) {
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

        for (ProfessionalRef prof : professionals) {
            // FIX: usar horario real del profesional
            LocalTime profStart = prof.getArrivalTime() != null ? prof.getArrivalTime() : LocalTime.of(7, 0);
            LocalTime profEnd = prof.getDepartureTime() != null ? prof.getDepartureTime() : LocalTime.of(18, 0);

            // FIX: respetar días no laborables
            if (prof.getUnavailableDaysList().contains(targetDate.getDayOfWeek())) {
                continue;
            }

            Integer interval = prof.getAttentionInterval();
            if (interval == null || interval <= 0)
                interval = 30;

            LocalTime currentTime = profStart;
            while (!currentTime.plusMinutes(interval).isAfter(profEnd)) {
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