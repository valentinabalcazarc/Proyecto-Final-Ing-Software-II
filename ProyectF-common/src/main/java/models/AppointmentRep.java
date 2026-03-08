package models;

import java.time.LocalDate;

public class AppointmentRep {

    private int codApp;
    private LocalDate date;
    private String namePat;
    private int idPat;
    private String nameProff;

    public AppointmentRep(int codApp, LocalDate date, String namePat, int idPat, String nameProff) {
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

    public int getIdPat() {
        return idPat;
    }

    public void setIdPat(int idPat) {
        this.idPat = idPat;
    }

    public String getNameProff() {
        return nameProff;
    }

    public void setNameProff(String nameProff) {
        this.nameProff = nameProff;
    }
}