package com.piedraazul.app_client.services;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.enums.StatusUserEnum;
import com.piedraazul.app_client.enums.TypeProfEnum;
import com.piedraazul.app_client.models.Professional;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.piedraazul.app_client.dto.ProfessionalResponseDTO;
import com.piedraazul.app_client.mappers.ProfessionalMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ProfessionalServiceImpl implements ProfessionalService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8082/piedraAzul/professionals";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public List<Professional> getAllProfessionals() {
        List<Professional> professionals = new ArrayList<>();
        try {
            String token = SessionManager.getToken();
            System.out.println(">> Token siendo usado: " + token);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">> Status getAllProfessionals: " + response.statusCode());
            System.out.println(">> Body getAllProfessionals: " + response.body());

            if (response.statusCode() == 200) {
                List<ProfessionalResponseDTO> dtos = objectMapper.readValue(response.body(),
                        new TypeReference<List<ProfessionalResponseDTO>>() {
                        });
                for (ProfessionalResponseDTO dto : dtos) {
                    professionals.add(ProfessionalMapper.toModel(dto));
                }
                System.out.println(">> Mapeados " + professionals.size() + " profesionales.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professionals;
    }

    @Override
    public Professional findByCod(int codProf) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user/" + codProf))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">> Status findByCod: " + response.statusCode());
            System.out.println(">> Body findByCod: " + response.body());

            if (response.statusCode() == 200) {
                ProfessionalResponseDTO dto = objectMapper.readValue(response.body(), ProfessionalResponseDTO.class);
                return ProfessionalMapper.toModel(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Professional> getAllProfessionalsBySpeciality(SpecialityProfEnum speciality) {
        List<Professional> professionals = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/speciality/" + speciality.name()))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">> Status getAllProfessionalsBySpeciality: " + response.statusCode());
            System.out.println(">> Body getAllProfessionalsBySpeciality: " + response.body());

            if (response.statusCode() == 200) {
                List<ProfessionalResponseDTO> dtos = objectMapper.readValue(response.body(),
                        new TypeReference<List<ProfessionalResponseDTO>>() {
                        });
                for (ProfessionalResponseDTO dto : dtos) {
                    professionals.add(ProfessionalMapper.toModel(dto));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professionals;
    }

}