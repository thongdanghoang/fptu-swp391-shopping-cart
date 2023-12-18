package com.thomasdang.as.util;

import java.security.SecureRandom;

public class RandomPasswordGenerator {

    private RandomPasswordGenerator() {
    }

    public static String generateRandomPassword(int length) {
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=";
        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            password.append(allowedCharacters.charAt(randomIndex));
        }
        return password.toString();
    }
}
