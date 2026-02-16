
package models;

public class Doctor extends User {
    private double codDoc;
    private String genDoc;
    private double phoneMed;
    private String statusDoc;

    public Doctor(double codDoc, String genDoc, double phoneMed, String statusDoc, int codUser, int cedUser, String passUser, String nameUser, String secondNameUser, String lastNameUser, String secondLastNameUser, String statusUser, String typeUser) {
        super(codUser, cedUser, passUser, nameUser, secondNameUser, lastNameUser, secondLastNameUser, statusUser, typeUser);
        this.codDoc = codDoc;
        this.genDoc = genDoc;
        this.phoneMed = phoneMed;
        this.statusDoc = statusDoc;
    }

    public Doctor() {
    }

    

    public double getCodDoc() {
        return codDoc;
    }

    public void setCodDoc(double codDoc) {
        this.codDoc = codDoc;
    }

    public String getGenDoc() {
        return genDoc;
    }

    public void setGenDoc(String genDoc) {
        this.genDoc = genDoc;
    }

    public double getPhoneMed() {
        return phoneMed;
    }

    public void setPhoneMed(double phoneMed) {
        this.phoneMed = phoneMed;
    }

    public String getStatusDoc() {
        return statusDoc;
    }

    public void setStatusDoc(String statusDoc) {
        this.statusDoc = statusDoc;
    }
    
    

}