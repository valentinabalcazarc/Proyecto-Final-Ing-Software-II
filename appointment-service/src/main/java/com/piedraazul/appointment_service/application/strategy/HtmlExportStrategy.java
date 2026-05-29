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
 * Genera un documento HTML completo con una tabla estilizada que
 * incluye colores por estado de cita, timestamp de exportación
 * y leyenda de estados.
 */
@Component
public class HtmlExportStrategy implements AppointmentExportStrategy {

    private static final DateTimeFormatter TIMESTAMP_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public byte[] export(List<Appointment> appointments) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"es\">\n");
        sb.append("<head>\n");
        sb.append("  <meta charset=\"UTF-8\">\n");
        sb.append("  <title>Citas Exportadas</title>\n");
        sb.append("  <style>\n");
        sb.append("    body { font-family: Arial, sans-serif; margin: 20px; }\n");
        sb.append("    h1 { color: #5947FF; }\n");
        sb.append("    table { border-collapse: collapse; width: 100%; }\n");
        sb.append("    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
        sb.append("    th { background-color: #5947FF; color: white; }\n");
        sb.append("    tr:nth-child(even) { background-color: #f2f2f2; }\n");
        sb.append("  </style>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("  <h1>Reporte de Citas Médicas</h1>\n");

        // Timestamp
        sb.append("  <p style=\"color: #666; font-size: 0.9em;\">");
        sb.append("Exportado el: <strong>").append(timestamp).append("</strong></p>\n");

        sb.append("  <table>\n");
        sb.append("    <tr>\n");
        sb.append("      <th>ID</th><th>Paciente</th><th>Profesional</th>");
        sb.append("<th>Especialidad</th><th>Tipo</th><th>Fecha</th>");
        sb.append("<th>Hora</th><th>Estado</th><th>Descripción</th>\n");
        sb.append("    </tr>\n");

        for (Appointment app : appointments) {
            String status = getStatus(app);
            String bgColor = resolveHtmlColor(status);

            if (!bgColor.isEmpty()) {
                sb.append("    <tr style=\"background-color: ").append(bgColor).append(";\">\n");
            } else {
                sb.append("    <tr>\n");
            }

            sb.append("      <td>").append(app.getCodApp()).append("</td>");
            sb.append("<td>").append(nullSafe(getPatientName(app))).append("</td>");
            sb.append("<td>").append(nullSafe(getProfessionalName(app))).append("</td>");
            sb.append("<td>").append(nullSafe(getSpeciality(app))).append("</td>");
            sb.append("<td>").append(nullSafe(getTypeProf(app))).append("</td>");
            sb.append("<td>").append(app.getDateApp()).append("</td>");
            sb.append("<td>").append(app.getTimeApp()).append("</td>");
            sb.append("<td>").append(nullSafe(status)).append("</td>");
            sb.append("<td>").append(nullSafe(app.getDescApp())).append("</td>\n");
            sb.append("    </tr>\n");
        }

        sb.append("  </table>\n");

        // Leyenda de estados
        sb.append("\n  <div style=\"margin-top: 16px; font-size: 0.85em; color: #444;\">\n");
        sb.append("    <strong>Leyenda de estados:</strong>&nbsp;\n");
        sb.append("    <span style=\"background:#e5ffe8; padding:2px 8px; border-radius:4px;\">Completada</span>&nbsp;\n");
        sb.append("    <span style=\"background:#fff9e5; padding:2px 8px; border-radius:4px;\">Reagendada</span>&nbsp;\n");
        sb.append("    <span style=\"background:#ffe5e5; padding:2px 8px; border-radius:4px;\">Cancelada</span>\n");
        sb.append("  </div>\n");

        sb.append("</body>\n");
        sb.append("</html>");

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public String getFileExtension() {
        return ".html";
    }

    private String resolveHtmlColor(String status) {
        if (status == null) return "";
        return switch (status) {
            case "Cancelled" -> "#ffe5e5";
            case "Rescheduled" -> "#fff9e5";
            case "Completed" -> "#e5ffe8";
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
