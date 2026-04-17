package com.piedraazul.app_client.models;

import java.time.LocalDate;
import java.time.LocalTime;
//import DesignPatterns.state.AppointmentState;

public class Appointment {

    private int id;
    private int patientId;
    private int professionalId;
    private LocalDate date;
    private LocalTime time;
    private String description;
    //private AppointmentState state;

    /*public Appointment() {
        this.state = AppointmentState.InitialState(this);
    }*/

    public Appointment() { }

    public Appointment(int id, int patientId, int professionalId, LocalDate date, LocalTime time, String description/*, AppointmentState state*/) {
        this.id = id;
        this.patientId = patientId;
        this.professionalId = professionalId;
        this.date = date;
        this.time = time;
        this.description = description;
        //this.state = state;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getProfessionalId() {
        return professionalId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription(){
        return description;
    }
    
    /*public AppointmentState getState() {
        return state;
    }*/

    public void setId(int id) {
        this.id = id;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setProfessionalId(int professionalId) {
        this.professionalId = professionalId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    /*public void setState(AppointmentState state) {
        this.state = state;
    }*/
}