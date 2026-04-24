
package com.piedraazul.app_client.models;

import com.piedraazul.app_client.enums.StatusUserEnum;
import com.piedraazul.app_client.enums.TypeProfEnum;
import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.enums.RoleUserEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Professional extends User {
    private Long codProf;
    private String genProf;
    private Long phoneProf;
    private StatusUserEnum statusProf;
    private TypeProfEnum typeProf;
    private SpecialityProfEnum SpecialityProf;
    private Integer attentionInterval;

    @Override
    public String toString() {
        return getNameUser() + " " + getLastNameUser();
    }

}