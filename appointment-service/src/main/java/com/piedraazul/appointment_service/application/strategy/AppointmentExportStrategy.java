package com.piedraazul.appointment_service.application.strategy;

import com.piedraazul.appointment_service.domain.model.Appointment;

import java.util.List;

/**
 * Interfaz del patrón Strategy (GoF) para la exportación de citas.
 *
 * Cada implementación concreta encapsula el algoritmo de generación
 * de un formato de archivo específico (JSON, HTML, CSV).
 *
 * El contexto ({@link com.piedraazul.appointment_service.application.service.AppointmentExportService})
 * selecciona la estrategia apropiada en tiempo de ejecución según
 * el formato solicitado por el cliente.
 */
public interface AppointmentExportStrategy {

    /**
     * Genera el contenido del archivo de exportación a partir de la
     * lista de citas proporcionada.
     *
     * @param appointments lista de citas a exportar
     * @return arreglo de bytes con el contenido del archivo generado
     */
    byte[] export(List<Appointment> appointments);

    /**
     * Retorna el MIME type correspondiente al formato de exportación.
     *
     * @return el Content-Type HTTP (e.g. "application/json", "text/html", "text/csv")
     */
    String getContentType();

    /**
     * Retorna la extensión del archivo generado.
     *
     * @return extensión con punto (e.g. ".json", ".html", ".csv")
     */
    String getFileExtension();
}
