package com.piedraazul.app_client.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {
    private Long codProf;
    private Long codPatient;
    private LocalDate dateApp;
    private LocalTime timeApp;
    private String descApp;
}
