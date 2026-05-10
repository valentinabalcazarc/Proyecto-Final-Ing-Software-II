package com.piedraazul.app_client.design_patterns.decorator.descApp;

public enum AppointmentTag {

    URGENTE("[Urgente]"),
    PRIORITARIA("[Prioritaria]"),
    CITA_CONTROL("[Cita Control]"),
    NINGUNA("");

    private final String prefix;

    AppointmentTag(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}