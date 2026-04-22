package com.piedraazul.app_client.services;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Professional;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ProfessionalServiceImpl implements ProfessionalService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8082/piedraAzul/professionals";

    @Override
    public List<Professional> getAllProfessionals() {
        List<Professional> professionals = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONArray jsonArray = new JSONArray(response.body());
                for (int i = 0; i < jsonArray.length(); i++) {
                    professionals.add(mapJsonToProfessional(jsonArray.getJSONObject(i)));
                }
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

            if (response.statusCode() == 200) {
                return mapJsonToProfessional(new JSONObject(response.body()));
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

            if (response.statusCode() == 200) {
                JSONArray jsonArray = new JSONArray(response.body());
                for (int i = 0; i < jsonArray.length(); i++) {
                    professionals.add(mapJsonToProfessional(jsonArray.getJSONObject(i)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professionals;
    }

    private Professional mapJsonToProfessional(JSONObject obj) {
        Professional prof = new Professional();
        // El microservicio devuelve codUser dentro del objeto Professional o UserRef
        // Ajustamos al nombre de campo que devuelve tu backend
        if (obj.has("codUser")) {
            prof.setCodUser(obj.getLong("codUser"));
        } else if (obj.has("userRef")) {
            prof.setCodUser(obj.getJSONObject("userRef").getLong("codUser"));
        }

        prof.setGenProf(obj.getString("genProf"));
        prof.setSpecialityProf(SpecialityProfEnum.valueOf(obj.getString("specialityProf")));
        // Agrega los demás campos necesarios para tu vista
        return prof;
    }
}