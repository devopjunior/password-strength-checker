package com.example.password;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PasswordCheckerUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PasswordCheckerUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Password Strength Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 350);
        frame.setResizable(false);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        frame.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label
        JLabel label = new JLabel("Enter Password:");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        // Password Field
        JPasswordField passwordField = new JPasswordField(25);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(passwordField, gbc);

        // Strength Bar
        JProgressBar strengthBar = new JProgressBar(0, 100);
        strengthBar.setStringPainted(true);
        strengthBar.setPreferredSize(new Dimension(400, 25));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(strengthBar, gbc);

        // Suggestions Area
        JTextArea suggestionsArea = new JTextArea(6, 45);
        suggestionsArea.setEditable(false);
        suggestionsArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        suggestionsArea.setLineWrap(true);
        suggestionsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(suggestionsArea);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        // Clear Button
        JButton clearButton = new JButton("Clear");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(clearButton, gbc);

        // Document listener for live feedback
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            void update() {
                String password = new String(passwordField.getPassword());
                if (password.isEmpty()) {
                    strengthBar.setValue(0);
                    strengthBar.setString("");
                    suggestionsArea.setText("");
                    return;
                }

                PasswordChecker checker = new PasswordChecker();
                PasswordResult result = checker.evaluate(password);

                // Map score to progress bar (0-5 score -> 0-100%)
                int value = result.getScore() * 20;
                strengthBar.setValue(value);

                // Color-code bar
                switch (result.getStrength().toUpperCase()) {
                    case "VERY WEAK", "WEAK" -> strengthBar.setForeground(Color.RED);
                    case "MEDIUM" -> strengthBar.setForeground(Color.ORANGE.darker());
                    case "STRONG", "VERY STRONG" -> strengthBar.setForeground(new Color(0, 128, 0));
                    default -> strengthBar.setForeground(Color.BLACK);
                }

                // Show suggestions with emoji
                String emoji = switch (result.getStrength().toUpperCase()) {
                    case "VERY WEAK", "WEAK" -> "\u274C "; // ❌
                    case "MEDIUM" -> "\u26A0 "; // ⚠️
                    case "STRONG", "VERY STRONG" -> "\u2705 "; // ✅
                    default -> "";
                };

                suggestionsArea.setText(emoji + result.toString());
            }

            @Override public void insertUpdate(DocumentEvent e) { update(); }
            @Override public void removeUpdate(DocumentEvent e) { update(); }
            @Override public void changedUpdate(DocumentEvent e) { update(); }
        });

        // Clear button action
        clearButton.addActionListener(e -> {
            passwordField.setText("");
            strengthBar.setValue(0);
            strengthBar.setString("");
            suggestionsArea.setText("");
            passwordField.requestFocus();
        });

        frame.setVisible(true);
    }
}
