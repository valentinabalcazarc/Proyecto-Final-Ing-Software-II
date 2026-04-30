package com.piedraazul.app_client.services;

import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.models.Professional;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String API_URL = "http://localhost:8082/piedraAzul/patients";

    @Override
    public boolean regPatient(Patient newPatient) {
        try {
            JSONObject json = new JSONObject();
            json.put("idPatient", newPatient.getIdPatient());
            json.put("namePatient", newPatient.getNamePatient());
            json.put("secondNamePatient", newPatient.getSecondNamePatient()); // Agregado
            json.put("lastNamePatient", newPatient.getLastNamePatient());
            json.put("secondLastNamePatient", newPatient.getSecondLastNamePatient()); // Agregado
            json.put("genderPatient", newPatient.getGenderPatient());
            json.put("phonePatient", newPatient.getPhonePatient());

            if (newPatient.getDateBirthPatient() != null) {
                json.put("dateBirthPatient", newPatient.getDateBirthPatient().toString());
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 201 || response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONArray jsonArray = new JSONArray(response.body());
                for (int i = 0; i < jsonArray.length(); i++) {
                    patients.add(mapJsonToPatient(jsonArray.getJSONObject(i)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public Patient findByCed(Long idPatient) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/" + idPatient)) // Endpoint: findByIdPatient
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapJsonToPatient(new JSONObject(response.body()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Patient mapJsonToPatient(JSONObject obj) {
        Patient p = new Patient();
        try {
            if (obj.has("codPatient") && !obj.isNull("codPatient"))
                p.setCodPatient(obj.getLong("codPatient"));
            if (obj.has("idPatient") && !obj.isNull("idPatient"))
                p.setIdPatient(obj.getLong("idPatient"));
            if (obj.has("namePatient") && !obj.isNull("namePatient"))
                p.setNamePatient(obj.getString("namePatient"));
            if (obj.has("secondNamePatient") && !obj.isNull("secondNamePatient"))
                p.setSecondNamePatient(obj.getString("secondNamePatient"));
            if (obj.has("lastNamePatient") && !obj.isNull("lastNamePatient"))
                p.setLastNamePatient(obj.getString("lastNamePatient"));
            if (obj.has("secondLastNamePatient") && !obj.isNull("secondLastNamePatient"))
                p.setSecondLastNamePatient(obj.getString("secondLastNamePatient"));
            if (obj.has("phonePatient") && !obj.isNull("phonePatient"))
                p.setPhonePatient(obj.getLong("phonePatient")); // Long es más seguro que Int
            if (obj.has("dateBirthPatient") && !obj.isNull("dateBirthPatient"))
                p.setDateBirthPatient(LocalDate.parse(obj.getString("dateBirthPatient"))); // JSONObject no tiene getLocalDate()
            if (obj.has("genderPatient") && !obj.isNull("genderPatient"))
                p.setGenderPatient(obj.getString("genderPatient"));

        } catch (Exception e) {
            System.err.println(">> Error mapeando paciente: " + e.getMessage());
            e.printStackTrace();
        }
        return p;
    }
}