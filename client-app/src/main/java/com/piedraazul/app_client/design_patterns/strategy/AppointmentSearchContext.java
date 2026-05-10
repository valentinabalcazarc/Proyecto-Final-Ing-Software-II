package com.piedraazul.app_client.design_patterns.strategy;

import com.piedraazul.app_client.models.Appointment;

import java.util.List;

public class AppointmentSearchContext {

    private IAppointmentSearchStrategy strategy;

    // ── Constructor ──────────────────────────────────────────────────────────

    public AppointmentSearchContext() {}

    public AppointmentSearchContext(IAppointmentSearchStrategy strategy) {
        this.strategy = strategy;
    }

    // ── API pública ──────────────────────────────────────────────────────────

    public void setStrategy(IAppointmentSearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Appointment> executeSearch(SearchParams params) {
        if (strategy == null) {
            throw new IllegalStateException(
                    "[AppointmentSearchContext] No hay ninguna estrategia establecida. " +
                            "Llama a setStrategy() antes de executeSearch().");
        }
        return strategy.search(params);
    }
}