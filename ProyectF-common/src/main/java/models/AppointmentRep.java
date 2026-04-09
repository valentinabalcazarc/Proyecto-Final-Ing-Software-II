package models;

import java.time.LocalDate;

public class AppointmentRep {

    private int codApp;
    private LocalDate date;
    private String namePat;
    private String idPat;
    private String nameProff;

    public AppointmentRep(int codApp, LocalDate date, String namePat, String idPat, String nameProff) {
        this.codApp = codApp;
        this.date = date;
        this.namePat = namePat;
        this.idPat = idPat;
        this.nameProff = nameProff;
    }

    public AppointmentRep() {
    }

    public int getCodApp() {
        return codApp;
    }

    public void setCodApp(int codApp) {
        this.codApp = codApp;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNamePat() {
        return namePat;
    }

    public void setNamePat(String namePat) {
        this.namePat = namePat;
    }

    public String getIdPat() {
        return idPat;
    }

    public void setIdPat(String idPat) {
        this.idPat = idPat;
    }

    public String getNameProff() {
        return nameProff;
    }

    public void setNameProff(String nameProff) {
        this.nameProff = nameProff;
    }
}