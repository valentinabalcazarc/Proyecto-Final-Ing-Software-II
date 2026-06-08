package com.piedraazul.app_client.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class UpdateProfessionalDTO {
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private Integer attentionInterval;
    private String unavailableDays;
}