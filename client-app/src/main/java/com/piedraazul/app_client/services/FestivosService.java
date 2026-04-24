package com.piedraazul.app_client.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class FestivosService {
    private final Set<LocalDate> festivos;

    public FestivosService() {
        festivos = new HashSet<>();
        // Tus fechas originales
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

    public boolean esDiaInvalido(LocalDate fecha) {
        return esFestivo(fecha)
                || fecha.getDayOfWeek() == DayOfWeek.SUNDAY
                || fecha.getDayOfWeek() == DayOfWeek.SATURDAY;
    }
}