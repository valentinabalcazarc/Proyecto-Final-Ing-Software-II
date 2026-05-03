package com.piedraazul.people_service.controller;

import com.piedraazul.people_service.dto.UnavailableDayDTO;
import com.piedraazul.people_service.model.UnavailableDay;
import com.piedraazul.people_service.service.UnavailableDayService;
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
@RequestMapping("unavailable-days")
@RequiredArgsConstructor
public class UnavailableDayController {

    private final UnavailableDayService unavailableDayService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UnavailableDayDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
                            .toList()
            );
        }
        try {
            UnavailableDay saved = unavailableDayService.create(dto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();
            return ResponseEntity.created(location).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/professional/{codProf}")
    public ResponseEntity<?> findByProfessional(@PathVariable Long codProf) {
        try {
            List<UnavailableDay> days = unavailableDayService.findByProfessional(codProf);
            if (days.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(days);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = unavailableDayService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Día no laborable no encontrado con id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}