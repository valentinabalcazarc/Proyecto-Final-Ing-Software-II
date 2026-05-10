package com.piedraazul.app_client.design_patterns.decorator.export;

import com.piedraazul.app_client.models.Appointment;

import java.util.List;

public interface AppointmentFormatter {
    String format(List<Appointment> appointments, String format);
}
