package com.piedraazul.app_client.design_patterns.decorator.descApp;

import com.piedraazul.app_client.models.Appointment;

public class DescriptionTagDecorator {

    private final AppointmentTag tag;

    public DescriptionTagDecorator(AppointmentTag tag) {
        this.tag = tag;
    }

    public void apply(Appointment appointment) {
        if (appointment == null || tag == AppointmentTag.NINGUNA) {
            return;
        }

        String original = appointment.getDescription();

        String decorated = (original == null || original.isBlank())
                ? tag.getPrefix()
                : tag.getPrefix() + " - " + original.trim();

        appointment.setDescription(decorated);
    }
}