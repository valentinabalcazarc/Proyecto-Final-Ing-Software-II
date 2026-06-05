package com.piedraazul.app_client.services;

import com.piedraazul.app_client.dto.UpdateProfessionalDTO;
import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.enums.StatusUserEnum;
import com.piedraazul.app_client.enums.TypeProfEnum;
import com.piedraazul.app_client.models.Professional;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.piedraazul.app_client.dto.ProfessionalResponseDTO;
import com.piedraazul.app_client.mappers.ProfessionalMapper;

import com.piedraazul.app_client.dto.ProfessionalDTO;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ProfessionalServiceImpl implements ProfessionalService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8080/piedraAzul/professionals";
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
    public Professional findByCod(Long codProf) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user/" + codProf))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">> Status findByCod prof: " + response.statusCode());
            System.out.println(">> Body findByCod prof: " + response.body());

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
    public boolean register(Professional professional) {
        int maxIntentos = 5;
        long esperaMs = 600;

        for (int intento = 1; intento <= maxIntentos; intento++) {
            try {
                ProfessionalDTO dto = new ProfessionalDTO();
                dto.setCodUser(professional.getCodUser());
                dto.setGenProf(professional.getGenProf());
                dto.setPhoneProf(professional.getPhoneProf() != null ? professional.getPhoneProf().toString() : null);
                dto.setTypeProf(professional.getTypeProf());
                dto.setSpecialityProf(professional.getSpecialityProf());
                dto.setArrivalTime(professional.getArrivalTime());
                dto.setDepartureTime(professional.getDepartureTime());
                dto.setAttentionInterval(professional.getAttentionInterval());

                String jsonBody = objectMapper.writeValueAsString(dto);
                System.out.println(">> Intento " + intento + " - Registrando profesional: " + jsonBody);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + SessionManager.getToken())
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(">> Status register profesional: " + response.statusCode());
                System.out.println(">> Body register profesional: " + response.body());

                if (response.statusCode() == 201 || response.statusCode() == 200) {
                    return true;
                }

                if (response.statusCode() == 409 && response.body().contains("No existe un usuario")) {
                    System.out.println(">> UserRef aún no propagado, esperando " + esperaMs + "ms...");
                    Thread.sleep(esperaMs);
                    esperaMs *= 2;
                } else {
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        System.err.println(">> No se pudo registrar el profesional tras " + maxIntentos + " intentos.");
        return false;
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

    @Override
    public boolean updateSchedule(Long codProf, UpdateProfessionalDTO dto) {
        try {
            String jsonBody = objectMapper.writeValueAsString(dto);
            System.out.println(">> Actualizando horario profesional codProf=" + codProf + ": " + jsonBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + codProf))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">> Status updateSchedule: " + response.statusCode());
            System.out.println(">> Body updateSchedule: " + response.body());

            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}