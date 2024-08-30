package com.xworkz.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ComplaintStatus {
    OPEN("Open"),
    IN_REVIEW("In Review"),
    ASSIGNED_TO_DEPARTMENT("Assigned to Department"),
    ASSIGNED_TO_EMPLOYEE("Assigned to Employee"),
    IN_PROGRESS("In Progress"),
    PENDING("Pending"),
    RESOLVED("Resolved"),
    REOPENED("Reopened"),
    NOT_RESOLVED("Not Resolved"),
    REJECTED("Rejected");

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
