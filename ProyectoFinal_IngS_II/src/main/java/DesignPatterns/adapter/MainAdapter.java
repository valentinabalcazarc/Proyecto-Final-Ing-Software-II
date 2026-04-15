package DesignPatterns.adapter;

import models.Patient;

public class MainAdapter {

    public static void main(String[] args) {

        System.out.println("=== PATRÓN ADAPTER ===\n");

        ExternalService servicioExterno = new ExternalService();

        PatientDataProvider adapter = new ExternalPatientAdapter(servicioExterno);

        Patient patient = adapter.getPatient();

        System.out.println("Paciente obtenido:\n");

        System.out.println("Cod: " + patient.getCodPatient());
        System.out.println("ID: " + patient.getIdPatient());
        System.out.println("Nombre: " + patient.getNamePatient() + " " + patient.getSecondNamePatient());
        System.out.println("Apellidos: " + patient.getLastNamePatient() + " " + patient.getSecondLastNamePatient());
        System.out.println("Telefono: " + patient.getPhonePatient());
        System.out.println("Fecha Nacimiento: " + patient.getDateBirthPatient());
        System.out.println("Genero: " + patient.getGenderPatient());
    }
}