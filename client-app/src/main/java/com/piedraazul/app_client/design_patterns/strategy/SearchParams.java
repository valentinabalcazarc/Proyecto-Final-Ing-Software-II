package com.piedraazul.app_client.design_patterns.strategy;

import com.piedraazul.app_client.enums.SpecialityProfEnum;

import java.time.LocalDate;

/**
 * Objeto de parámetros para las estrategias de búsqueda.
 *
 * <p>Actúa como contenedor que agrupa todos los posibles criterios de
 * filtrado. Cada {@link IAppointmentSearchStrategy} concreta lee solo
 * los campos que necesita e ignora el resto.</p>
 *
 * <p>Se construye mediante su {@link Builder} interno:</p>
 * <pre>
 *   SearchParams params = new SearchParams.Builder()
 *       .professionalId(prof.getCodProf())
 *       .date(dpDate.getValue())
 *       .build();
 * </pre>
 */
public class SearchParams {

    private final Long professionalId;
    private final LocalDate date;
    private final Long patientId;
    private final SpecialityProfEnum speciality;

    private SearchParams(Builder builder) {
        this.professionalId = builder.professionalId;
        this.date           = builder.date;
        this.patientId      = builder.patientId;
        this.speciality     = builder.speciality;
    }

    public Long getProfessionalId(){
        return professionalId;
    }

    public LocalDate getDate(){
        return date; }

    public Long getPatientId(){
        return patientId;
    }

    public SpecialityProfEnum getSpeciality(){
        return speciality;
    }

    // ── Builder interno ──────────────────────────────────────────────────────

    public static class Builder {

        private Long professionalId;
        private LocalDate date;
        private Long patientId;
        private SpecialityProfEnum speciality;

        public Builder professionalId(Long professionalId) {
            this.professionalId = professionalId;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder patientId(Long patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder speciality(SpecialityProfEnum speciality) {
            this.speciality = speciality;
            return this;
        }

        public SearchParams build() {
            return new SearchParams(this);
        }
    }
}