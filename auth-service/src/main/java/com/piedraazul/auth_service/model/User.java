package com.piedraazul.auth_service.model;

import com.piedraazul.auth_service.enums.RoleUserEnum;
import com.piedraazul.auth_service.enums.StatusUserEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODUSER")
    private Long codUser;

    @Column(name = "CEDUSER", unique = true, nullable = false)
    private Long cedUser;

    @Column(name = "PASSUSER", nullable = false)
    private String passUser;

    @Column(name = "NAMEUSER", nullable = false)
    private String nameUser;

    @Column(name = "SECOND_NAMEUSER")
    private String secondNameUser;

    @Column(name = "LASTNAMEUSER", nullable = false)
    private String lastNameUser;

    @Column(name = "SECOND_LASTNAMEUSER")
    private String secondLastNameUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUSUSER", nullable = false)
    private StatusUserEnum statusUser = StatusUserEnum.Active;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLEUSER", nullable = false)
    private RoleUserEnum roleUser;

    @Column(name = "QUESTUSER", nullable = false)
    private String securityQuestion;

    @Column(name = "ANSWERUSER", nullable = false)
    private String securityAnswer;
}