package com.piedraazul.app_client.services;

import com.piedraazul.app_client.models.Patient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String API_URL = "http://localhost:8082/piedraAzul/patients";

    @Override
    public boolean regPatient(Patient newPatient) {
        try {
            // Empaquetamos los datos en el JSON que el PatientDTO del backend espera
            JSONObject json = new JSONObject();
            json.put("idPatient", newPatient.getIdPatient());
            json.put("namePatient", newPatient.getNamePatient());
            json.put("lastNamePatient", newPatient.getLastNamePatient());
            json.put("genderPatient", newPatient.getGenderPatient());
            // El backend también permite opcionales como el teléfono o segundo nombre
            json.put("phonePatient", newPatient.getPhonePatient());

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
        p.setIdPatient(obj.getLong("idPatient"));
        p.setNamePatient(obj.getString("namePatient"));
        p.setLastNamePatient(obj.getString("lastNamePatient"));
        p.setGenderPatient(obj.getString("genderPatient"));
        return p;
    }
}