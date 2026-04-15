package DesignPatterns.adapter;

import models.Patient;

public interface PatientDataProvider {
    Patient getPatient();
}