package com.piedraazul.people_service.dto;

import com.piedraazul.people_service.enums.SpecialityProfEnum;
import com.piedraazul.people_service.enums.TypeProfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProfessionalDTO {
    private Long codUser;
    private String genProf;
    private String phoneProf;
    private TypeProfEnum typeProf;
    private SpecialityProfEnum specialityProf;
    private Integer attentionInterval;
}