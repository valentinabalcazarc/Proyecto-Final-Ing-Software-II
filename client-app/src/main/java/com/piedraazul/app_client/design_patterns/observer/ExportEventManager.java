package com.piedraazul.app_client.design_patterns.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Notificador básico del patrón Observer (GoF).
 *
 * Gestiona la lista de suscriptores y los notifica cuando ocurre
 * un evento de exportación.
 */
public class ExportEventManager {

    /**
     * Mapa de tipo de evento → lista de suscriptores registrados.
     */
    private final Map<String, List<ExportEventListener>> listeners = new HashMap<>();

    /**
     * Constructor que inicializa los tipos de eventos soportados.
     *
     */
    public ExportEventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    /**
     * Suscribe un listener a un tipo de evento específico.
     */
    public void subscribe(String eventType, ExportEventListener listener) {
        List<ExportEventListener> users = listeners.get(eventType);
        if (users != null) {
            users.add(listener);
        }
    }

    /**
     * Cancela la suscripción de un listener de un tipo de evento.
     *
     */
    public void unsubscribe(String eventType, ExportEventListener listener) {
        List<ExportEventListener> users = listeners.get(eventType);
        if (users != null) {
            users.remove(listener);
        }
    }

    /**
     * Notifica a todos los suscriptores de un tipo de evento con el
     * formato y contenido exportado.
     *
     */
    public void notifyListeners(String eventType, String format, String exportedContent) {
        List<ExportEventListener> users = listeners.get(eventType);
        if (users != null) {
            for (ExportEventListener listener : users) {
                listener.update(format, exportedContent);
            }
        }
    }
}
