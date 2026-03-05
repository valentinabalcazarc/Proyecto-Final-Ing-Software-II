
package models;

import enums.StatusAppointment;
import java.util.Date;


public class Appointment {
    private double codApp;
    private Date dateApp;
    private String descApp;
    private StatusAppointment statusApp;  

    public Appointment(double codApp, Date dateApp, String descApp, StatusAppointment statusApp) {
        this.codApp = codApp;
        this.dateApp = dateApp;
        this.descApp = descApp;
        this.statusApp = statusApp;
    }

    public double getCodApp() {
        return codApp;
    }

    public void setCodApp(double codApp) {
        this.codApp = codApp;
    }

    public Date getDateApp() {
        return dateApp;
    }

    public void setDateApp(Date dateApp) {
        this.dateApp = dateApp;
    }

    public String getDescApp() {
        return descApp;
    }

    public void setDescApp(String descApp) {
        this.descApp = descApp;
    }

    public StatusAppointment getStatusApp() {
        return statusApp;
    }

    public void setStatusApp(StatusAppointment statusApp) {
        this.statusApp = statusApp;
    }   
}


