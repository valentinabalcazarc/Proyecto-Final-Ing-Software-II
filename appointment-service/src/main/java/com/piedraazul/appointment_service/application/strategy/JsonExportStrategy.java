package com.piedraazul.appointment_service.application.strategy;

import com.piedraazul.appointment_service.domain.model.Appointment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Estrategia concreta — Patrón Strategy (GoF).
 *
 * Genera un archivo JSON con los datos de las citas, incluyendo
 * los nombres del paciente y profesional resueltos, timestamp de
 * exportación y total de citas.
 */
@Component
public class JsonExportStrategy implements AppointmentExportStrategy {

    private static final DateTimeFormatter TIMESTAMP_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public byte[] export(List<Appointment> appointments) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"exportadoEn\": \"").append(timestamp).append("\",\n");
        sb.append("  \"totalCitas\": ").append(appointments.size()).append(",\n");
        sb.append("  \"citas\": [\n");

        for (int i = 0; i < appointments.size(); i++) {
            Appointment app = appointments.get(i);
            sb.append("  {\n");
            sb.append("    \"id\": ").append(app.getCodApp()).append(",\n");
            sb.append("    \"paciente\": \"").append(nullSafe(getPatientName(app))).append("\",\n");
            sb.append("    \"profesional\": \"").append(nullSafe(getProfessionalName(app))).append("\",\n");
            sb.append("    \"especialidad\": \"").append(nullSafe(getSpeciality(app))).append("\",\n");
            sb.append("    \"tipo\": \"").append(nullSafe(getTypeProf(app))).append("\",\n");
            sb.append("    \"fecha\": \"").append(app.getDateApp()).append("\",\n");
            sb.append("    \"hora\": \"").append(app.getTimeApp()).append("\",\n");
            sb.append("    \"estado\": \"").append(nullSafe(getStatus(app))).append("\",\n");
            sb.append("    \"descripcion\": \"").append(nullSafe(app.getDescApp())).append("\"");

            // Agregar alerta según estado
            String alerta = resolveAlert(getStatus(app));
            if (!alerta.isEmpty()) {
                sb.append(",\n    \"alerta\": \"").append(alerta).append("\"");
            }

            sb.append("\n  }");
            if (i < appointments.size() - 1) sb.append(",");
            sb.append("\n");
        }

        sb.append("  ]\n");
        sb.append("}");

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public String getFileExtension() {
        return ".json";
    }

    private String resolveAlert(String status) {
        if (status == null) return "";
        return switch (status) {
            case "Cancelled" -> "Cita cancelada";
            case "Rescheduled" -> "Cita reagendada";
            case "Completed" -> "Cita completada";
            default -> "";
        };
    }

    private String getPatientName(Appointment app) {
        if (app.getPatientRef() == null) return "";
        String name = nullSafe(app.getPatientRef().getNamePatient());
        String lastName = nullSafe(app.getPatientRef().getLastNamePatient());
        return (name + " " + lastName).trim();
    }

    private String getProfessionalName(Appointment app) {
        if (app.getProfessionalRef() == null) return "";
        String name = nullSafe(app.getProfessionalRef().getNameProf());
        String lastName = nullSafe(app.getProfessionalRef().getLastNameProf());
        return (name + " " + lastName).trim();
    }

    private String getSpeciality(Appointment app) {
        if (app.getProfessionalRef() == null || app.getProfessionalRef().getSpecialityProf() == null) return "";
        return app.getProfessionalRef().getSpecialityProf().name();
    }

    private String getTypeProf(Appointment app) {
        if (app.getProfessionalRef() == null || app.getProfessionalRef().getTypeProf() == null) return "";
        return app.getProfessionalRef().getTypeProf().name();
    }

    private String getStatus(Appointment app) {
        return app.getStatusApp() != null ? app.getStatusApp().name() : "";
    }

    private String nullSafe(String value) {
        return value != null ? value : "";
    }
}
