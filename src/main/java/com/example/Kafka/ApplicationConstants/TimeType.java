package com.example.Kafka.ApplicationConstants;

public enum TimeType {
    MILLISECONDS("MILLISECONDS"),
    SECONDS("SECONDS"),
    MINUTES("MINUTES"),
    HOURS("HOURS"),
    DAYS("DAYS");

    private final String value;

    private TimeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.value;
    }

    public String getName() {
        return this.name();
    }

    public static TimeType getTimeType(String value) {
        for (TimeType a : values()) {
            if (a.value.equalsIgnoreCase(value)) {
                return a;
            }
        }
        return null;
    }
}