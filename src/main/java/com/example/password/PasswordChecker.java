package com.example.password;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PasswordChecker {

    private static final int MIN_LENGTH = 8;

    // Common weak passwords
    private static final Set<String> COMMON_PASSWORDS = new HashSet<>(
            Arrays.asList("123456", "password", "qwerty", "abc123", "111111")
    );

    // Evaluate password and return result
    public PasswordResult evaluate(String password) {

        int score = 0;
        StringBuilder feedback = new StringBuilder();

        // Check common passwords first
        if (COMMON_PASSWORDS.contains(password.toLowerCase())) {
            return new PasswordResult("VERY WEAK", 0,
                    "Password is too common and easily guessable.");
        }

        // Rule-based scoring
        if (password.length() >= MIN_LENGTH) score++;
        else feedback.append("- Use at least 8 characters\n");

        if (password.matches(".*[A-Z].*")) score++;
        else feedback.append("- Add at least one uppercase letter\n");

        if (password.matches(".*[a-z].*")) score++;
        else feedback.append("- Add at least one lowercase letter\n");

        if (password.matches(".*[0-9].*")) score++;
        else feedback.append("- Add at least one number\n");

        if (password.matches(".*[^a-zA-Z0-9].*")) score++;
        else feedback.append("- Add at least one special character\n");

        // Entropy calculation
        double entropy = calculateEntropy(password);

        String strength = calculateStrength(score, entropy);

        return new PasswordResult(strength, score, feedback.toString(), entropy);
    }

    // Entropy calculation (optional for job projects)
    private double calculateEntropy(String password) {
        int charsetSize = 0;
        if (password.matches(".*[a-z].*")) charsetSize += 26;
        if (password.matches(".*[A-Z].*")) charsetSize += 26;
        if (password.matches(".*[0-9].*")) charsetSize += 10;
        if (password.matches(".*[^a-zA-Z0-9].*")) charsetSize += 32;

        return password.length() * (Math.log(charsetSize) / Math.log(2));
    }

    // Map score + entropy to strength label
    private String calculateStrength(int score, double entropy) {
        if (score == 5 && entropy > 50) return "VERY STRONG";
        if (score >= 4) return "STRONG";
        if (score >= 3) return "MEDIUM";
        return "WEAK";
    }
}
