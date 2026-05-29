package com.piedraazul.appointment_service.application.service;

import com.piedraazul.appointment_service.application.strategy.AppointmentExportStrategy;
import com.piedraazul.appointment_service.domain.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Servicio "contexto" del patrón Strategy (GoF).
 *
 * Registra las tres estrategias concretas de exportación (JSON, HTML, CSV)
 * en un mapa y selecciona la estrategia apropiada en tiempo de ejecución
 * según el parámetro {@code format} recibido del controlador.
 *
 * Las estrategias se inyectan automáticamente por Spring gracias al
 * constructor que recibe una {@code List<AppointmentExportStrategy>}.
 */
@Service
public class AppointmentExportService {

    private final Map<String, AppointmentExportStrategy> strategies;

    /**
     * Spring inyecta todas las implementaciones de {@link AppointmentExportStrategy}
     * registradas como beans. Se indexan por extensión de archivo (sin punto)
     * para permitir la selección por formato.
     */
    public AppointmentExportService(List<AppointmentExportStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        s -> s.getFileExtension().replace(".", "").toLowerCase(),
                        Function.identity()
                ));
    }

    /**
     * Genera el contenido del archivo de exportación delegando al
     * Strategy correspondiente al formato solicitado.
     *
     * @param appointments lista de citas a exportar
     * @param format       formato de exportación ("json", "html" o "csv")
     * @return arreglo de bytes con el contenido del archivo
     * @throws IllegalArgumentException si el formato no es soportado
     */
    public byte[] exportAppointments(List<Appointment> appointments, String format) {
        AppointmentExportStrategy strategy = resolveStrategy(format);
        return strategy.export(appointments);
    }

    /**
     * Retorna el Content-Type HTTP correspondiente al formato.
     */
    public String getContentType(String format) {
        return resolveStrategy(format).getContentType();
    }

    /**
     * Retorna la extensión del archivo correspondiente al formato.
     */
    public String getFileExtension(String format) {
        return resolveStrategy(format).getFileExtension();
    }

    private AppointmentExportStrategy resolveStrategy(String format) {
        AppointmentExportStrategy strategy = strategies.get(format.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Formato de exportación no soportado: " + format
                            + ". Formatos válidos: " + strategies.keySet());
        }
        return strategy;
    }
}
