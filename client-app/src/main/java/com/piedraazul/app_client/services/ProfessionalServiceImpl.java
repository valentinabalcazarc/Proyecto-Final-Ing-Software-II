package com.piedraazul.app_client.services;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.enums.StatusUserEnum;
import com.piedraazul.app_client.enums.TypeProfEnum;
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
            //System.out.println(">> Status getAllProfessionals: " + response.statusCode());
            //System.out.println(">> Body getAllProfessionals: " + response.body());

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
        try {
            if (obj.has("codProf") && !obj.isNull("codProf"))
                prof.setCodProf(obj.getLong("codProf"));

            // Nombre y apellido vienen dentro de userRef
            if (obj.has("userRef") && !obj.isNull("userRef")) {
                JSONObject userRef = obj.getJSONObject("userRef");
                if (userRef.has("codUser")) prof.setCodUser(userRef.getLong("codUser"));
                if (userRef.has("nameUser")) prof.setNameUser(userRef.getString("nameUser"));
                if (userRef.has("lastNameUser")) prof.setLastNameUser(userRef.getString("lastNameUser"));
                if (userRef.has("cedUser")) prof.setCedUser(userRef.getLong("cedUser"));
            }

            if (obj.has("genProf") && !obj.isNull("genProf"))
                prof.setGenProf(obj.getString("genProf"));
            if (obj.has("phoneProf") && !obj.isNull("phoneProf"))
                prof.setPhoneProf(obj.getLong("phoneProf"));
            if (obj.has("typeProf") && !obj.isNull("typeProf"))
                prof.setTypeProf(TypeProfEnum.valueOf(obj.getString("typeProf")));
            if (obj.has("specialityProf") && !obj.isNull("specialityProf"))
                prof.setSpecialityProf(SpecialityProfEnum.valueOf(obj.getString("specialityProf")));
            if (obj.has("attentionInterval") && !obj.isNull("attentionInterval"))
                prof.setAttentionInterval(obj.getInt("attentionInterval"));
            if (obj.has("statusProf") && !obj.isNull("statusProf"))
                prof.setStatusProf(StatusUserEnum.valueOf(obj.getString("statusProf")));

        } catch (Exception e) {
            System.err.println(">> Error mapeando profesional: " + e.getMessage());
            e.printStackTrace();
        }
        return prof;
    }
}