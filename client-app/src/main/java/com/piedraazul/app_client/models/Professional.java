package com.piedraazul.app_client.models;

import com.piedraazul.app_client.enums.StatusUserEnum;
import com.piedraazul.app_client.enums.TypeProfEnum;
import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.enums.RoleUserEnum;
import lombok.Data;
import java.time.LocalTime;

@Data
public class Professional extends User {
    private Long codProf;
    private String genProf;
    private Long phoneProf;
    private StatusUserEnum statusProf;
    private TypeProfEnum typeProf;
    private SpecialityProfEnum specialityProf;
    private Integer attentionInterval;
    private LocalTime arrivalTime;
    private LocalTime departureTime;

    public String getFullName() {
        return getNameUser() + " " + getLastNameUser();
    }

    @Override
    public String toString() {
        return getNameUser() + " " + getLastNameUser();
    }
}