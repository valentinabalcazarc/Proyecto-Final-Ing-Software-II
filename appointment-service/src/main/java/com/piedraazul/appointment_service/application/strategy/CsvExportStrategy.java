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
 * Genera un archivo CSV con cabecera, escape correcto de valores,
 * comentario con timestamp de exportación y columna de alerta
 * según el estado de cada cita.
 */
@Component
public class CsvExportStrategy implements AppointmentExportStrategy {

    private static final DateTimeFormatter TIMESTAMP_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public byte[] export(List<Appointment> appointments) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);

        StringBuilder sb = new StringBuilder();

        // Comentario con timestamp
        sb.append("# Exportado el: ").append(timestamp).append("\n");

        // Cabecera
        sb.append("ID,Paciente,Profesional,Especialidad,Tipo,Fecha,Hora,Estado,Descripción,Alerta\n");

        for (Appointment app : appointments) {
            sb.append(app.getCodApp()).append(",");
            sb.append(escapeCsv(nullSafe(getPatientName(app)))).append(",");
            sb.append(escapeCsv(nullSafe(getProfessionalName(app)))).append(",");
            sb.append(escapeCsv(nullSafe(getSpeciality(app)))).append(",");
            sb.append(escapeCsv(nullSafe(getTypeProf(app)))).append(",");
            sb.append(app.getDateApp()).append(",");
            sb.append(app.getTimeApp()).append(",");
            sb.append(escapeCsv(nullSafe(getStatus(app)))).append(",");
            sb.append(escapeCsv(nullSafe(app.getDescApp()))).append(",");
            sb.append(escapeCsv(resolveAlert(getStatus(app)))).append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getContentType() {
        return "text/csv";
    }

    @Override
    public String getFileExtension() {
        return ".csv";
    }

    /**
     * Escapa un valor para CSV: si contiene comas, comillas dobles o saltos
     * de línea, lo envuelve entre comillas dobles y duplica las comillas internas.
     */
    private String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
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
