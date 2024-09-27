package com.xworkz.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ComplaintStatus {
    RECEIVED("RECEIVED"),
    OPEN("OPEN"),
    IN_REVIEW("IN_REVIEW"),
    ASSIGNED_TO_DEPARTMENT("ASSIGNED_TO_DEPARTMENT"),
    ASSIGNED_TO_EMPLOYEE("ASSIGNED_TO_EMPLOYEE"),
    IN_PROGRESS("IN_PROGRESS"),
    PENDING("PENDING"),
    RESOLVED("RESOLVED"),
    REOPENED("REOPENED"),
    NOT_RESOLVED("NOT_RESOLVED"),
    REJECTED("REJECTED");

    private final String displayValue;

    ComplaintStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonValue
    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static ComplaintStatus forValue(String value) {
        for (ComplaintStatus status : values()) {
            if (status.displayValue.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }

    @Override
    public String toString() {
        return displayValue;
    }
}
