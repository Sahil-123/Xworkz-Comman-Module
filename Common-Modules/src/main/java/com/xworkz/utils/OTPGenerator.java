package com.xworkz.utils;

import java.security.SecureRandom;

public class OTPGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    public static String generateOTP() {
        int otp = secureRandom.nextInt(900000) + 100000; // Ensure a 6-digit number
        return String.valueOf(otp);
    }

}
