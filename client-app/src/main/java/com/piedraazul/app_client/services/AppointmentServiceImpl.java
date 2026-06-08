package com.piedraazul.app_client.services;

import com.piedraazul.app_client.enums.SpecialityProfEnum;
import com.piedraazul.app_client.models.Appointment;
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
import java.util.ArrayList;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8080/piedraAzul/appointments";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final FestivosService festivosService = new FestivosService();

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
        // FIX: si el backend devuelve una fecha inválida (festivo, fin de semana),
        // reintentar desde el día siguiente hasta encontrar un slot válido o agotar 60 días.
        LocalDate fromDate = null; // primera consulta sin fromDate → backend usa LocalDate.now()
        LocalDate limit = LocalDate.now().plusDays(60);

        while (true) {
            Appointment appointment = fetchFirstAvailable(speciality, fromDate);

            // El backend no encontró nada → no hay citas disponibles
            if (appointment == null || appointment.getDate() == null) {
                return null;
            }

            // La fecha es válida → retornar
            if (!festivosService.esDiaInvalido(appointment.getDate())) {
                return appointment;
            }

            // La fecha es inválida → avanzar al día siguiente y reintentar
            System.err.println(">> Fecha inválida recibida del backend (" + appointment.getDate()
                    + "), reintentando desde " + appointment.getDate().plusDays(1));
            fromDate = appointment.getDate().plusDays(1);

            // Saltar fines de semana y festivos antes de volver a consultar
            while (fromDate.isBefore(limit) && festivosService.esDiaInvalido(fromDate)) {
                fromDate = fromDate.plusDays(1);
            }

            if (!fromDate.isBefore(limit)) {
                return null; // Superado el límite de búsqueda
            }
        }
    }

    /** Hace una sola llamada al endpoint /first-available/{speciality}[?fromDate=X] */
    private Appointment fetchFirstAvailable(SpecialityProfEnum speciality, LocalDate fromDate) {
        try {
            String url = BASE_URL + "/first-available/" + speciality.name();
            if (fromDate != null) {
                url += "?fromDate=" + fromDate;
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + SessionManager.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">> firstAvailable [fromDate=" + fromDate + "] status: " + response.statusCode());
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
        return fetchAppointmentList(BASE_URL + "/generated/speciality/" + speciality.name());
    }

    @Override
    public List<Appointment> getGeneratedAppointmentsBySpecialityFilteredTyped(Long codProf, LocalDate fecha, SpecialityProfEnum speciality) {
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
        return fetchAppointmentList(BASE_URL + "/generated/speciality/" + speciality.name());
    }

    @Override
    public List<Appointment> getGeneretedAppointmentsBySpecialityFiltered(
            Long codProf, LocalDate fecha, SpecialityProfEnum speciality) {
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
            System.out.println(">> JSON response: " + response);

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