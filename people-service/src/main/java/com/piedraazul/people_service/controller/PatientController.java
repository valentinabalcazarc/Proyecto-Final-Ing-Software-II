package com.piedraazul.people_service.controller;

import com.piedraazul.people_service.dto.PatientDTO;
import com.piedraazul.people_service.dto.UpdatePatientDTO;
import com.piedraazul.people_service.model.Patient;
import com.piedraazul.people_service.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Patient> patients = patientService.findAll();
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{idPatient}")
    public ResponseEntity<?> findById(@PathVariable Long idPatient) {
        return patientService.findByIdPatient(idPatient)
                .map(ResponseEntity::ok)
                .<ResponseEntity<?>>map(r -> r)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró el paciente con id: " + idPatient));
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody PatientDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
                            .toList()
            );
        }
        try {
            Patient newPatient = patientService.register(dto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newPatient.getCodPatient())
                    .toUri();
            return ResponseEntity.created(location).body(newPatient);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody UpdatePatientDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
                            .toList()
            );
        }
        try {
            Patient updated = patientService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            patientService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}