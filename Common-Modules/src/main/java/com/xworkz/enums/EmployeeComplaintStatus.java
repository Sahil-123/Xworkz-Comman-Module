package com.xworkz.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EmployeeComplaintStatus {
    IN_PROGRESS("In Progress"),
    PENDING("Pending"),
    NOT_RESOLVED("Not Resolved"),
    RESOLVED("Resolved");

    private final String displayValue;

    EmployeeComplaintStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonValue
    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static EmployeeComplaintStatus forValue(String value) {
        for (EmployeeComplaintStatus status : values()) {
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
