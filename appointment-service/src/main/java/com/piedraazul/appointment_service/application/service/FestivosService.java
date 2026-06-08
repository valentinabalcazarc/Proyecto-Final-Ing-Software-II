package com.piedraazul.appointment_service.application.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class FestivosService {

    private final Set<LocalDate> festivos = new HashSet<>();

    public FestivosService() {
        festivos.add(LocalDate.of(2026, 3, 23));
        festivos.add(LocalDate.of(2026, 4, 2));
        festivos.add(LocalDate.of(2026, 4, 3));
        festivos.add(LocalDate.of(2026, 5, 1));
        festivos.add(LocalDate.of(2026, 5, 18));
        festivos.add(LocalDate.of(2026, 6, 8));
        festivos.add(LocalDate.of(2026, 6, 15));
        festivos.add(LocalDate.of(2026, 6, 29));
        festivos.add(LocalDate.of(2026, 7, 20));
        festivos.add(LocalDate.of(2026, 8, 7));
        festivos.add(LocalDate.of(2026, 8, 17));
        festivos.add(LocalDate.of(2026, 10, 12));
        festivos.add(LocalDate.of(2026, 11, 2));
        festivos.add(LocalDate.of(2026, 11, 16));
        festivos.add(LocalDate.of(2026, 12, 8));
        festivos.add(LocalDate.of(2026, 12, 25));
    }

    public boolean esFestivo(LocalDate fecha) {
        return festivos.contains(fecha);
    }
}