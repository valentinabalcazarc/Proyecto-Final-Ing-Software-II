package com.piedraazul.app_client.models;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Appointment {

    private Long id;
    private Long patientId;
    private Long professionalId;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private String description;

    // CAMPOS AUXILIARES PARA LA VISTA
    private String professionalName;
    private String specialityName;
    private String typeProfName;
    private String patientName;
}