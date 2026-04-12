package com.piedraazul.appointment_service.dto;

import com.piedraazul.appointment_service.enums.StatusAppointment;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateAppointmentDTO {
    private LocalDate dateApp;
    private LocalTime timeApp;
    private String descApp;
    private StatusAppointment statusApp;
}