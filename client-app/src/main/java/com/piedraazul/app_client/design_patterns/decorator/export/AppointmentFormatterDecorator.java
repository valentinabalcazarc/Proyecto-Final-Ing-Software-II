package com.piedraazul.app_client.design_patterns.decorator.export;

import com.piedraazul.app_client.models.Appointment;
import java.util.List;

/**
 * Clase abstracta base del patrón Decorador (GoF).
 *
 * Envuelve un AppointmentFormatter existente y delega la llamada
 * a format() en él. Los decoradores concretos extienden esta clase,
 * llaman a super.format() para obtener el resultado del formateador
 * envuelto, y luego le agregan su propio comportamiento.
 *
 * Esto permite encadenar decoradores de forma transparente:
 *
 *   AppointmentFormatter f = new BaseAppointmentFormatter();
 *   f = new TimestampDecorator(f);
 *   f = new StatusHighlightDecorator(f);
 *   String resultado = f.format(citas, "HTML");
 */
public abstract class AppointmentFormatterDecorator implements AppointmentFormatter {

    protected final AppointmentFormatter wrapped;

    public AppointmentFormatterDecorator(AppointmentFormatter wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String format(List<Appointment> appointments, String format) {
        return wrapped.format(appointments, format);
    }
}