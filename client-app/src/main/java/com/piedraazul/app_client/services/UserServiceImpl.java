package com.piedraazul.app_client.services;

import com.piedraazul.app_client.dto.LoginRequestDTO;
import com.piedraazul.app_client.dto.LoginResponseDTO;
import com.piedraazul.app_client.enums.RoleUserEnum;
import com.piedraazul.app_client.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.piedraazul.app_client.dto.RegisterDTO;
import com.piedraazul.app_client.mappers.UserMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserServiceImpl implements UserService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8080/piedraAzul/auth";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public RoleUserEnum authUser(Long cedUser, String password) {
        try {
            LoginRequestDTO requestDTO = new LoginRequestDTO((long) cedUser, password);
            String jsonBody = objectMapper.writeValueAsString(requestDTO);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Respuesta del servidor: " + response.body());

            if (response.statusCode() == 200) {
                LoginResponseDTO loginRes = objectMapper.readValue(response.body(), LoginResponseDTO.class);
                SessionManager.setSession(loginRes);
                return RoleUserEnum.valueOf(loginRes.getRole());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User regUser(User newUser) {
        try {
            RegisterDTO dto = UserMapper.toRegisterDTO(newUser);
            String jsonBody = objectMapper.writeValueAsString(dto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">> Status regUser: " + response.statusCode());
            System.out.println(">> Body regUser: " + response.body());

            if (response.statusCode() == 201 || response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean recoverPassword(int cedUser, String answer) {
        try {
            String jsonBody = "{\"cedUser\":" + cedUser + ",\"securityAnswer\":\"" + answer + "\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/recover"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Si el microservicio devuelve 200, la respuesta de seguridad es correcta
            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePassword(int cedUser, String newPassword) {
        try {
            String jsonBody = "{\"cedUser\":" + cedUser + ",\"newPassword\":\"" + newPassword + "\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/update-password"))
                    .header("Content-Type", "application/json")
                    // Importante: Esta acción suele requerir estar logueado
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findByCedUser(Long cedUser) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user/" + cedUser))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                User user = objectMapper.readValue(response.body(), User.class);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}