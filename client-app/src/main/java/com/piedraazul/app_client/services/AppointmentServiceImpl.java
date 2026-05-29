package com.piedraazul.app_client.services;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.enums.TypeProfEnum;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.AppointmentRep;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.piedraazul.app_client.dto.AppointmentDTO;
import com.piedraazul.app_client.dto.AppointmentResponseDTO;
import com.piedraazul.app_client.mappers.AppointmentMapper;

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
    private final String BASE_URL = "http://localhost:8080/piedraAzul/appointments";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public boolean registerAppointment(Appointment appointment) {
        if (appointment == null)
            return false;
        try {
            AppointmentDTO dto = AppointmentMapper.toRequestDTO(appointment, null);
            String jsonBody = objectMapper.writeValueAsString(dto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
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
    public Appointment getFirstAvailableBySpeciality(SpecialityProfEnum speciality) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/first-available/" + speciality.name()))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">> firstAvailable status: " + response.statusCode());
            System.out.println(">> firstAvailable body: " + response.body());

            if (response.statusCode() == 200) {
                AppointmentResponseDTO dto = objectMapper.readValue(response.body(), AppointmentResponseDTO.class);
                return AppointmentMapper.toModel(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
                List<Object[]> rows = objectMapper.readValue(response.body(), new TypeReference<List<Object[]>>() {});
                data.addAll(rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
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
        // GET /generated  (sin params → backend usa LocalDate.now() por defecto)
        return fetchAppointmentList(BASE_URL + "/generated");
    }

    @Override
    public List<Appointment> getGeneratedAppointmentsFilteredTyped(Long codProf, LocalDate fecha) {
        // GET /generated[?date=X][&codProf=Y]
        // Si fecha es null NO se envía el param → el backend decide el default
        StringBuilder url = new StringBuilder(BASE_URL + "/generated");
        String sep = "?";
        if (fecha != null) {
            url.append(sep).append("date=").append(fecha);
            sep = "&";
        }
        if (codProf != null) {
            url.append(sep).append("codProf=").append(codProf);
        }
        return fetchAppointmentList(url.toString());
    }

    @Override
    public List<Appointment> getGeneratedAppointmentsBySpecialityTyped(SpecialityProfEnum speciality) {
        // GET /generated/speciality/{speciality}  ← endpoint correcto del backend
        return fetchAppointmentList(BASE_URL + "/generated/speciality/" + speciality.name());
    }

    @Override
    public List<Appointment> getGeneratedAppointmentsBySpecialityFilteredTyped(Long codProf, LocalDate fecha, SpecialityProfEnum speciality) {
        // GET /generated?speciality=X[&date=Y][&codProf=Z]
        // Si fecha es null NO se fuerza LocalDate.now() → muestra todos los slots futuros
        StringBuilder url = new StringBuilder(BASE_URL + "/generated?speciality=" + speciality.name());
        if (fecha != null) {
            url.append("&date=").append(fecha);
        }
        if (codProf != null) {
            url.append("&codProf=").append(codProf);
        }
        return fetchAppointmentList(url.toString());
    }

    @Override
    public List<Appointment> getGeneretedAppointmentsBySpeciality(SpecialityProfEnum speciality) {
        // GET /generated/speciality/{speciality}
        return fetchAppointmentList(BASE_URL + "/generated/speciality/" + speciality.name());
    }

    @Override
    public List<Appointment> getGeneretedAppointmentsBySpecialityFiltered(
            Long codProf, LocalDate fecha, SpecialityProfEnum speciality) {
        // GET /generated?speciality=X[&date=Y][&codProf=Z]
        StringBuilder url = new StringBuilder(BASE_URL + "/generated?speciality=" + speciality.name());
        if (fecha != null) {
            url.append("&date=").append(fecha);
        }
        if (codProf != null) {
            url.append("&codProf=").append(codProf);
        }
        return fetchAppointmentList(url.toString());
    }

    @Override
    public boolean saveAppointment(Appointment appointment, Long patientId) {
        try {
            AppointmentDTO dto = AppointmentMapper.toRequestDTO(appointment, patientId);
            String jsonBody = objectMapper.writeValueAsString(dto);

            System.out.println(">> JSON enviado: " + jsonBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
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

    @Override
    public boolean updateAppointmentStatus(Long appointmentId, String newStatus) {
        try {
            String jsonBody = "{\"statusApp\":\"" + newStatus + "\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + appointmentId + "/status"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">> updateStatus status: " + response.statusCode());
            System.out.println(">> updateStatus body: " + response.body());
            return response.statusCode() == 200 || response.statusCode() == 204;
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

            if (response.statusCode() == 200) {
                List<AppointmentResponseDTO> dtos = objectMapper.readValue(response.body(), new TypeReference<List<AppointmentResponseDTO>>() {});
                for (AppointmentResponseDTO dto : dtos) {
                    data.add(AppointmentMapper.toModel(dto));
                }
            }
        } catch (Exception e) {
            System.err.println(">> Error al parsear citas: " + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public byte[] exportFile(List<Long> ids, String format) {
        try {
            String jsonBody = objectMapper.writeValueAsString(ids);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/export?format=" + format.toLowerCase()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.err.println(">> Error en exportación: HTTP " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println(">> Error al exportar archivo: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}