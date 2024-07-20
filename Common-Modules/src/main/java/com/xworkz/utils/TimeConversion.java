package com.xworkz.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeConversion {
    public static Long getDuration(LocalDateTime startDateTime) {
        LocalDateTime endDateTime = LocalDateTime.now();

        if (startDateTime == null) return 0L;

        // Calculate the duration between the two LocalDateTime instances
        Duration duration = Duration.between(startDateTime, endDateTime);

        // Get the number of hours between the two LocalDateTime instances
        long hours = duration.toHours();

        System.out.println("Number of hours between the two dates: " + hours);
        System.out.println(LocalDateTime.now().minusHours(1L).minusMinutes(30));

        return hours;
    }

    public static Long getDurationInMinutes(LocalDateTime startDateTime) {
        LocalDateTime endDateTime = LocalDateTime.now();

        if (startDateTime == null) return 0L;

        // Calculate the duration between the two LocalDateTime instances
        Duration duration = Duration.between(startDateTime, endDateTime);

        // Get the number of hours between the two LocalDateTime instances
        long minutes = duration.toMinutes();

        System.out.println("Number of minutes between the two times: " + minutes);
        System.out.println(LocalDateTime.now().minusHours(1L).minusMinutes(30));

        return Math.abs(minutes);
    }
}
