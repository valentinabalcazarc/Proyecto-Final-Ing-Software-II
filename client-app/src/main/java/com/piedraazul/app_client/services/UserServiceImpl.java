package com.piedraazul.app_client.services;

import com.piedraazul.app_client.dto.LoginRequestDTO;
import com.piedraazul.app_client.dto.LoginResponseDTO;
import com.piedraazul.app_client.enums.RoleUserEnum;
import com.piedraazul.app_client.models.User;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserServiceImpl implements UserService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8081/piedraAzul/auth";

    @Override
    public RoleUserEnum authUser(int cedUser, String password) {
        try {
            LoginRequestDTO requestDTO = new LoginRequestDTO((long) cedUser, password);
            String jsonBody = new JSONObject(requestDTO).toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Respuesta del servidor: " + response.body());

            if (response.statusCode() == 200) {
                JSONObject resJson = new JSONObject(response.body());
                LoginResponseDTO loginRes = new LoginResponseDTO();
                loginRes.setToken(resJson.getString("token"));
                loginRes.setRole(resJson.getString("role"));
                loginRes.setNameUser(resJson.getString("nameUser"));
                loginRes.setCodUser(resJson.getLong("codUser"));

                SessionManager.setSession(loginRes);
                return RoleUserEnum.valueOf(loginRes.getRole());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean regUser(User newUser) {
        try {
            // Usamos los nombres de campos exactos de tu RegisterUserDTO del backend
            JSONObject json = new JSONObject();
            json.put("cedUser", newUser.getCedUser());
            json.put("passUser", newUser.getPassUser());
            json.put("nameUser", newUser.getNameUser());
            json.put("lastNameUser", newUser.getLastNameUser());
            json.put("roleUser", newUser.getRoleUser().toString());
            json.put("securityQuestion", newUser.getSecurityQuestion());
            json.put("securityAnswer", newUser.getSecurityAnswer());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/register"))
                    .header("Content-Type", "application/json")
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
    public boolean recoverPassword(int cedUser, String answer) {
        try {
            JSONObject json = new JSONObject();
            json.put("cedUser", (long) cedUser);
            json.put("securityAnswer", answer);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/recover"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
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
            JSONObject json = new JSONObject();
            json.put("cedUser", (long) cedUser);
            json.put("newPassword", newPassword);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/update-password"))
                    .header("Content-Type", "application/json")
                    // Importante: Esta acción suele requerir estar logueado
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findByCedUser(int cedUser) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user/" + cedUser))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject obj = new JSONObject(response.body());
                User user = new User();
                user.setCedUser(obj.getLong("cedUser"));
                user.setNameUser(obj.getString("nameUser"));
                user.setLastNameUser(obj.getString("lastNameUser"));
                user.setRoleUser(RoleUserEnum.valueOf(obj.getString("roleUser")));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}