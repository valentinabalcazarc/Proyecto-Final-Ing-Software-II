package com.piedraazul.appointment_service.controller;

import com.piedraazul.appointment_service.dto.AppointmentDTO;
import com.piedraazul.appointment_service.dto.UpdateAppointmentDTO;
import com.piedraazul.appointment_service.enums.StatusAppointment;
import com.piedraazul.appointment_service.model.Appointment;
import com.piedraazul.appointment_service.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

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
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        // Si no pasan fecha, usar hoy por defecto
        LocalDate targetDate = (date != null) ? date : LocalDate.now();

        List<AppointmentDTO> slots = appointmentService.generateAvailableSlots(codProf, targetDate);

        if (slots.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return appointmentService.findById(id)
                .map(ResponseEntity::ok)
                .<ResponseEntity<?>>map(r -> r)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró la cita con id: " + id));
    }

    @GetMapping("/professional/{codProf}")
    public ResponseEntity<?> findByCodProf(@PathVariable Long codProf) {
        List<Appointment> appointments = appointmentService.findByCodProf(codProf);
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{codPatient}")
    public ResponseEntity<?> findByCodPatient(@PathVariable Long codPatient) {
        List<Appointment> appointments = appointmentService.findByCodPatient(codPatient);
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> findByStatus(@PathVariable StatusAppointment status) {
        List<Appointment> appointments = appointmentService.findByStatus(status);
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/professional/{codProf}/date/{date}")
    public ResponseEntity<?> findByCodProfAndDate(
            @PathVariable Long codProf,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Appointment> appointments = appointmentService.findByCodProfAndDate(codProf, date);
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> findByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Appointment> appointments = appointmentService.findByDateApp(date);
        if (appointments.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(appointments);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AppointmentDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
                            .toList()
            );
        }
        try {
            Appointment newApp = appointmentService.create(dto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newApp.getCodApp())
                    .toUri();
            return ResponseEntity.created(location).body(newApp);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody UpdateAppointmentDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
                            .toList()
            );
        }
        try {
            Appointment updated = appointmentService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        boolean cancelled = appointmentService.cancel(id);
        if (!cancelled) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la cita con id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}