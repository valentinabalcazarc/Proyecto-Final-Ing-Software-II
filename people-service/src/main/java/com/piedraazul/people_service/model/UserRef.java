package com.piedraazul.people_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_REF")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRef {

    @Id
    @Column(name = "CODUSER")
    private Long codUser;

    @Column(name = "CEDUSER")
    private Long cedUser;

    @Column(name = "NAMEUSER")
    private String nameUser;

    @Column(name = "LASTNAMEUSER")
    private String lastNameUser;

    @Column(name = "ROLEUSER")
    private String roleUser;
}
