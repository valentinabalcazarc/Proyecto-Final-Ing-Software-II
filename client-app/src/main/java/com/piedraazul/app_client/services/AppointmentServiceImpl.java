package com.piedraazul.app_client.services;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.AppointmentRep;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8083/piedraAzul/appointments";

    @Override
    public boolean registerAppointment(Appointment appointment) {
        if (appointment == null) return false;
        try {
            JSONObject json = new JSONObject();
            json.put("dateApp", appointment.getDate().toString());
            json.put("timeApp", appointment.getTime().toString());
            json.put("codProf", appointment.getProfessionalId());
            json.put("idPatient", appointment.getPatientId());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 201;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Object[]> getAppointmentsForTable() {
        return fetchObjectList(BASE_URL + "/table");
    }

    @Override
    public List<Object[]> searchAppointments(Integer codProf, LocalDate fecha) {
        String url = String.format("%s/search?codProf=%d&fecha=%s", BASE_URL, codProf, fecha);
        return fetchObjectList(url);
    }

    @Override
    public List<Object[]> getGeneretedAppointments() {
        return fetchObjectList(BASE_URL + "/generated");
    }

    @Override
    public Appointment getFirstAvailableBySpeciality(SpecialityProfEnum speciality) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/first-available?speciality=" + speciality.name()))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONObject obj = new JSONObject(response.body());
                Appointment app = new Appointment();
                app.setId(obj.getInt("idAppointment"));
                // ... mapear fecha y hora
                return app;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para manejar los reportes (AppointmentRep)
    @Override
    public List<AppointmentRep> getAppointmentForReport() {
        List<AppointmentRep> reports = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/report"))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONArray array = new JSONArray(response.body());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    // Aquí mapeas según los atributos de tu clase AppointmentRep
                    reports.add(new AppointmentRep(
                            obj.getString("patientName"),
                            obj.getString("doctorName"),
                            obj.getString("date")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }

    /**
     * Helper para transformar las respuestas JSON en Object[]
     * para que tus TableView sigan funcionando igual que antes.
     */
    private List<Object[]> fetchObjectList(String url) {
        List<Object[]> data = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONArray array = new JSONArray(response.body());
                for (int i = 0; i < array.length(); i++) {
                    JSONArray rowJson = array.getJSONArray(i);
                    Object[] row = new Object[rowJson.length()];
                    for (int j = 0; j < rowJson.length(); j++) {
                        row[j] = rowJson.get(j);
                    }
                    data.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // Implementarías los filtros restantes siguiendo el patrón de URL con parámetros:
    @Override public List<Appointment> getAllAppointments() { return new ArrayList<>(); }
    @Override public List<Object[]> getGeneretedAppointmentsFiltered(Integer codProf, LocalDate fecha) { return fetchObjectList(BASE_URL + "/generated-filtered?cod=" + codProf + "&date=" + fecha); }
    @Override public List<Object[]> getGeneretedAppointmentsBySpeciality(SpecialityProfEnum speciality) { return fetchObjectList(BASE_URL + "/generated-speciality?spec=" + speciality.name()); }
    @Override public List<Object[]> getGeneretedAppointmentsBySpecialityFiltered(Integer codProf, LocalDate fecha, SpecialityProfEnum speciality) { return new ArrayList<>(); }
}