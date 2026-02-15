
package co.unicauca.domain;

import java.util.Date;


public class Patient {
    private double codPat;
    private double phonePat;
    private String statusPat;
    private Date dateBirthPat;
    private String genPat;

    public Patient(double codPat, double phonePat, String statusPat, Date dateBirthPat, String genPat) {
        this.codPat = codPat;
        this.phonePat = phonePat;
        this.statusPat = statusPat;
        this.dateBirthPat = dateBirthPat;
        this.genPat = genPat;
    }

    public double getCodPat() {
        return codPat;
    }

    public void setCodPat(double codPat) {
        this.codPat = codPat;
    }

    public double getPhonePat() {
        return phonePat;
    }

    public void setPhonePat(double phonePat) {
        this.phonePat = phonePat;
    }

    public String getStatusPat() {
        return statusPat;
    }

    public void setStatusPat(String statusPat) {
        this.statusPat = statusPat;
    }

    public Date getDateBirthPat() {
        return dateBirthPat;
    }

    public void setDateBirthPat(Date dateBirthPat) {
        this.dateBirthPat = dateBirthPat;
    }

    public String getGenPat() {
        return genPat;
    }

    public void setGenPat(String genPat) {
        this.genPat = genPat;
    }
    
    
}
