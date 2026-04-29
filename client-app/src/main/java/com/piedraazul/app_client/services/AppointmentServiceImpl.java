package com.piedraazul.app_client.services;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.enums.TypeProfEnum;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.AppointmentRep;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8083/piedraAzul/appointments";

    @Override
    public boolean registerAppointment(Appointment appointment) {
        if (appointment == null)
            return false;
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
    public List<Appointment> getAllAppointments() {
        return fetchAppointmentList(BASE_URL);
    }

    @Override
    public List<Object[]> getAppointmentsForTable() {
        return fetchObjectList(BASE_URL + "/table");
    }

    @Override
    public List<Object[]> searchAppointments(Long codProf, LocalDate fecha) {
        String url = String.format("%s/search?codProf=%d&fecha=%s", BASE_URL, codProf, fecha);
        return fetchObjectList(url);
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
                app.setId(obj.getLong("idAppointment"));
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
                            obj.getString("date")));
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

    @Override
    public List<Object[]> getGeneretedAppointmentsBySpeciality(SpecialityProfEnum speciality) {
        return fetchObjectList(BASE_URL + "/generated-speciality?spec=" + speciality.name());
    }

    @Override
    public List<Object[]> getGeneretedAppointmentsBySpecialityFiltered(Long codProf, LocalDate fecha,
            SpecialityProfEnum speciality) {
        return new ArrayList<>();
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(Long codPatient) {
        return fetchAppointmentList(BASE_URL + "/patient/" + codPatient);
    }

    @Override
    public List<Appointment> searchAppointmentsTyped(Long codProf, LocalDate fecha) {
        String url;
        if (codProf != null && fecha != null) {
            url = BASE_URL + "/professional/" + codProf + "/date/" + fecha;
        } else if (codProf != null) {
            url = BASE_URL + "/professional/" + codProf;
        } else if (fecha != null) {
            url = BASE_URL + "/date/" + fecha;
        } else {
            url = BASE_URL;
        }
        return fetchAppointmentList(url);
    }

    @Override
    public List<Appointment> getGeneratedAppointmentsTyped() {
        return fetchAppointmentList(BASE_URL + "/generated");
    }

    @Override
    public List<Appointment> getGeneratedAppointmentsFilteredTyped(Long codProf, LocalDate fecha) {
        LocalDate targetDate = (fecha != null) ? fecha : LocalDate.now();

        StringBuilder url = new StringBuilder(BASE_URL + "/generated?date=" + targetDate);
        if (codProf != null) url.append("&codProf=").append(codProf);

        return fetchAppointmentList(url.toString());
    }

    @Override
    public boolean saveAppointment(Appointment appointment, Long patientId) {
        try {
            JSONObject json = new JSONObject();
            json.put("codProf", appointment.getProfessionalId());
            json.put("codPatient", patientId);          // ← verifica que sea "codPatient"
            json.put("dateApp", appointment.getDate().toString());
            json.put("timeApp", appointment.getTime().toString());
            json.put("descApp", appointment.getDescription());

            System.out.println(">> JSON enviado: " + json.toString()); // 👈 log temporal

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(">> Status saveAppointment: " + response.statusCode());
            //System.out.println(">> Body saveAppointment: " + response.body());

            return response.statusCode() == 201 || response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAppointment(Long appointmentId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + appointmentId))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 204 || response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<Appointment> fetchAppointmentList(String url) {
        List<Appointment> data = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(">> Status fetchAppointmentList: " + response.statusCode());
            //System.out.println(">> Body fetchAppointmentList: " + response.body());

            if (response.statusCode() == 200) {
                JSONArray array = new JSONArray(response.body());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Appointment app = new Appointment();

                    // ID (puede no existir en slots generados)
                    if (obj.has("codApp") && !obj.isNull("codApp"))
                        app.setId(obj.getLong("codApp"));

                    // Fecha y hora
                    if (obj.has("dateApp") && !obj.isNull("dateApp"))
                        app.setDate(LocalDate.parse(obj.getString("dateApp")));
                    if (obj.has("timeApp") && !obj.isNull("timeApp"))
                        app.setTime(LocalTime.parse(obj.getString("timeApp")));

                    // PACIENTE
                    if (obj.has("patientRef") && !obj.isNull("patientRef")) {
                        JSONObject patJson = obj.getJSONObject("patientRef");
                        app.setPatientId(patJson.optLong("codPatient"));
                        app.setPatientName(
                                patJson.optString("namePatient", "") + " " + patJson.optString("lastNamePatient", "")
                        );
                    }

                    // PROFESIONAL - primero intenta campos planos (slots generados)
                    if (obj.has("professionalName") && !obj.isNull("professionalName")) {
                        app.setProfessionalName(obj.getString("professionalName"));
                        app.setProfessionalId(obj.optLong("codProf"));
                        app.setSpecialityName(obj.optString("specialityProf", ""));
                        app.setTypeProfName(obj.optString("typeProf", ""));
                    // luego intenta objeto anidado (citas reales)
                    } else if (obj.has("professionalRef") && !obj.isNull("professionalRef")) {
                        JSONObject profJson = obj.getJSONObject("professionalRef");
                        app.setProfessionalId(profJson.optLong("codProf"));
                        app.setProfessionalName(
                                profJson.optString("nameProf", "") + " " + profJson.optString("lastNameProf", "")
                        );
                        app.setSpecialityName(profJson.optString("specialityProf", ""));
                        app.setTypeProfName(profJson.optString("typeProf", ""));
                    }

                    // STATUS y DESCRIPCIÓN
                    if (obj.has("statusApp") && !obj.isNull("statusApp"))
                        app.setStatus(obj.getString("statusApp"));
                    if (obj.has("descApp") && !obj.isNull("descApp"))
                        app.setDescription(obj.getString("descApp"));

                    data.add(app);
                }
            }
        } catch (Exception e) {
            System.err.println(">> Error al parsear citas: " + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }
}