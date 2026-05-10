package com.piedraazul.app_client.design_patterns.decorator.export;


import com.piedraazul.app_client.models.Appointment;
import java.util.List;

/**
 * Implementación base concreta del patrón Decorador (GoF).
 *
 * Genera el contenido de exportación de las citas en el formato
 * solicitado (JSON o HTML), sin ningún comportamiento adicional.
 *
 * Es el "componente concreto" que los decoradores envuelven para
 * agregarle funcionalidad extra sin modificar esta clase.
 */
public class BaseAppointmentFormatter implements AppointmentFormatter {

    @Override
    public String format(List<Appointment> appointments, String format) {
        if ("JSON".equalsIgnoreCase(format)) {
            return buildJson(appointments);
        } else if ("HTML".equalsIgnoreCase(format)) {
            return buildHtml(appointments);
        }
        return "";
    }

    // Métodos privados de construcción
    private String buildJson(List<Appointment> appointments) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment app = appointments.get(i);
            sb.append("  {\n");
            sb.append("    \"id\": ").append(app.getId()).append(",\n");
            sb.append("    \"paciente\": \"").append(nullSafe(app.getPatientName())).append("\",\n");
            sb.append("    \"profesional\": \"").append(nullSafe(app.getProfessionalName())).append("\",\n");
            sb.append("    \"especialidad\": \"").append(nullSafe(app.getSpecialityName())).append("\",\n");
            sb.append("    \"tipo\": \"").append(nullSafe(app.getTypeProfName())).append("\",\n");
            sb.append("    \"fecha\": \"").append(app.getDate()).append("\",\n");
            sb.append("    \"hora\": \"").append(app.getTime()).append("\",\n");
            sb.append("    \"estado\": \"").append(nullSafe(app.getStatus())).append("\",\n");
            sb.append("    \"descripcion\": \"").append(nullSafe(app.getDescription())).append("\"\n");
            sb.append("  }");
            if (i < appointments.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

    private String buildHtml(List<Appointment> appointments) {
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
        sb.append("  <table>\n");
        sb.append("    <tr>\n");
        sb.append("      <th>ID</th><th>Paciente</th><th>Profesional</th>");
        sb.append("<th>Especialidad</th><th>Tipo</th><th>Fecha</th>");
        sb.append("<th>Hora</th><th>Estado</th><th>Descripción</th>\n");
        sb.append("    </tr>\n");
        for (Appointment app : appointments) {
            sb.append("    <tr>\n");
            sb.append("      <td>").append(app.getId()).append("</td>");
            sb.append("<td>").append(nullSafe(app.getPatientName())).append("</td>");
            sb.append("<td>").append(nullSafe(app.getProfessionalName())).append("</td>");
            sb.append("<td>").append(nullSafe(app.getSpecialityName())).append("</td>");
            sb.append("<td>").append(nullSafe(app.getTypeProfName())).append("</td>");
            sb.append("<td>").append(app.getDate()).append("</td>");
            sb.append("<td>").append(app.getTime()).append("</td>");
            sb.append("<td>").append(nullSafe(app.getStatus())).append("</td>");
            sb.append("<td>").append(nullSafe(app.getDescription())).append("</td>\n");
            sb.append("    </tr>\n");
        }
        sb.append("  </table>\n");
        sb.append("</body>\n");
        sb.append("</html>");
        return sb.toString();
    }

    protected String nullSafe(String value) {
        return value != null ? value : "";
    }
}