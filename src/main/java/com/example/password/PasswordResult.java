package com.example.password;

public class PasswordResult {

    private String strength;
    private int score;
    private String feedback;
    private double entropy;

    public PasswordResult(String strength, int score, String feedback, double entropy) {
        this.strength = strength;
        this.score = score;
        this.feedback = feedback;
        this.entropy = entropy;
    }

    public PasswordResult(String strength, int score, String feedback) {
        this(strength, score, feedback, 0);
    }

    // Getter for strength
    public String getStrength() {
        return strength;
    }

    // Getter for score (needed for UI progress bar)
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\nPassword Strength: ").append(strength);
        result.append("\nScore: ").append(score).append("/5");
        if (entropy > 0) {
            result.append(String.format("\nEntropy: %.2f bits", entropy));
        }
        if (!feedback.isEmpty()) {
            result.append("\nSuggestions:\n").append(feedback);
        }
        return result.toString();
    }
}
