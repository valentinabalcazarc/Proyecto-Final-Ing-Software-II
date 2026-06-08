package com.piedraazul.appointment_service.infrastructure.adapter.in.web;

import com.piedraazul.appointment_service.application.dto.AppointmentDTO;
import com.piedraazul.appointment_service.application.dto.CreateAppointmentDTO;
import com.piedraazul.appointment_service.application.dto.UpdateAppointmentDTO;
import com.piedraazul.appointment_service.application.service.AppointmentExportService;
import com.piedraazul.appointment_service.enums.SpecialityProfEnum;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.domain.model.Appointment;
import com.piedraazul.appointment_service.domain.port.in.AppointmentServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentServicePort appointmentService;
    private final AppointmentExportService appointmentExportService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Appointment> appointments = appointmentService.findAll();
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/generated")
    public ResponseEntity<?> getGeneratedAppointments(
            @RequestParam(required = false) Long codProf,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) SpecialityProfEnum speciality) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        List<AppointmentDTO> slots = appointmentService.generateAvailableSlots(codProf, targetDate, speciality);

        if (slots.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return appointmentService.findById(id)
                .map(ResponseEntity::ok)
                .<ResponseEntity<?>>map(r -> r)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{codPatient}")
    public ResponseEntity<?> findByCodPatient(@PathVariable Long codPatient) {
        List<Appointment> appointments = appointmentService.findByCodPatient(codPatient);
        if (appointments.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/professional/{codProf}")
    public ResponseEntity<?> findByCodProf(@PathVariable Long codProf) {
        List<Appointment> appointments = appointmentService.findByCodProf(codProf);
        if (appointments.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/professional/{codProf}/date/{date}")
    public ResponseEntity<?> findByCodProfAndDate(
            @PathVariable Long codProf,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Appointment> appointments = appointmentService.findByCodProfAndDate(codProf, date);
        if (appointments.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> findByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Appointment> appointments = appointmentService.findByDateApp(date);
        if (appointments.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> findByStatus(@PathVariable StatusAppointment status) {
        List<Appointment> appointments = appointmentService.findByStatus(status);
        if (appointments.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(appointments);
    }

    // FIX: agregado parámetro opcional fromDate para buscar desde una fecha específica
    @GetMapping("/first-available/{speciality}")
    public ResponseEntity<?> findFirstAvailable(
            @PathVariable SpecialityProfEnum speciality,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate) {
        AppointmentDTO slot = appointmentService.findFirstAvailableBySpeciality(speciality, fromDate);
        if (slot == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(slot);
    }

    @GetMapping("/generated/speciality/{speciality}")
    public ResponseEntity<?> generateBySpeciality(@PathVariable SpecialityProfEnum speciality) {
        List<AppointmentDTO> slots = appointmentService.generateBySpeciality(speciality);
        if (slots.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(slots);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateAppointmentDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            Appointment created = appointmentService.create(dto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(created.getCodApp()).toUri();
            return ResponseEntity.created(location).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateAppointmentDTO dto) {
        try {
            Appointment updated = appointmentService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody UpdateAppointmentDTO dto) {
        try {
            Appointment updated = appointmentService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        boolean cancelled = appointmentService.cancel(id);
        if (!cancelled) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/export")
    public ResponseEntity<?> exportAppointments(
            @RequestParam String format,
            @RequestBody List<Long> ids) {
        try {
            List<Appointment> appointments = ids.stream()
                    .map(id -> appointmentService.findById(id).orElse(null))
                    .filter(app -> app != null)
                    .collect(Collectors.toList());

            byte[] fileBytes = appointmentExportService.exportAppointments(appointments, format);
            String contentType = appointmentExportService.getContentType(format);
            String filename = "citas" + appointmentExportService.getFileExtension(format);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(fileBytes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al exportar: " + e.getMessage());
        }
    }
}