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

    private String nullSafe(String value) {
        return value != null ? value : "";
    }
}