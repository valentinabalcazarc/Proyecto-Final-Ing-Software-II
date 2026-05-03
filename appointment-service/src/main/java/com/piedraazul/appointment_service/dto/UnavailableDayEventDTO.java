package com.piedraazul.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnavailableDayEventDTO {
    private Long id;
    private Long codProf;
    private LocalDate date;
    private String reason;
}