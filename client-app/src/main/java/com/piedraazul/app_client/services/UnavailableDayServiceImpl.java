package com.piedraazul.app_client.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.piedraazul.app_client.dto.UnavailableDayDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UnavailableDayServiceImpl implements UnavailableDayService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8080/piedraAzul/unavailable-days";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public boolean create(UnavailableDayDTO dto) {
        try {
            String jsonBody = objectMapper.writeValueAsString(dto);
            System.out.println(">> Registrando día no laborable: " + jsonBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">> Status create UnavailableDay: " + response.statusCode());
            System.out.println(">> Body create UnavailableDay: " + response.body());

            return response.statusCode() == 201 || response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}