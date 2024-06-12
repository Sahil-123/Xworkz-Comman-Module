package com.xworkz.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS;
    private static final int PASSWORD_LENGTH = 16;
    private static final Random RANDOM = new SecureRandom();

    public static String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        // Ensure at least one character from each category
        password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));

        // Fill remaining characters randomly
        for (int i = 3; i < PASSWORD_LENGTH - 14; i++) {
            password.append(ALL_CHARACTERS.charAt(RANDOM.nextInt(ALL_CHARACTERS.length())));
        }

        // Append current date and time
        String dateTime = getCurrentDateTime();
        password.append(dateTime);

        // Shuffle to ensure randomness
        return shuffleString(password.toString());
    }

    private static String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(formatter);
    }

    private static String shuffleString(String string) {
        StringBuilder shuffled = new StringBuilder(string.length());
        int[] indices = RANDOM.ints(0, string.length()).distinct().limit(string.length()).toArray();
        for (int i : indices) {
            shuffled.append(string.charAt(i));
        }
        return shuffled.toString();
    }
}
