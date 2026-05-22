package com.piedraazul.appointment_service.domain.model;

import com.piedraazul.appointment_service.enums.StatusAppointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private Long codApp;
    private ProfessionalRef professionalRef;
    private PatientRef patientRef;
    private LocalDate dateApp;
    private LocalTime timeApp;
    private String descApp;
    private StatusAppointment statusApp;

    public Appointment(ProfessionalRef professionalRef, PatientRef patientRef, LocalDate dateApp, LocalTime timeApp,
            String descApp) {
        if (professionalRef == null)
            throw new IllegalArgumentException("Professional is required");
        if (patientRef == null)
            throw new IllegalArgumentException("Patient is required");
        if (dateApp == null)
            throw new IllegalArgumentException("Date is required");
        if (timeApp == null)
            throw new IllegalArgumentException("Time is required");

        this.professionalRef = professionalRef;
        this.patientRef = patientRef;
        this.dateApp = dateApp;
        this.timeApp = timeApp;
        this.descApp = descApp;
        this.statusApp = StatusAppointment.Scheduled;
    }

    public void reschedule(LocalDate newDate, LocalTime newTime) {
        if (this.statusApp == StatusAppointment.Cancelled) {
            throw new IllegalStateException("Cannot reschedule a cancelled appointment");
        }
        if (this.statusApp == StatusAppointment.Completed) {
            throw new IllegalStateException("Cannot reschedule a completed appointment");
        }
        if (newDate == null)
            throw new IllegalArgumentException("New date is required");
        if (newTime == null)
            throw new IllegalArgumentException("New time is required");

        this.dateApp = newDate;
        this.timeApp = newTime;
    }

    public void updateDescription(String newDescription) {
        this.descApp = newDescription;
    }

    public void cancel() {
        if (this.statusApp == StatusAppointment.Completed) {
            throw new IllegalStateException("Cannot cancel a completed appointment");
        }
        this.statusApp = StatusAppointment.Cancelled;
    }

    public void complete() {
        if (this.statusApp == StatusAppointment.Cancelled) {
            throw new IllegalStateException("Cannot complete a cancelled appointment");
        }
        this.statusApp = StatusAppointment.Completed;
    }
}
