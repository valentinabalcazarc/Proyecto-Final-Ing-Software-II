package com.piedraazul.people_service.controller;

import com.piedraazul.people_service.dto.ProfessionalDTO;
import com.piedraazul.people_service.dto.UpdateProfessionalDTO;
import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.model.Professional;
import com.piedraazul.people_service.service.ProfessionalService;
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
@RequestMapping("professionals")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Professional> professionals = professionalService.findAll();
        if (professionals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(professionals);
    }

    @GetMapping("/user/{codUser}")
    public ResponseEntity<?> findByCodUser(@PathVariable Long codUser) {
        return professionalService.findByCodUser(codUser)
                .map(ResponseEntity::ok)
                .<ResponseEntity<?>>map(r -> r)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró el profesional con codUser: " + codUser));
    }

    @GetMapping("/speciality/{speciality}")
    public ResponseEntity<?> findBySpeciality(@PathVariable SpecialityProfEnum speciality) {
        List<Professional> professionals = professionalService.findBySpeciality(speciality);
        if (professionals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(professionals);
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody ProfessionalDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
                            .toList()
            );
        }
        try {
            Professional newProf = professionalService.register(dto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newProf.getCodProf())
                    .toUri();
            return ResponseEntity.created(location).body(newProf);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UpdateProfessionalDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
                            .toList()
            );
        }
        try {
            Professional updated = professionalService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        try {
            professionalService.deactivate(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}