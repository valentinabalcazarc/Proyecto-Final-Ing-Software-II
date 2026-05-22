package com.piedraazul.app_client.design_patterns.decorator.export;

import com.piedraazul.app_client.models.Appointment;
import java.util.List;

/**
 * Decorador concreto — Patrón Decorador (GoF).
 *
 * Resalta visualmente las citas según su estado (Scheduled,
 * Completed, Cancelled, Rescheduled) en la salida exportada.
 *
 * - JSON: agrega un campo "alerta" a cada cita con estado relevante.
 * - HTML: aplica color de fondo a cada fila de la tabla según el estado.
 *
 * Estados y su representación:
 *   Cancelled    → rojo     (#ffe5e5 )
 *   Rescheduled  → amarillo (#fff9e5 )
 *   Completed    → verde    (#e5ffe8 )
 *   Scheduled    → sin marca (cita normal pendiente)
 *
 */
public class StatusHighlightDecorator extends AppointmentFormatterDecorator {

    public StatusHighlightDecorator(AppointmentFormatter wrapped) {
        super(wrapped);
    }

    @Override
    public String format(List<Appointment> appointments, String format) {
        String base = super.format(appointments, format);

        if ("JSON".equalsIgnoreCase(format)) {
            return highlightJson(base, appointments);
        } else if ("HTML".equalsIgnoreCase(format)) {
            return highlightHtml(base, appointments);
        } else if ("CSV".equalsIgnoreCase(format)) {
            return highlightCsv(base, appointments);
        }
        return base;
    }

    // JSON: agrega campo "alerta" por cada cita según su estado
    private String highlightJson(String base, List<Appointment> appointments) {
        for (Appointment app : appointments) {
            String status = app.getStatus();
            String alerta = resolveJsonAlert(status);

            if (!alerta.isEmpty()) {
                // Busca la descripcion de esta cita y agrega el campo "alerta" después
                String target = "\"descripcion\": \"" + nullSafe(app.getDescription()) + "\"\n  }";
                String replacement = "\"descripcion\": \"" + nullSafe(app.getDescription()) + "\",\n"
                        + "    \"alerta\": \"" + alerta + "\"\n  }";
                base = base.replace(target, replacement);
            }
        }
        return base;
    }

    private String resolveJsonAlert(String status) {
        if (status == null) return "";
        return switch (status) {
            case "Cancelled"   -> "Cita cancelada";
            case "Rescheduled" -> "Cita reagendada";
            case "Completed"   -> "Cita completada";
            default            -> "";
        };
    }

    // HTML: aplica color de fondo a cada <tr> según estado

    private String highlightHtml(String base, List<Appointment> appointments) {
        for (Appointment app : appointments) {
            String status = nullSafe(app.getStatus());
            String bgColor = resolveHtmlColor(status);

            if (!bgColor.isEmpty()) {
                String idCell = "<td>" + app.getId() + "</td>";
                String oldRow = "    <tr>\n      " + idCell;
                String newRow = "    <tr style=\"background-color: " + bgColor + ";\">\n      " + idCell;
                base = base.replace(oldRow, newRow);
            }
        }

        String legend = buildHtmlLegend();
        base = base.replace("</body>", legend + "</body>");

        return base;
    }

    private String resolveHtmlColor(String status) {
        return switch (status) {
            case "Cancelled"   -> "#ffe5e5";
            case "Rescheduled" -> "#fff9e5";
            case "Completed"   -> "#e5ffe8";
            default            -> "";
        };
    }

    private String buildHtmlLegend() {
        return "\n  <div style=\"margin-top: 16px; font-size: 0.85em; color: #444;\">\n"
                + "    <strong>Leyenda de estados:</strong>&nbsp;\n"
                + "    <span style=\"background:#e5ffe8; padding:2px 8px; border-radius:4px;\">Completada</span>&nbsp;\n"
                + "    <span style=\"background:#fff9e5; padding:2px 8px; border-radius:4px;\">Reagendada</span>&nbsp;\n"
                + "    <span style=\"background:#ffe5e5; padding:2px 8px; border-radius:4px;\">Cancelada</span>\n"
                + "  </div>\n";
    }

    /**
     * CSV: agrega una columna "Alerta" al final de cada fila según el estado.
     */
    private String highlightCsv(String base, List<Appointment> appointments) {
        String[] lines = base.split("\n");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            // Detectar y modificar la línea de cabecera (contiene "ID,Paciente")
            if (line.startsWith("ID,") || line.startsWith("# ")) {
                if (line.startsWith("ID,")) {
                    sb.append(line).append(",Alerta").append("\n");
                } else {
                    sb.append(line).append("\n");
                }
                continue;
            }

            // Línea de datos: buscar la cita correspondiente por índice
            if (!line.trim().isEmpty()) {
                // Encontrar el índice de la cita en la lista de líneas de datos
                int dataIndex = getDataLineIndex(lines, i);
                if (dataIndex >= 0 && dataIndex < appointments.size()) {
                    String alerta = resolveJsonAlert(appointments.get(dataIndex).getStatus());
                    // Remover el salto de línea final si existe y agregar la columna
                    sb.append(line).append(",").append(alerta).append("\n");
                } else {
                    sb.append(line).append("\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * Calcula el índice de una línea de datos (excluyendo cabecera y comentarios).
     */
    private int getDataLineIndex(String[] lines, int currentIndex) {
        int dataIndex = -1;
        for (int i = 0; i <= currentIndex; i++) {
            String l = lines[i];
            if (!l.startsWith("#") && !l.startsWith("ID,") && !l.trim().isEmpty()) {
                dataIndex++;
            }
        }
        return dataIndex;
    }

    private String nullSafe(String value) {
        return value != null ? value : "";
    }
}