package com.piedraazul.app_client.services;

import com.piedraazul.app_client.models.Patient;
import com.piedraazul.app_client.models.Professional;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.piedraazul.app_client.dto.PatientDTO;
import com.piedraazul.app_client.mappers.PatientMapper;
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
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public boolean regPatient(Patient newPatient) {
        try {
            PatientDTO dto = PatientMapper.toDTO(newPatient);
            String jsonBody = objectMapper.writeValueAsString(dto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
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
                List<PatientDTO> dtos = objectMapper.readValue(response.body(), new TypeReference<List<PatientDTO>>() {});
                for (PatientDTO dto : dtos) {
                    patients.add(PatientMapper.toModel(dto));
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
                PatientDTO dto = objectMapper.readValue(response.body(), PatientDTO.class);
                return PatientMapper.toModel(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}