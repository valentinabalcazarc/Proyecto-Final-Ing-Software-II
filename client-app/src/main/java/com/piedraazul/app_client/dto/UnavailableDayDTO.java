package com.piedraazul.app_client.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UnavailableDayDTO {
    private Long id;
    private Long codProf;
    private LocalDate date;
    private String reason;
}