package com.piedraazul.app_client.dto;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.enums.StatusUserEnum;
import com.piedraazul.app_client.enums.TypeProfEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfessionalResponseDTO {
    private Long codProf;
    private UserRefDTO userRef;
    private String genProf;
    private Long phoneProf;
    private TypeProfEnum typeProf;
    private SpecialityProfEnum specialityProf;
    private Integer attentionInterval;
    private StatusUserEnum statusProf;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private String unavailableDays;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserRefDTO {
        private Long codUser;
        private Long cedUser;
        private String nameUser;
        private String lastNameUser;
    }
}