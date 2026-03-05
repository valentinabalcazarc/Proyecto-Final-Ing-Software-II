
package models;

import java.util.Date;


public class Patient {
    private double idPatient;
    private String namePatient;
    private String secondNamePatient;
    private String lastNamePatient;
    private String secondLastNamePatient;
    private double phonePatient;
    private Date dateBirthPatient;
    private String genderPatient;

    public Patient(double idPatient, String namePatient, String secondNamePatient, String lastNamePatient, String secondLastNamePatient, double phonePatient, Date dateBirthPatient, String genderPatient) {
        this.idPatient = idPatient;
        this.namePatient = namePatient;
        this.secondNamePatient = secondNamePatient;
        this.lastNamePatient = lastNamePatient;
        this.secondLastNamePatient = secondLastNamePatient;
        this.phonePatient = phonePatient;
        this.dateBirthPatient = dateBirthPatient;
        this.genderPatient = genderPatient;
    }

    public double getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(double idPatient) {
        this.idPatient = idPatient;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }

    public String getSecondNamePatient() {
        return secondNamePatient;
    }

    public void setSecondNamePatient(String secondNamePatient) {
        this.secondNamePatient = secondNamePatient;
    }

    public String getLastNamePatient() {
        return lastNamePatient;
    }

    public void setLastNamePatient(String lastNamePatient) {
        this.lastNamePatient = lastNamePatient;
    }

    public String getSecondLastNamePatient() {
        return secondLastNamePatient;
    }

    public void setSecondLastNamePatient(String secondLastNamePatient) {
        this.secondLastNamePatient = secondLastNamePatient;
    }

    public double getPhonePatient() {
        return phonePatient;
    }

    public void setPhonePatient(double phonePatient) {
        this.phonePatient = phonePatient;
    }

    public Date getDateBirthPatient() {
        return dateBirthPatient;
    }

    public void setDateBirthPatient(Date dateBirthPatient) {
        this.dateBirthPatient = dateBirthPatient;
    }

    public String getGenderPatient() {
        return genderPatient;
    }

    public void setGenderPatient(String genderPatient) {
        this.genderPatient = genderPatient;
    } 
}
