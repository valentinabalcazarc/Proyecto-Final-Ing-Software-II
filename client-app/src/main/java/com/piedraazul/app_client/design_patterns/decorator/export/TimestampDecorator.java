package com.piedraazul.app_client.design_patterns.decorator.export;

import com.piedraazul.app_client.models.Appointment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Decorador concreto — Patrón Decorador (GoF).
 *
 * Agrega al inicio del contenido exportado una cabecera con la
 * fecha y hora exacta en que se generó el reporte.
 *
 * Funciona tanto para JSON como para HTML, adaptando el formato
 * de la cabecera a cada tipo de salida.
 *
 */
public class TimestampDecorator extends AppointmentFormatterDecorator {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public TimestampDecorator(AppointmentFormatter wrapped) {
        super(wrapped);
    }

    @Override
    public String format(List<Appointment> appointments, String format) {
        String base = super.format(appointments, format);
        String timestamp = LocalDateTime.now().format(FORMATTER);

        if ("JSON".equalsIgnoreCase(format)) {
            return addTimestampToJson(base, timestamp);
        } else if ("HTML".equalsIgnoreCase(format)) {
            return addTimestampToHtml(base, timestamp);
        } else if ("CSV".equalsIgnoreCase(format)) {
            return addTimestampToCsv(base, timestamp);
        }
        return base;
    }

    // Métodos privados de inserción de timestamp
    private String addTimestampToJson(String base, String timestamp) {
        // Contar cuántas citas hay contando los objetos en el array base
        int totalCitas = base.split("\"id\":").length - 1;

        return "{\n"
                + "  \"exportadoEn\": \"" + timestamp + "\",\n"
                + "  \"totalCitas\": " + totalCitas + ",\n"
                + "  \"citas\": " + base + "\n"
                + "}";
    }

    private String addTimestampToHtml(String base, String timestamp) {
        String timestampTag = "<p style=\"color: #666; font-size: 0.9em;\">"
                + "Exportado el: <strong>" + timestamp + "</strong></p>\n";

        return base.replace(
                "<h1>Reporte de Citas Médicas</h1>",
                "<h1>Reporte de Citas Médicas</h1>\n  " + timestampTag
        );
    }

    /**
     * CSV: agrega una línea de comentario con la fecha/hora de exportación
     * antes de la cabecera del CSV.
     */
    private String addTimestampToCsv(String base, String timestamp) {
        return "# Exportado el: " + timestamp + "\n" + base;
    }
}