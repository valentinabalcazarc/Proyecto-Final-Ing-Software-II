package models;



public class Patient {
    private int codPatient;
    private int idPatient;
    private String namePatient;
    private String secondNamePatient;
    private String lastNamePatient;
    private String secondLastNamePatient;
    private int phonePatient;
    private java.time.LocalDate dateBirthPatient;
    private String genderPatient;

    public Patient(int codPatient, int idPatient, String namePatient, String secondNamePatient, String lastNamePatient, String secondLastNamePatient, int phonePatient, java.time.LocalDate dateBirthPatient, String genderPatient) {
        this.codPatient = codPatient;
        this.idPatient = idPatient;
        this.namePatient = namePatient;
        this.secondNamePatient = secondNamePatient;
        this.lastNamePatient = lastNamePatient;
        this.secondLastNamePatient = secondLastNamePatient;
        this.phonePatient = phonePatient;
        this.dateBirthPatient = dateBirthPatient;
        this.genderPatient = genderPatient;
    }
    
    public Patient() { }
    
    public int getCodPatient() {
        return codPatient;
    }
    
    public void setCodPatient(int codPatient) {
        this.codPatient = codPatient;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
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

    public int getPhonePatient() {
        return phonePatient;
    }

    public void setPhonePatient(int phonePatient) {
        this.phonePatient = phonePatient;
    }

    public java.time.LocalDate getDateBirthPatient() {
        return dateBirthPatient;
    }

    public void setDateBirthPatient(java.time.LocalDate dateBirthPatient) {
        this.dateBirthPatient = dateBirthPatient;
    }

    public String getGenderPatient() {
        return genderPatient;
    }

    public void setGenderPatient(String genderPatient) {
        this.genderPatient = genderPatient;
    } 
}
