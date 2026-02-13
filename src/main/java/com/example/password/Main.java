package com.example.password;

import java.io.Console;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Use Console if available (hides password input)
        Console console = System.console();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Professional Password Strength Checker ===");

        while (true) {
            String password;

            if (console != null) {
                char[] passwordArray = console.readPassword("\nEnter password (or type 'exit' to quit): ");
                password = new String(passwordArray);
            } else {
                System.out.print("\nEnter password (or type 'exit' to quit): ");
                password = scanner.nextLine();
            }

            if (password.equalsIgnoreCase("exit")) {
                System.out.println("\nThank you for using the Password Strength Checker!");
                break;
            }

            PasswordChecker checker = new PasswordChecker();
            PasswordResult result = checker.evaluate(password);

            System.out.println(result);
        }

        scanner.close();
    }
}
