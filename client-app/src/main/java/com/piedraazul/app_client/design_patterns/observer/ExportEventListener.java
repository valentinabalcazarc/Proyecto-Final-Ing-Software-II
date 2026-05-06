package com.piedraazul.app_client.design_patterns.observer;

/**
 * Interfaz suscriptora del patrón Observer.
 *
 * Cualquier clase que desee recibir notificaciones de exportación
 * debe implementar esta interfaz.
 */
public interface ExportEventListener {

    /**
     * Método invocado por el EventManager cuando ocurre un evento de exportación.
     *
     */
    void update(String format, String exportedContent);
}
