package DesignPatterns.adapter;


import DesignPatterns.adapter.ExternalService;
import DesignPatterns.adapter.PatientDataProvider;
import models.Patient;
import org.json.JSONObject;

import java.time.LocalDate;

public class ExternalPatientAdapter implements PatientDataProvider {

    private final ExternalService externalService;

    public ExternalPatientAdapter(ExternalService externalService) {
        this.externalService = externalService;
    }

    @Override
    public Patient getPatient() {

        try {
            String jsonData = externalService.getPatientData();
            JSONObject json = new JSONObject(jsonData);

            Patient patient = new Patient();

            patient.setCodPatient(json.optInt("codPatient", 0));
            patient.setIdPatient(json.optInt("idPatient", 0));
            patient.setNamePatient(json.optString("namePatient", ""));
            patient.setSecondNamePatient(json.optString("secondNamePatient", ""));
            patient.setLastNamePatient(json.optString("lastNamePatient", ""));
            patient.setSecondLastNamePatient(json.optString("secondLastNamePatient", ""));
            patient.setPhonePatient(json.optInt("phonePatient", 0));

            String dateStr = json.optString("dateBirthPatient", null);

            if (dateStr != null && !dateStr.isEmpty()) {
                patient.setDateBirthPatient(LocalDate.parse(dateStr));
            } else {
                patient.setDateBirthPatient(null); // o manejarlo diferente
            }
            patient.setGenderPatient(json.optString("genderPatient", ""));

            return patient;

        } catch (Exception e) {
            Patient errorPatient = new Patient();
            errorPatient.setNamePatient("Error");
            errorPatient.setIdPatient(-1);
            return errorPatient;
        }
    }
}